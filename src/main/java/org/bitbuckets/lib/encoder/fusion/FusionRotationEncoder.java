package org.bitbuckets.lib.encoder.fusion;

import org.bitbuckets.lib.encoder.IEncoder;
import org.bitbuckets.lib.encoder.IRotationEncoder;
import org.bitbuckets.lib.motor.BaseUnitType;

public class FusionRotationEncoder implements IRotationEncoder {

    final IRotationEncoder talon;
    final IRotationEncoder can;

    public FusionRotationEncoder(IRotationEncoder talon, IRotationEncoder can) {
        this.talon = talon;
        this.can = can;
    }


    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public double getMotorFactor() {
        return 1;
    }

    @Override
    public BaseUnitType getBaseUnitType() {
        return BaseUnitType.FUSION;
    }

    @Override
    public double getEncoderPosition_radians() {

        return talon.getEncoderPosition_radians();
    }

    @Override
    public double getMechanismPosition_radians() {
        return can.getMechanismPosition_radians();
    }

    @Override
    public double getPositionRaw() {
        return talon.getPositionRaw();
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
        throw new UnsupportedOperationException();
    }
}
