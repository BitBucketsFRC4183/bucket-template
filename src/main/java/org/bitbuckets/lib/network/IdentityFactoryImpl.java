package org.bitbuckets.lib.network;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

//TODO someone write tests for this
public class IdentityFactoryImpl implements IIdentityFactory {

    class Record {
        final int parent;
        final int child;

        public Record(int parent, int child) {
            this.parent = parent;
            this.child = child;
        }
    }

    final List<Record> family = new ArrayList<>();
    final List<String> fullNameCache = new ArrayList<>();
    final List<String> nameCache = new ArrayList<>();

    int currentId = 0;

    @Override
    public int rootProcess(String name) {
        currentId++;

        nameCache.add(currentId, name);

        return currentId;
    }

    @Override
    public int childProcess(int parentId, String name) {
        currentId++;

        family.add(new Record(parentId, currentId));
        nameCache.add(currentId, name);

        return currentId;
    }

    @Override
    public String fullPath(int id) {
        return buildPath(id);
    }

    String buildPath(int id) {

        String possible = fullNameCache.get(id);
        if (possible != null) return possible;

        Deque<String> deque = new ArrayDeque<>();
        deque.addLast(nameCache.get(id));


        int searchId = id;
        for (;;) {

            Record parent = findParent(searchId);
            if (parent == null) break;
            searchId = parent.parent;

            deque.addLast(nameCache.get(parent.parent));
        }

        //w

        StringBuilder builder = new StringBuilder();

        while (!deque.isEmpty()) {
            builder.append(deque.pop());
        }

        String str = builder.toString();

        fullNameCache.add(id, str);

        return str;

    }

    Record findParent(int id) {
        for (Record record : family) {
            if (record.child == id) {
                return record; //there you go
            }
        }

        return null;
    }
}
