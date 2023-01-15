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

    double getEncoderPositionAccumulated_radians();
    double getEncoderPositionBounded_radians();

    double getMechanismPositionAccumulated_radians();
    double getMechanismPositionBounded_radians();

    double getPositionRaw();

    <T> T rawAccess(Class<T> clazz);


}
