package org.bitbuckets.lib.motor.stages;

/**
 * The first phase of setting up a motor, where you choose
 * whether you'd like to use percent control or pid control or worse
 */
public interface InitStage {


    PercentStage usePercent();

    /**
     *
     * @param positionOrVelocity true for velocity
     * @param pidConstants length 5 array, in order p,i,d,ff,izone
     * @return
     */
    PidStage usePID(boolean positionOrVelocity, double[] pidConstants);
}