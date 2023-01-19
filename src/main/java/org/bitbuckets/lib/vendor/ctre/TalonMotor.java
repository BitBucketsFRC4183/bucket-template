package org.bitbuckets.lib.vendor.ctre;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import org.bitbuckets.lib.abstractions.IMotor;

public class TalonMotor implements IMotor {

    final TalonFX talonFX;
    final ControlMode controlMode;

    public TalonMotor(TalonFX talonFX, ControlMode controlMode) {
        this.talonFX = talonFX;
        this.controlMode = controlMode;
    }

    double cachedSetpoint = 0.0;

    @Override
    public void moveAt(double units) {
        talonFX.set(controlMode, units);

        cachedSetpoint = units;
    }

    @Override
    public double getSetpoint() {
        return cachedSetpoint;
    }

    @Override
    public <T> T rawAccess(Class<T> clazz) throws UnsupportedOperationException {
        return clazz.cast(talonFX);
    }
}
