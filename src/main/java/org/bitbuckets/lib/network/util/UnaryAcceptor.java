package org.bitbuckets.lib.network.util;

import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class UnaryAcceptor<T> implements Consumer<UnaryOperator<T>> {

    final T object;

    public UnaryAcceptor(T object) {
        this.object = object;
    }

    @Override
    public void accept(UnaryOperator<T> tUnaryOperator) {
        tUnaryOperator.apply(object);
    }
}
