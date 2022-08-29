package coreJava.collections.set;

import org.testng.annotations.Test;

import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

public class Set03_SortedSet {

    @Test
    public void sortedSetSample() {
        TreeSet<Object> treeSet = new TreeSet<>();
        SortedSet sortedSet = Collections.synchronizedSortedSet(treeSet);

        sortedSet.add(45);
        sortedSet.add(15);
        sortedSet.add(99);
        sortedSet.add(70);
        sortedSet.add(65);
        sortedSet.add(30);
        sortedSet.add(10);
        sortedSet.add(10); // Duplicate record
        sortedSet.add(60);
        sortedSet.add(80);

        System.out.println("sortedSet\n" + sortedSet);

    }
}
