package org.bitbuckets.lib.motor.talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import org.bitbuckets.lib.motor.IMotor;

import java.util.Optional;

/**
 * Talon FX setpoint
 */
public class TalonMotor implements IMotor {

    //TODO send the backlayer logging some useful data

    final BaseTalon talon;
    final ControlMode mode;

    public TalonMotor(BaseTalon talon, ControlMode mode) {
        this.talon = talon;
        this.mode = mode;
    }

    double cachedUnits = 0.0;

    double cachedUnits() {
        return cachedUnits;
    }

    @Override
    public void moveAt(double units) {
        cachedUnits = units;

        talon.set(mode, units);
    }


    @Override
    public <T> T rawAccess(Class<T> type) {
        if (type.equals(BaseTalon.class)) {
            return type.cast(talon);
        }

        throw new UnsupportedOperationException();
    }
}
