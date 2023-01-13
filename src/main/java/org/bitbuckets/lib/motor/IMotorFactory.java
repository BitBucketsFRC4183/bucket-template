package org.bitbuckets.lib.motor;

import org.bitbuckets.lib.motor.stages.InitStage;

/**
 * Represents a the creation of a motor
 */
public interface IMotorFactory {

    /**
     *
     * @param canId
     * @param brushless neos are brushless
     * @param inverted
     * @param neutralMode true for coast, false for break
     * @param currentLimit set to Double.NAN for no limit
     * @return
     */
    InitStage buildNewMotor(int canId, boolean brushless, boolean inverted, boolean neutralMode, double currentLimit);



}
