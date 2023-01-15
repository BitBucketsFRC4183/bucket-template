package org.bitbuckets.lib.network;

import edu.wpi.first.wpilibj.DriverStation;

public class ErrorManager {

    final IIdentityManager identityFactory;

    public ErrorManager(IIdentityManager identityFactory) {
        this.identityFactory = identityFactory;
    }

    public void flagError(int process, String error) {
        //TODO actually robust error handling that isn't dumb

        DriverStation.reportError(String.format("[BUCKET] process id [%s] failed with error [%s]", process, error), false);
        throw new IllegalStateException("[BUCKET] Fail Fast Guard Exception");
    }
}
