package org.bitbuckets.lib;

import org.bitbuckets.SimLevel;
import org.bitbuckets.lib.abstractions.IExecutable;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.abstractions.log.ISignalLogger;
import org.bitbuckets.lib.logging.DiffableData;

import java.util.function.Supplier;

/**
 * Represents a path in the process tree during setup
 * This path can add children to itself and register loggers and loops for this process
 */
public interface IProcessPath {


    /**
     * Are we in the matrix?
     * @return
     */
    SimLevel getSimLevel();

    /**
     * You must call this if you are calling factories inside a factory
     * @param name the name associated with this code module
     * @return a sub-tools build specifically for the child factory. You must pass it to the child factory.
     */
    IProcessPath addChild(String name);

    //looping

    void registerLoop(IExecutable executable);
    void registerLoop(IExecutable executable, int periodMs);
    void registerLoop(IExecutable executable, int periodMs, boolean canSkip);


    //logging


    /**
     * Generates a Loggable which can log data classes
     * @param dataInitializer the constructor of the autogenerated version of your data class
     * @return a class which you can submit data to using lambdas
     * @param <T> the type of data class you want to log. Make sure it is marked with @Loggable
     */
    <A,T extends DiffableData<?>> IDataLogger<A> generatePushDataLogger(Supplier<T> dataInitializer);
    ISignalLogger generateSignalLogger(String taskName);


    /**
     * Reports that something has gone really wrong while trying to start up the robot
     * kills the robot.
     * @param error
     */
    void reportSetupCriticalError(String error);

    //will look for the process that was running when this was called and find any open signals
    //in which case it will throw a more descriptive error like "robot failed while doing: task"


}
