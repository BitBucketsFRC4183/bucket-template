package org.bitbuckets.lib;

import org.bitbuckets.SimLevel;
import org.bitbuckets.lib.abstractions.IExecutable;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.abstractions.log.ISignalLogger;
import org.bitbuckets.lib.implementations.log.DataLogger;
import org.bitbuckets.lib.implementations.log.LogDriver;
import org.bitbuckets.lib.logging.DiffableData;
import org.bitbuckets.lib.network.*;

import java.util.function.Supplier;

public class ProcessPath implements IProcessPath {

    final int currentId;

    final IIdentityManager identityFactory;
    final ErrorManager errorManager;
    final LoopManager loopManager;
    final LogDriver driver;

    public ProcessPath(int currentId, IIdentityManager identityFactory, ErrorManager errorManager, LoopManager loopManager, LogDriver driver) {
        this.currentId = currentId;
        this.identityFactory = identityFactory;
        this.errorManager = errorManager;
        this.loopManager = loopManager;
        this.driver = driver;
    }

    @Override
    public SimLevel getSimLevel() {
        return SimLevel.SIM_CLASSES;
    }

    @Override
    public IProcessPath addChild(String name) {
        int childId = identityFactory.childProcess(currentId, name);

        return new ProcessPath(childId, identityFactory, errorManager, loopManager, driver);
    }


    @Override
    public void registerLoop(IExecutable executable) {
        loopManager.registerLoop(executable::run);
    }

    @Override
    public void registerLoop(IExecutable executable, int periodMs) {
        loopManager.registerLoop(executable::run);
    }

    @Override
    public void registerLoop(IExecutable executable, int periodMs, boolean canSkip) {
        loopManager.registerLoop(executable::run);
    }

    @Override
    public <A, T extends DiffableData<?>> IDataLogger<A> generatePushDataLogger(Supplier<T> dataInitializer) {
        return new DataLogger(currentId, driver, dataInitializer.get());
    }


    @Override
    public ISignalLogger generateSignalLogger(String taskName) {
        return null;
    }



    @Override
    public void reportSetupCriticalError(String error) {

    }



    public ErrorManager errorManager() {
        return errorManager;
    }


}
