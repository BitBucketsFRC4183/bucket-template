package org.bitbuckets.lib.motor;

import org.bitbuckets.lib.motor.stages.InitPreBuild;

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
    InitPreBuild buildNewMotor(int canId, boolean brushless, boolean inverted, boolean neutralMode, double currentLimit);



}
