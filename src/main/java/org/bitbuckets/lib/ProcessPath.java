package org.bitbuckets.lib;

import org.bitbuckets.lib.logging.DiffableData;
import org.bitbuckets.lib.network.*;
import org.bitbuckets.lib.logging.Receptor;

import java.util.function.Supplier;

public class ProcessPath implements IProcessPath {

    final int currentId;

    final IIdentityManager identityFactory;
    final ErrorManager errorManager;
    final ILoopManager loopManager;
    final ILogFactory selfLogFactory;


    public ProcessPath(int currentId, IIdentityManager identityFactory, ErrorManager errorManager, ILoopManager loopManager) {
        this.currentId = currentId;
        this.identityFactory = identityFactory;
        this.errorManager = errorManager;
        this.loopManager = loopManager;
        this.selfLogFactory = new LogFactory(identityFactory, loopManager, currentId);
    }

    @Override
    public boolean isTest() {
        return true;
    }

    @Override
    public IProcessPath addChild(String name) {
        int childId = identityFactory.childProcess(currentId, name);

        return new ProcessPath(childId, identityFactory, errorManager, loopManager);
    }


    @Override
    public ILoopManager loopFactory() {
        return loopManager;
    }

    @Override
    public ILogFactory logFactory() { //TODO this is really dumb and bad
        return selfLogFactory;
    }

    @Override
    public <T extends DiffableData<T>> Receptor<T> data(Supplier<T> dataInitializer) {
        return null;
    }

    @Override
    public void reportSetupCriticalError(String error) {

    }

    @Override
    public int beginProfiling(String taskName) {
        return 0;
    }

    @Override
    public void endProfiling(int profileTaskId) {

    }


    public ErrorManager errorManager() {
        return errorManager;
    }


}
