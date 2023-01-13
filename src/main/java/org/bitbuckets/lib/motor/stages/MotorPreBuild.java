package org.bitbuckets.lib.motor.stages;

import org.bitbuckets.lib.motor.setpoint.IMotor;

/**
 * The third stage of setting up your motor where you decide whether to use
 * my ridiculous optimizations or not
 */
public interface MotorPreBuild {

    /**
     * enables non-pid oriented status frame modifications if enabled
     * really experimental dont use this during competitions
     *
     * @param use dont enable this if you value your sanity
     * @return prelogged motor
     */
    IMotor buildNoEncoder(boolean use);

}
