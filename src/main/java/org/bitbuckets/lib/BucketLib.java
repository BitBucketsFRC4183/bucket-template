package org.bitbuckets.lib;

import org.bitbuckets.lib.network.*;

import java.util.ArrayList;
import java.util.List;

@Deprecated
/**
 * TODO: Move logic into RobotContainer
 */
public class BucketLib {


    final List<Runnable> runnables = new ArrayList<>();

    final ILoopManager loop = new LoopManager(runnables);
    final IIdentityManager identity = new IdentityManager();
    final ErrorManager error = new ErrorManager(identity);

    public void setup() {
        //nothing to do...
    }

    public IProcessPath rootProcessPath() {
        return new ProcessPath(0, identity, error, loop);
    }





    public void runLoop() {
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
