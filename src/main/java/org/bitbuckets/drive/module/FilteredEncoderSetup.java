package org.bitbuckets.drive.module;

import com.ctre.phoenix.ErrorCode;
import com.ctre.phoenix.sensors.*;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.abstractions.IEncoder;
import org.bitbuckets.lib.abstractions.ISetup;

public class FilteredEncoderSetup implements ISetup<FilteredEncoder> {

    final double magneticOffset_degrees;
    final ISetup<IEncoder> relative;

    public FilteredEncoderSetup(double magneticOffset_degrees, ISetup<IEncoder> relative) {
        this.magneticOffset_degrees = magneticOffset_degrees;
        this.relative = relative;
    }

    double generateStartingPosition_encoderRadians(IEncoder encoder, double startingPosition_mechanismRadians) {
        return startingPosition_mechanismRadians / encoder.getMechanismFactor();
    }

    double retrieveCanCoderReadout_mechanismRadians(CANCoder cancoder) {
        double lastAbsolutePosition = cancoder.getAbsolutePosition();
        double sanityCheckingCounter = 0;
        while (sanityCheckingCounter < 3) {
            double currentPosition = cancoder.getAbsolutePosition();

            if (lastAbsolutePosition == currentPosition) {
                sanityCheckingCounter++;
            }
            lastAbsolutePosition = currentPosition;
        }

        return lastAbsolutePosition;
    }

    void forcePositionToEncoder(IEncoder relativeEncoder, double lastAbsolutePosition_mechanismRadians) {
        double startingPosition_encoderRadians = generateStartingPosition_encoderRadians(relativeEncoder, lastAbsolutePosition_mechanismRadians);
        double encoderSensorUnits = startingPosition_encoderRadians / Math.PI / 2.0 * 2048.0;
        relativeEncoder.forceOffset(encoderSensorUnits);
    }

    //TODO test this all more
    @Override
    public FilteredEncoder build(IProcessPath path) {
        //TODO this is bad but honestly i dont care as long as i get consistent measurements

        CANCoderConfiguration config = new CANCoderConfiguration();

        //needed to not explode instantly (cancoders suck)
        config.initializationStrategy = SensorInitializationStrategy.BootToAbsolutePosition;
        config.sensorCoefficient = 2 * Math.PI / 4096.0;
        config.unitString = "rad";
        config.sensorTimeBase = SensorTimeBase.PerSecond;
        config.magnetOffsetDegrees = magneticOffset_degrees;
        config.enableOptimizations = true;

        CANCoder cancoder = new CANCoder(0);
        ErrorCode code = cancoder.configAllSettings(config, 250);
        if (code != null) {
            path.reportSetupCriticalError("cannot use can encoder for reading!");
        }

        CANCoderFaults faults = new CANCoderFaults();
        cancoder.getFaults(faults);

        if (faults.hasAnyFault()) {
            path.reportSetupCriticalError("should not have any faults!");
        }

        double lastAbsolutePosition = retrieveCanCoderReadout_mechanismRadians(cancoder);
        IEncoder relativeEncoder = relative.build(path);
        forcePositionToEncoder(relativeEncoder, lastAbsolutePosition);

        return new FilteredEncoder(relativeEncoder);
    }
}
