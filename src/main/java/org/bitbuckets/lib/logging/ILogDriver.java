package org.bitbuckets.lib.logging;

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
    <T extends DiffableData<T>> Receptor<T> pushLogger(int id, Supplier<T> initConstructor);


    void report(int id, String keyName, double data);

}
