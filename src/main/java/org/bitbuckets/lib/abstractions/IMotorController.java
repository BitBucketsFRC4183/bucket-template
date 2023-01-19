package org.bitbuckets.lib.abstractions;

/**
 * Can be obtained in the same place as IMotor since most IMotor setups are producing IMotorControllers
 */
public interface IMotorController extends IMotor, IEncoder {
}
