package org.bitbuckets.lib.abstractions.log;

import java.util.function.Consumer;

public interface IDataLogger<T> {

    /**
     * Use this for tests
     */
    static <T> IDataLogger<T> TEST() {
        return dataProcessor -> {
            //do nothing
        };
    }

    void process(Consumer<T> dataProcessor);

}
