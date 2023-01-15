package org.bitbuckets.lib.encoder.can;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;

public class CANRotationEncoder implements IRotationEncoder {

    final double gearRatio;
    final WPI_CANCoder coder;

    public CANRotationEncoder(double gearRatio, WPI_CANCoder coder) {
        this.gearRatio = gearRatio;
        this.coder = coder;
    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public double getMotorFactor() {
        return gearRatio;
    }

    @Override
    public BaseUnitType getBaseUnitType() {
        return BaseUnitType.DEGREES;
    }

    @Override
    public double getEncoderPosition_radians() {
        return Angle.bind2pi_radians(Math.toRadians(getPositionRaw()));
    }

    @Override
    public double getMechanismPosition_radians() {
        return getEncoderPosition_radians() * gearRatio;
    }

    @Override
    public double getPositionRaw() {
        return coder.getAbsolutePosition();
    }

    @Override
    public double getEncoderVelocity_radiansPerSecond() {
        return 0;
    }

    @Override
    public double getVelocityRaw() {
        return 0;
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) {
        if (clazz.equals(CANCoder.class)) {
            return clazz.cast(coder);
        }

        throw new UnsupportedOperationException();
    }
}
