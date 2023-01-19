package org.bitbuckets.lib.abstractions.core;

import edu.wpi.first.math.kinematics.SwerveModuleState;
import org.bitbuckets.lib.abstractions.log.IDataLogger;
import org.bitbuckets.lib.logging.DiffableData;

import java.util.function.Supplier;

/**
 * Users of the library should never see this
 * Allows users to report data to the logs optimally
 */
public interface ILogDriver {

    /**
     * Generates a Receptor object-pooled logger (uses an object to describe your logging)
     * TODO actually document this
     *
     * @param id
     * @param initConstructor
     * @return
     * @param <T>
     */
    <T extends DiffableData<T>> IDataLogger<T> generatePushLogger(int id, Supplier<T> initConstructor);

    /**
     *
     * @param id
     * @param name the name assigned to the flasher
     * @param durationMs how long before the flasher resets to 0
     * @return a runnable that you can call when you want to signal some arbitrary thing to the logger
     */
    Runnable generateFlasher(int id, String name, long durationMs);


    void report(int id, String keyName, double data);
    void report(int id, String keyname, SwerveModuleState[] moduleStates);

}
