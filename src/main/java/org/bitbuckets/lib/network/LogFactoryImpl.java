package org.bitbuckets.lib.network;

import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Implementation
 */
public class LogFactoryImpl implements ILogFactory {

    final Logger logger;
    final IIdentityManager identities;
    final ILoopManager loopFactory;
    final int selfIdentity;

    public LogFactoryImpl(IIdentityManager identities, ILoopManager loopFactory, int selfIdentity) {
        this.logger = Logger.getInstance();
        this.identities = identities;
        this.loopFactory = loopFactory;
        this.selfIdentity = selfIdentity;
    }


    @Override
    public Loggable<Integer> intLogger(String id) {
        return i -> logger.recordOutput(id, i);
    }

    @Override
    public Loggable<String> stringLogger(String id) {
        return i -> logger.recordOutput(id, i);
    }

    @Override
    public Loggable<Double> doubleLogger(String id) {
        return i -> logger.recordOutput(id, i);
    }

    @Override
    public Loggable<double[]> arrayLogger(String id) {
        return i -> logger.recordOutput(id, i);
    }

    @Override
    public void periodicDoubleLogger(String id, Supplier<Double> data) {
        loopFactory.registerLoop(() -> {
            logger.recordOutput(id,data.get());
        });
    }

    @Override
    public <T extends LoggableInputs> void registerInputLoop(Supplier<T> constructor, Consumer<T> updateInputs, Consumer<T> updateDevices) {
        T inputs = constructor.get(); //object is shared between the two, this is dangerous, don't multithread unless you enjoy pain

        loopFactory.registerPriorityLoop(() -> {
            updateInputs.accept(inputs);
            logger.processInputs(identities.fullPath(selfIdentity), inputs);
        });

        loopFactory.registerLoop(() -> updateDevices.accept(inputs));
    }
}
