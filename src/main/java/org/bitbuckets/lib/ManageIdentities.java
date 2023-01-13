package org.bitbuckets.lib;

import java.util.ArrayList;
import java.util.List;

public class ManageIdentities implements IManageIdentities {

    final List<String> strings = new ArrayList<>();
    int last = 0;


    @Override
    public int childProcess(int a, String name) {
        int nextId = last++;
        strings.add(nextId, name);


        return nextId;
    }
}
