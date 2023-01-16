package org.bitbuckets.lib;

import org.bitbuckets.lib.network.ErrorManager;
import org.bitbuckets.lib.network.ILogFactory;
import org.bitbuckets.lib.network.ILoopManager;

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

    ErrorManager errorManager();

    interface ReportFactory {
        //TODO fill it out
    }

}
