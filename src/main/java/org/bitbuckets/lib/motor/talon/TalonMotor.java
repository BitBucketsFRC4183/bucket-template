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

    @Override
    public double cachedUnits() {
        return cachedUnits;
    }

    @Override
    public void moveAt(double units) {
        cachedUnits = units;

        talon.set(mode, units);
    }


    //ignore this reflection code because if everyone on the team is coding diligently
    //we wont even need it!
    @Override
    public <T> Optional<T> underlyingMotor(Class<T> typeOfMotor) {
        if (typeOfMotor.equals(BaseTalon.class)) {
            return Optional.of(typeOfMotor.cast(talon));
        }

        return Optional.empty();
    }
}
