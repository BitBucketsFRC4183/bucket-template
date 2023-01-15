package org.bitbuckets.lib.network.util;

import org.bitbuckets.lib.network.Loggable;

import java.util.function.Consumer;

/**
 * This exists so i can make threaded sends or whatever LMAO
 * @param <T>
 */
public class CachedConsumer<T> implements Loggable<T> {

    final Consumer<T> run;

    public CachedConsumer(Consumer<T> run) {
        this.run = run;
    }

    long cacheMilis = System.currentTimeMillis();
    T cachedT = null;

    @Override
    public void log(T t) {
        long now = System.currentTimeMillis();
        long delta = now - cacheMilis;

        if (t == cachedT || t.equals(cachedT)) return; //Don't send to logs/NT if it's the same value
        if (delta < 20) return; //Don't send to logs/NT if it hasn't been more than 1 periodic since the last send


        run.accept(t); //we good
        cacheMilis = now;
    }

}
