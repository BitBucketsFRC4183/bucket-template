package org.bitbuckets.lib.logging;

import org.bitbuckets.lib.Incubating;
import org.bitbuckets.lib.network.IIdentityManager;
import org.bitbuckets.lib.network.ILogFactory;
import org.bitbuckets.lib.network.ILoopManager;
import org.littletonrobotics.junction.Logger;

import java.util.function.Supplier;

@Incubating
/**
 * This will be a logger that directly uses ids, will support backwards timestamp injection for less-useful data
 * in order to reduce network usage and replace {@link ILogFactory}
 */
public class LogDriver implements ILogDriver {

    final IIdentityManager identityManager;
    final ILoopManager loopManager;

    public LogDriver(IIdentityManager identityManager, ILoopManager loopManager) {
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
    public <T extends DiffableData<T>> Receptor<T> pushLogger(int id, Supplier<T> initConstructor) {
        return new LogReceptor<>(id, this, initConstructor.get());
    }


    public void report(int id, String keyName, double data) {
        Logger.getInstance().recordOutput(identityManager.fullPath(id) + keyName, data);
    }

    void report(int id, String keyName, String data) {
        Logger.getInstance().recordOutput(identityManager.fullPath(id) + keyName, data);
    }


}
