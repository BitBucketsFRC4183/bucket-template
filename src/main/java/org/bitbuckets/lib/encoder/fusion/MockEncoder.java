package org.bitbuckets.lib.encoder.fusion;

import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;

import java.util.function.Supplier;

public class MockEncoder implements IRotationEncoder {

    final double gearRatio;
    final Supplier<Double> raw;

    public MockEncoder(double gearRatio, Supplier<Double> raw) {
        this.gearRatio = gearRatio;
        this.raw = raw;
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
        return BaseUnitType.FUSION;
    }

    @Override
    public double getEncoderPositionAccumulated_radians() {
        return getPositionRaw() / 2048 * (2.0 * Math.PI);
    }

    @Override
    public double getEncoderPositionBounded_radians() {
        //su -> revs -> rads
        return getEncoderPositionAccumulated_radians();
    }

    @Override
    public double getMechanismPositionAccumulated_radians() {
        return getEncoderPositionAccumulated_radians() * gearRatio;
    }

    @Override
    public double getMechanismPositionBounded_radians() {
        return getEncoderPositionBounded_radians() * gearRatio; //actually produces rads
    }

    @Override
    public double getPositionRaw() {
        return raw.get();
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}
