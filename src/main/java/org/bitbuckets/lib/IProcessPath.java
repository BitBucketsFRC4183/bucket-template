package org.bitbuckets.lib;

import org.bitbuckets.lib.network.ILogFactory;
import org.bitbuckets.lib.network.ILoopManager;
import org.bitbuckets.lib.logging.DiffableData;
import org.bitbuckets.lib.logging.Receptor;

import java.util.function.Supplier;

/**
 * Represents a path in the process tree during setup
 * This path can add children to itself and register loggers and loops for this process
 */
public interface IProcessPath {

    boolean isTest();

    /**
     * You must call this if you are calling factories inside a factory
     * @param name the name associated with this code module
     * @return a sub-tools build specifically for the child factory. You must pass it to the child factory.
     */
    IProcessPath addChild(String name);


    /**
     * A factory that allows you to register arbitrary code on the periodic loop with execution reserved
     * before all commands and subsystems
     * @return
     */
    ILoopManager loopFactory();

    /**
     * A factory that generates Loggables, simple classes that can take your data and share it with me :D
     * @return
     */
    ILogFactory logFactory();


    <T extends DiffableData<T>> Receptor<T> data(Supplier<T> dataInitializer);

    /**
     * Reports that something has gone really wrong while trying to start up the robot
     * kills the robot.
     * @param error
     */
    void reportSetupCriticalError(String error);

    /**
     * optionally can be called at the start of your setup method to record how long it takes to do a task
     * @return a profiler id (not to be confused with ProcessPath id) that can be fed into {@link #endProfiling(int id)}
     */
    int beginProfiling(String taskName);

    /**
     * should be called at the end of whatever task your setup method had
     */
    void endProfiling(int profileTaskId);

}
