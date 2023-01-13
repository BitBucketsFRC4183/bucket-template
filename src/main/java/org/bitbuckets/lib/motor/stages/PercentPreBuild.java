package org.bitbuckets.lib.motor.stages;

import org.bitbuckets.lib.motor.EncoderType;

/**
 * The second stage of configuring your percent motor, where you decide whether to
 * link an encoder to it or not
 */
public interface PercentPreBuild {

    /**
     * Hooks up an encoder but doesn't hook it to pid, because this is PO
     * @param type type
     * @return my ass
     */
    EncoderMotorPreBuild withEncoder(EncoderType type);

    /**
     * Doesn't set up code for motor based encoder
     * @return ass
     */
    EncoderMotorPreBuild withNoEncoder();

}
