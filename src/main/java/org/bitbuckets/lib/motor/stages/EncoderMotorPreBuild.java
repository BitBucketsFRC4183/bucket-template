package org.bitbuckets.lib.motor.stages;

import org.bitbuckets.lib.motor.IControllerEncoder;
import org.bitbuckets.lib.motor.setpoint.IMotor;

/**
 * Third stage of building an encoder enabled motor where
 * you can acquire a direct handle to the encoder as well
 * as choose whether you want to use my inane optimizations
 */
public interface EncoderMotorPreBuild {

    /**
     * builds an encoder, can be called alongside buildWithOptimizations
     * @return prelogged encoder
     */
    IControllerEncoder acquireEncoder();

    /**
     * builds the setpoint setter
     * @param use whether it should be optimized or not
     * @return prelogged motor
     */
    IMotor build(boolean use);
}
