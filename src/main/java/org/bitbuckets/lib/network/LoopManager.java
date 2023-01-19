package org.bitbuckets.lib.network;

import org.bitbuckets.lib.abstractions.IExecutable;

import java.util.List;


//TODO better
public class LoopManager  {


    final List<IExecutable> runnables;

    public LoopManager(List<IExecutable> runnables) {
        this.runnables = runnables;
    }


    public void registerShutdown(Runnable runnable) {
        throw new UnsupportedOperationException();
    }


    public void registerDisable(IExecutable runnable) {
        throw new UnsupportedOperationException();
    }


    public void registerLoop(IExecutable runnable) {
        this.runnables.add(runnable);
    }


    public void registerPriorityLoop(IExecutable runnable) {
        this.runnables.add(runnable);
    }




}
