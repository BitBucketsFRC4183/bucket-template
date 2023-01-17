package org.bitbuckets.lib.logging;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public interface Receptor<T> {

    void process(Consumer<T> dataProcessor);

}
