package org.bitbuckets.lib.motor.encoder;

import org.bitbuckets.lib.motor.BaseUnitType;

/**
 * Represents an encoder that's on a motor controller
 * it's being read from the motor controller and not a DIO/analog port on the RIO
 */
public interface IControllerEncoder {

    //transparency

    boolean isAbsolute();
    double getGeneralConversionFactor();
    BaseUnitType getBaseUnitType();

    double getPosition();
    double getPositionNoFactor();

    double getVelocity();
    double getVelocityNoFactor();
}
