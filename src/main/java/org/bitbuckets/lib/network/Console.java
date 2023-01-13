package org.bitbuckets.lib.network;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.util.datalog.DataLog;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInLayouts;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardContainer;
import org.bitbuckets.lib.IManageIdentities;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

//TODO fix networking
/**
 * TODO make this optimized and not garbo
 */
public class Console implements INetworking, IManageIdentities, INetworkedFactory {

    boolean allowNetwork = false;


    //This is really dumb
    final List<ShuffleboardContainer> containers = new ArrayList<>();
    final List<List<Integer>> relationships = new ArrayList<>();

    int currentId = 0;


    @Override
    public void flagError(int process, String error) {
        //I am  too lazy to actually set stuff up

        throw new IllegalStateException(error);
    }

    @Override
    public int flagTimingStart(int process, String taskName) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void flagTimingStop(int timingID) {
        throw new UnsupportedOperationException();
    }

    //shitcode
    @Override
    public int childProcess(int a, String name) {
        currentId++;

        List<Integer> children = relationships.get(a);
        if (children == null) {
            relationships.add(children = new ArrayList<>());
        }
        children.add(currentId);
        containers.add(currentId, containers.get(a).getLayout(name, BuiltInLayouts.kList));

        return currentId;
    }

    //always networked
    @SuppressWarnings("all")
    @Override
    public <T> Supplier<T> persistent(int processId, String id, T defaultValue) {
        GenericEntry e = containers.get(processId).add("h",defaultValue ).getEntry();

        return () -> (T) e.get().getValue();
    }

    //typically not networked
    @SuppressWarnings("all")
    @Override
    public <T> Consumer<T> logger(int processId, String id, T restingValue) {
        GenericEntry e = containers.get(processId).add("h",restingValue ).getEntry();


        return (j) -> {
            e.setValue(restingValue);
        };
    }
}
