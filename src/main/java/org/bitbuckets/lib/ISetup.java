package org.bitbuckets.lib;

/**
 * Represents the setup phase for some code that will run during the periodic loop. Need to provide a root
 * level version of this to the loggable robot
 * @param <T> type of object it will produce
 */
public interface ISetup<T> {

    /**
     * function representing the setup of a piece of code that requires robot-initalized-specific devices
     *
     * make sure you call any child factory with tools.child() and NOT with this tools instance
     * @param userBucketLib a variety of tools.
     * @return a fully initialized object
     */
    T build(UserBucketLib userBucketLib);

}
