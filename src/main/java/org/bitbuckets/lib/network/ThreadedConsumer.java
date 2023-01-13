package org.bitbuckets.lib.network;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

/**
 * This exists so i can make threaded sends or whatever LMAO
 * @param <T>
 */
public class ThreadedConsumer<T> implements Consumer<T> {

    static Executor LOGGING_THREAD = Executors.newSingleThreadExecutor();

    final Consumer<T>[] runThreaded;

    long cacheMilis = System.currentTimeMillis();

    @SafeVarargs
    public ThreadedConsumer(Consumer<T>... runThreaded) {
        this.runThreaded = runThreaded;
    }

    @Override
    public void accept(T t) {
        long now = System.currentTimeMillis();
        long delta = now - cacheMilis;

        if (delta > 50) {
            LOGGING_THREAD.execute(() -> {
                for (Consumer<T> consumer : runThreaded) {
                    consumer.accept(t);
                }
            });
        }
    }
}
