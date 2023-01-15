package org.bitbuckets.lib;

import org.bitbuckets.lib.network.ILoopManager;

import java.util.List;


//TODO better
public class LoopManagerImpl implements ILoopManager {


    final List<Runnable> runnables;

    LoopManagerImpl(List<Runnable> runnables) {
        this.runnables = runnables;
    }

    @Override
    public void registerShutdown(Runnable runnable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerDisable(Runnable runnable) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void registerLoop(Runnable runnable) {
        runnables.add(runnable);
    }

    @Override
    public void registerPriorityLoop(Runnable runnable) {
        runnables.add(runnable);
    }

    @Override
    public List<Runnable> extract() {
        return runnables;
    }


}
