package org.bitbuckets.lib.encoder.talon;

import com.ctre.phoenix.motorcontrol.can.BaseTalon;
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
    public double getEncoderPosition_radians() {
        //su -> revs -> rads
        return getPositionRaw() / 2048 * (2 * Math.PI);
    }

    @Override
    public double getMechanismPosition_radians() {
        return Angle.bind2pi_radians(getEncoderPosition_radians() * gearRatio_revs); //actually produces rads
    }

    @Override
    public double getPositionRaw() {
        return talon.getSensorCollection().getIntegratedSensorPosition();
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
        if (clazz.equals(TalonFX.class)) {
            return clazz.cast(talon);
        }

        throw new UnsupportedOperationException();
    }

}
