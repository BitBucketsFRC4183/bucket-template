package org.bitbuckets.lib.motor.setpoint;

import java.util.Optional;

/**
 * Represents setpoint control over a device, make sure you know what kind of units you
 * configured it to use, because a velocity pid setpoint is much different than controlling velocity via voltage
 *
 * sorry if this is confusing it's basically just motor.set() but with checks to make it safer and
 * also let you use many  types of motors and not cry
 */
public interface IMotor {

    void moveAt(double units);

    /**
     * Tries to get the underlying motor, will cry if you request the wrong type
     * i.e. provide BaseTalon.class when it's a SparkMAX
     *
     * long story short dont use this unless you have a real good reason buddy
     * @param typeOfMotor type of motor class, like BaseTalon.class
     * @return the motor if it's possible, otherwise an empty optional
     * @param <T>
     */
    @Deprecated
    <T> Optional<T> underlyingMotor(Class<T> typeOfMotor);

}
