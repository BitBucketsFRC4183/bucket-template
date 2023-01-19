package org.bitbuckets.lib.implementations.log;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.lib.abstractions.core.ILogDriver;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.logging.DiffableData;
import org.bitbuckets.lib.network.IIdentityManager;
import org.bitbuckets.lib.network.LoopManager;
import org.littletonrobotics.junction.Logger;

import java.util.function.Supplier;

/**
 * This will be a logger that directly uses ids, will support backwards timestamp injection for less-useful data
 * in order to reduce network usage
 */
public class LogDriver implements ILogDriver {

    final Logger logger;
    final IIdentityManager identityManager;
    final LoopManager loopManager;

    public LogDriver(Logger logger, IIdentityManager identityManager, LoopManager loopManager) {
        this.logger = logger;
        this.identityManager = identityManager;
        this.loopManager = loopManager;
    }

    /**
     * Logger that will only log changes when you push new data to it
     * useful for saving network bandwidth
     *
     * @param id
     * @param initConstructor
     * @return
     * @param <T>
     */
    public <T extends DiffableData<T>> IDataLogger<T> generatePushLogger(int id, Supplier<T> initConstructor) {
        return new DataLogger<>(id, this, initConstructor.get());
    }

    @Override
    public Runnable generateFlasher(int id, String name, long durationMs) {
        return null;
    }


    public void report(int id, String keyName, double data) {
        logger.recordOutput(identityManager.fullPath(id) + keyName, data);
    }

    @Override
    public void report(int id, String keyName, SwerveModuleState[] moduleStates) {
        logger.recordOutput(identityManager.fullPath(id) + keyName, moduleStates);
    }

    void report(int id, String keyName, String data) {
        logger.recordOutput(identityManager.fullPath(id) + keyName, data);
    }


}
