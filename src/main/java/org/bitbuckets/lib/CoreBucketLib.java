package org.bitbuckets.lib;

import org.bitbuckets.lib.network.IIdentityFactory;
import org.bitbuckets.lib.network.ILogFactory;
import org.bitbuckets.lib.network.ILoopFactory;

public class CoreBucketLib implements BucketLib {

    final int currentId;

    final IIdentityFactory identityFactory;
    final ILoopFactory loopFactory;
    final ILogFactory logFactory;

    public CoreBucketLib(int currentId, IIdentityFactory identityFactory, ILoopFactory loopFactory, ILogFactory logFactory) {
        this.currentId = currentId;
        this.identityFactory = identityFactory;
        this.loopFactory = loopFactory;
        this.logFactory = logFactory;
    }

    @Override
    public BucketLib child(String name) {
        int childId = identityFactory.childProcess(currentId, name);


        return new CoreBucketLib(childId, identityFactory, loopFactory, logFactory);
    }

    @Override
    public ILoopFactory loopFactory() {
        return null;
    }

    @Override
    public ILogFactory logFactory() {
        return null;
    }
}
