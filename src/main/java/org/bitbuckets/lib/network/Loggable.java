package org.bitbuckets.lib.network;

import java.util.function.Consumer;

public interface Loggable<T> extends Consumer<T> {

    void log(T object);

    @Override
    default void accept(T t) {
        log(t);
    }
}
