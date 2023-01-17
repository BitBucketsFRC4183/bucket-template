package org.bitbuckets.lib.network;

import java.util.List;


//TODO better
public class LoopManager implements ILoopManager {


    final List<Runnable> runnables;

    public LoopManager(List<Runnable> runnables) {
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
