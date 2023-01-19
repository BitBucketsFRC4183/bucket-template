package org.bitbuckets.lib.abstractions;

/**
 * Represents setpoint control over a device, make sure you know what kind of units you
 * configured it to use, because a velocity pid setpoint is much different than controlling velocity via voltage
 *
 * sorry if this is confusing it's basically just motor.set() but with checks to make it safer and
 * also let you use many  types of motors and not cry
 *
 * Common ways of obtaining: TalonPositionSetup, TalonVelocitySetup, TalonPercentSetup
 */
public interface IMotor extends IRaw {

    void moveAt(double units);

    double getSetpoint();


}
