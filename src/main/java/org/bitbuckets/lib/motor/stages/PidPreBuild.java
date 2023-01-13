package org.bitbuckets.lib.motor.stages;

import org.bitbuckets.lib.motor.EncoderType;

/**
 * The second stage of controlling your pid motor, where you decide whether to use
 * an encoder or an encoder
 */
public interface PidPreBuild {

    /**
     * pid has to use encoder, choose the right type, it's going to determine ur pid loop error
     * @param type
     * @return
     */
    EncoderMotorPreBuild withEncoder(EncoderType type);

}
