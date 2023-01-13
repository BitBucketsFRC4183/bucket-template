package org.bitbuckets.lib.motor.setpoint;

import java.util.Optional;

public class TuneableTalonMotor implements IMotor{
    @Override
    public void moveAt(double units) {

    }

    @Override
    public <T> Optional<T> underlyingMotor(Class<T> typeOfMotor) {
        return Optional.empty();
    }
}
