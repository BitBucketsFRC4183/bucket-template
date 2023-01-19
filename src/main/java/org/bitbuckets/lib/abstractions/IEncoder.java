package org.bitbuckets.lib.abstractions;

/**
 * Represents an encoder that's on a motor controller
 * it's being read from the motor controller and not a DIO/analog port on the RIO
 *
 * TODO caching delegate
 * TODO logging delegate
 */
public interface IEncoder extends IRaw {

    //actual methods

    double getMechanismFactor();
    double getRotationsToMetersFactor();
    double getPositionRaw();

    @Deprecated //DONT USE THIS UNLESS YOU HAVE TO
    void forceOffset(double offsetUnits_baseUnits);


    //utility methods

    double getEncoderPositionAccumulated_radians();
    double getEncoderPositionBounded_radians();
    double getMechanismPositionAccumulated_radians();
    double getMechanismPositionBounded_radians();


    double getPositionMechanism_meters();
    double getPositionEncoder_meters();
    double getVelocityMechanism_metersPerSecond();
    double getVelocityEncoder_metersPerSecond();


}
