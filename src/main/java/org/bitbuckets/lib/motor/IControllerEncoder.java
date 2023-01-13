package org.bitbuckets.lib.motor;

import edu.wpi.first.wpilibj.Encoder;

/**
 * Represents an encoder that's on a motor controller
 * it's being read from the motor controller and not a DIO/analog port on the RIO
 */
public interface IControllerEncoder {

    //transparency

    boolean isAbsolute();
    double getConversionFactor();
    BaseUnitType getBaseUnitType();

    double getPosition();
    double getPositionNoFactor();

    double getVelocity();
    double getVelocityNoFactor();
}
