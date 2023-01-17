package org.bitbuckets.lib.encoder;

import org.bitbuckets.lib.IRaw;
import org.bitbuckets.lib.motor.BaseUnitType;

/**
 * Represents an encoder that's on a motor controller
 * it's being read from the motor controller and not a DIO/analog port on the RIO
 */
public interface IEncoder extends IRaw {

    //transparency

    boolean isAbsolute();
    double getGeneralConversionFactor();
    BaseUnitType getBaseUnitType();

    double getPositionMechanism_meters();
    double getPositionEncoder_meters();

    double getVelocityMechanism_metersPerSecond();
    double getVelocityEncoder_metersPerSecond();


}
