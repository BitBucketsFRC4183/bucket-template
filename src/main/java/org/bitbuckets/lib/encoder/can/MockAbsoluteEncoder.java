package org.bitbuckets.lib.encoder.can;

import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;

import java.util.function.Supplier;

public class MockAbsoluteEncoder implements IRotationEncoder {

    final Supplier<Double> angleDegress;

    public MockAbsoluteEncoder(Supplier<Double> angleDegress) {
        this.angleDegress = angleDegress;
    }

    @Override
    public boolean isAbsolute() {
        return true;
    }

    @Override
    public double getMotorFactor() {
        return 1;
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
        throw new UnsupportedOperationException();
    }

    @Override
    public double getPositionRaw() {
        return angleDegress.get();
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) {
        throw new UnsupportedOperationException();
    }
}
