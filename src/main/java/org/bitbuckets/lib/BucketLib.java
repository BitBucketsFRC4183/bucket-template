package org.bitbuckets.lib;

import org.bitbuckets.lib.network.*;

import java.util.ArrayList;
import java.util.List;

public class BucketLib {


    final List<Runnable> runnables = new ArrayList<>();

    final ILoopManager loop = new LoopManagerImpl(runnables);
    final IIdentityManager identity = new IdentityManagerImpl();
    final ErrorManager error = new ErrorManager(identity);

    public void setup() {
        //nothing to do...
    }

    public IHandle rootHandle() {
        return new BucketHandle(0, identity, error, loop);
    }





    public void runLoop() {
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
