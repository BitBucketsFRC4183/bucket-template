package org.bitbuckets.lib.network;

import org.littletonrobotics.junction.inputs.LoggableInputs;

import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public interface ILogFactory {

    Loggable<Integer> intLogger(String id);
    Loggable<String> stringLogger(String id);
    Loggable<Double> doubleLogger(String id);

    Loggable<double[]> arrayLogger(String id);

    void periodicDoubleLogger(String id, Supplier<Double> data);

    <T extends LoggableInputs> void registerInputLoop(Supplier<T> constructor, Consumer<T> updateInputs, Consumer<T> updateDevices);

}
