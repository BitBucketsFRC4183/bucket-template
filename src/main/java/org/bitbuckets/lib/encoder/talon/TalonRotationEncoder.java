package org.bitbuckets.lib.encoder.talon;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.encoder.Angle;
import org.bitbuckets.lib.motor.BaseUnitType;
import org.bitbuckets.lib.encoder.IRotationEncoder;

public class TalonRotationEncoder implements IRotationEncoder {

    final TalonFX talon;
    final double gearRatio_revs;

    public TalonRotationEncoder(TalonFX talon, double gearRatio_revs) {
        this.talon = talon;
        this.gearRatio_revs = gearRatio_revs;
    }

    @Override
    public boolean isAbsolute() {
        return false;
    }

    @Override
    public double getMotorFactor() {
        return gearRatio_revs;
    }

    @Override
    public BaseUnitType getBaseUnitType() {
        return BaseUnitType.SU;
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
        return getEncoderPositionAccumulated_radians() * gearRatio_revs;
    }

    @Override
    public double getMechanismPositionBounded_radians() {
        return Angle.wrap(getEncoderPositionAccumulated_radians() * gearRatio_revs); //actually produces rads
    }

    @Override
    public double getPositionRaw() {
        return talon.getSensorCollection().getIntegratedSensorPosition();
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) {
        if (clazz.equals(TalonFX.class)) {
            return clazz.cast(talon);
        }

        throw new UnsupportedOperationException();
    }

}
