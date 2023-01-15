package org.bitbuckets.lib.encoder.can;

import com.ctre.phoenix.sensors.*;
import org.bitbuckets.lib.IHandle;
import org.bitbuckets.lib.ISetup;
import org.bitbuckets.lib.encoder.IRotationEncoder;

public class CANRotationSetup implements ISetup<IRotationEncoder> {

    final int canCoderId;
    final double offset_radians;
    final double encoderToMechanismRatio_revs;

    public CANRotationSetup(int canCoderId, double offset_radians, double encoderToMechanismRatio_revs) {
        this.canCoderId = canCoderId;
        this.offset_radians = offset_radians;
        this.encoderToMechanismRatio_revs = encoderToMechanismRatio_revs;
    }

    @Override
    public CANRotationEncoder build(IHandle userBucketLib) {

        CANCoderConfiguration config = new CANCoderConfiguration();
        config.absoluteSensorRange = AbsoluteSensorRange.Unsigned_0_to_360;
        config.magnetOffsetDegrees = Math.toDegrees(offset_radians);
        config.sensorDirection = false;

        WPI_CANCoder encoder = new WPI_CANCoder(canCoderId);
        encoder.configAllSettings(config, 250);
        encoder.setStatusFramePeriod(CANCoderStatusFrame.SensorData, 10);


        CANRotationEncoder rot = new CANRotationEncoder(encoderToMechanismRatio_revs, encoder);
        userBucketLib.logFactory().periodicDoubleLogger("raw", rot::getPositionRaw);


        return rot;
    }
}
