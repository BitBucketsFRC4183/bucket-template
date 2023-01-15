package org.bitbuckets.lib;

import org.bitbuckets.lib.network.ILogFactory;
import org.bitbuckets.lib.network.ILoopManager;

public interface IHandle {

    boolean isTest();

    /**
     * You must call this if you are calling factories inside a factory
     * @param name the name associated with this code module
     * @return a sub-tools build specifically for the child factory. You must pass it to the child factory.
     */
    IHandle child(String name);


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

    interface ReportFactory {
        //TODO fill it out
    }

}
