package org.bitbuckets.lib;

import org.bitbuckets.lib.network.*;

public class BucketHandle implements IHandle {

    final int currentId;

    final IIdentityManager identityFactory;
    final ErrorManager errorManager;
    final ILoopManager loopManager;
    final ILogFactory selfLogFactory;


    public BucketHandle(int currentId, IIdentityManager identityFactory, ErrorManager errorManager, ILoopManager loopManager) {
        this.currentId = currentId;
        this.identityFactory = identityFactory;
        this.errorManager = errorManager;
        this.loopManager = loopManager;
        this.selfLogFactory = new LogFactoryImpl(identityFactory, loopManager, currentId);
    }

    @Override
    public boolean isTest() {
        return true;
    }

    @Override
    public IHandle child(String name) {
        int childId = identityFactory.childProcess(currentId, name);

        return new BucketHandle(childId, identityFactory, errorManager, loopManager);
    }


    @Override
    public ILoopManager loopFactory() {
        return loopManager;
    }

    @Override
    public ILogFactory logFactory() { //TODO this is really dumb and bad
        return selfLogFactory;
    }


}
