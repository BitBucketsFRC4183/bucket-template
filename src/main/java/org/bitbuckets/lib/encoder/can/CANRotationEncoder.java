package org.bitbuckets.lib.encoder.can;

import com.ctre.phoenix.sensors.CANCoder;
import com.ctre.phoenix.sensors.WPI_CANCoder;
import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;

//don't touch the math or your head will come off
public class CANRotationEncoder implements IRotationEncoder {

    final double offset_degrees;
    final double gearRatio;
    final WPI_CANCoder coder;

    public CANRotationEncoder(double offset_degrees, double gearRatio, WPI_CANCoder coder) {
        this.offset_degrees = offset_degrees;
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
    public double getEncoderPositionAccumulated_radians() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getEncoderPositionBounded_radians() {
        return Math.toRadians(getPositionRaw());
    }

    @Override
    public double getMechanismPositionAccumulated_radians() {
        throw new UnsupportedOperationException();
    }

    @Override
    public double getMechanismPositionBounded_radians() {
        return Angle.wrap(getEncoderPositionBounded_radians() * gearRatio);
    }

    @Override
    public double getPositionRaw() {
        return coder.getAbsolutePosition();
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) {
        if (clazz.equals(CANCoder.class)) {
            return clazz.cast(coder);
        }

        throw new UnsupportedOperationException();
    }
}
