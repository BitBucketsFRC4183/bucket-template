package org.bitbuckets.lib;

import org.bitbuckets.lib.abstractions.IExecutable;
import org.bitbuckets.lib.implementations.log.LogDriver;
import org.bitbuckets.lib.network.*;
import org.littletonrobotics.junction.Logger;

import java.util.ArrayList;
import java.util.List;

@Deprecated
/**
 * TODO: Move logic into RobotContainer
 */
public class BucketLib {


    final List<IExecutable> runnables = new ArrayList<>();

    final LoopManager loop = new LoopManager(runnables);
    final IIdentityManager identity = new IdentityManager();
    final ErrorManager error = new ErrorManager(identity);
    final LogDriver driver = new LogDriver(Logger.getInstance(), identity, loop);

    public void setup() {
        //nothing to do...
    }

    public IProcessPath rootProcessPath() {
        return new ProcessPath(0, identity, error, loop, driver);
    }





    public void runLoop() {
        for (IExecutable runnable : runnables) {
            try {
                runnable.run();
            } catch (Exception e) {

            }

        }
    }
}
