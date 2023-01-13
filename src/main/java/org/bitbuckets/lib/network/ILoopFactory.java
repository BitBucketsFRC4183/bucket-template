package org.bitbuckets.lib.network;

import java.util.List;

public interface ILoopFactory {

    /**
     * Registers a runnable piece of code to run when the robot turns off
     * @param runnable
     */
    void registerShutdown(Runnable runnable);

    /**
     * Registers a runnable piece of code to run every time the robot is disabled (post match, etc)
     * @param runnable
     */
    void registerDisable(Runnable runnable);

    /**
     * Registers a runnable to run every periodic frame, before any command or subsystem
     * @param runnable
     */
    void registerLoop(Runnable runnable);

    /**
     * Registers a runnable to run every periodic frame, before any other runnables scheduled to run every frame
     * @param runnable
     */
    void registerPriorityLoop(Runnable runnable);

    /**
     * DONT CALL THIS UNLESS YOU WANT TO CRY
     * @return
     */
    @Deprecated
    List<Runnable> extract();

}
