package org.bitbuckets.lib.motor;

/**
 * What kind of encoder is it?
 */
public enum EncoderType {

    ANALOG, //it's analog, uses analog control (pwm-like or literally just voltage)
    INTEGRATED, //it's built into the motor!
    QUAD //it's a normal digital encoder

}
