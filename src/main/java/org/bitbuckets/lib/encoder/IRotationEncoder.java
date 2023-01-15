package org.bitbuckets.lib.encoder;

import org.bitbuckets.lib.motor.BaseUnitType;

/**
 * TODO NONE OF THE ANGLES ARE BOUND TO 2PI.
 * Do it yourself if you need to
 */
public interface IRotationEncoder {

    //transparency

    boolean isAbsolute();
    double getMotorFactor();
    BaseUnitType getBaseUnitType();

    double getEncoderPosition_radians();
    double getMechanismPosition_radians();
    double getPositionRaw();

    double getEncoderVelocity_radiansPerSecond();
    double getVelocityRaw();

    <T> T rawAccess(Class<T> clazz);


}
