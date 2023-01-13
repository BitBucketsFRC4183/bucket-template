package org.bitbuckets.lib.motor.setpoint;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;

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

    double cachedUnits = Double.NaN; //only command the motor if our setpoint has changed

    @Override
    public void moveAt(double units) {
        if (cachedUnits != units) {
            //TODO more optimizations because i'm that cool

            cachedUnits = units;
            talon.set(mode, units);
        }
    }
}
