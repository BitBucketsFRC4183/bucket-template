package org.bitbuckets.lib.network;

import org.bitbuckets.lib.network.util.CachedConsumer;
import org.bitbuckets.lib.network.util.UnaryAcceptor;
import org.littletonrobotics.junction.Logger;
import org.littletonrobotics.junction.inputs.LoggableInputs;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Implementation
 */
public class LogFactoryImpl implements ILogFactory {

    final Logger logger;
    final IIdentityFactory identities;
    final ILoopFactory loopFactory;
    final int selfIdentity;

    public LogFactoryImpl(Logger logger, IIdentityFactory identities, ILoopFactory loopFactory, int selfIdentity) {
        this.logger = logger;
        this.identities = identities;
        this.loopFactory = loopFactory;
        this.selfIdentity = selfIdentity;
    }


    @Override
    public Loggable<Integer> intLogger(String id) {
        return new CachedConsumer<>(i -> logger.recordOutput(identities.fullPath(selfIdentity) + "/" + id, i));
    }

    @Override
    public Loggable<String> stringLogger(String id) {
        return new CachedConsumer<>(i -> logger.recordOutput(identities.fullPath(selfIdentity) + "/" + id, i));
    }

    @Override
    public Loggable<Double> doubleLogger(String id) {
        return new CachedConsumer<>(i -> logger.recordOutput(identities.fullPath(selfIdentity) + "/" + id, i));
    }

    @Override
    public Loggable<double[]> arrayLogger(String id) {
        return new CachedConsumer<>(i -> logger.recordOutput(identities.fullPath(selfIdentity) + "/" + id, i));
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
