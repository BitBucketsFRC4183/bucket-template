package org.bitbuckets.lib.encoder.can;

import com.ctre.phoenix.sensors.*;
import org.bitbuckets.lib.IProcessPath;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.IRotationEncoder;

public class CANRotationSetup implements ISetup<IRotationEncoder> {

    final int canCoderId;
    final double offset_degrees;
    final double encoderToMechanismRatio_revs;

    public CANRotationSetup(int canCoderId, double offset_degrees, double encoderToMechanismRatio_revs) {
        this.canCoderId = canCoderId;
        this.offset_degrees = offset_degrees;
        this.encoderToMechanismRatio_revs = encoderToMechanismRatio_revs;
    }

    @Override
    public CANRotationEncoder build(IProcessPath path) {

        WPI_CANCoder encoder = new WPI_CANCoder(canCoderId);

        encoder.configFactoryDefault();
        encoder.configAbsoluteSensorRange(AbsoluteSensorRange.Unsigned_0_to_360);
        encoder.configSensorDirection(false);
        encoder.configMagnetOffset(offset_degrees);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10);

        CANRotationEncoder rot = new CANRotationEncoder(offset_degrees, encoderToMechanismRatio_revs, encoder);

        path.logFactory().periodicDoubleLogger("rads", rot::getEncoderPositionBounded_radians);

        return rot;
    }
}
