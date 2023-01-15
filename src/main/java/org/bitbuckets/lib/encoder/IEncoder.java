package org.bitbuckets.lib.encoder;

import org.bitbuckets.lib.motor.BaseUnitType;

/**
 * Represents an encoder that's on a motor controller
 * it's being read from the motor controller and not a DIO/analog port on the RIO
 */
public interface IEncoder {

    //transparency

    boolean isAbsolute();
    double getGeneralConversionFactor();
    BaseUnitType getBaseUnitType();

    double getPosition_metersPerSecond();
    double getPositionFactored();
    double getPositionRaw();

    double getVelocity_metersPerSecond();
    double getVelocityFactored();
    double getVelocityRaw();
}
