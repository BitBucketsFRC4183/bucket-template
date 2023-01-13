package org.bitbuckets.lib;

/**
 * Represents a whole ass robot that has had it's dependencies initialized
 */
public interface IRobot {

    /**
     * Don't use this
     */
    @Deprecated
    void autoPeriodic();

    /**
     * Runs during periodic, useful if a certain someone hasn't set up Routines yet
     */
    void teleopPeriodic();

}
