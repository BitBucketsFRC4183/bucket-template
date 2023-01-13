package org.bitbuckets.lib.motor;

/**
 * What are the base unit types of my encoder?
 */
public enum BaseUnitType {

    REVS, //revolutions, used by sparks, objectively superior unless you want to do anything that isn't rotation
    SU, //Sensor units, used by talons and the like, also known as ticks and other dumb stuff

}
