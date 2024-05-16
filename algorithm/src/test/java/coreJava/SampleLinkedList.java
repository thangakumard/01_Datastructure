package coreJava;

import org.testng.annotations.Test;

import java.util.LinkedList;

public class SampleLinkedList {

    @Test
    private void test() {
        LinkedList<Integer> intList = new LinkedList<>();
        intList.push(10);
        intList.push(20);
        intList.push(30);

        System.out.print(intList.getFirst());
        System.out.print(intList.getLast());
        intList.add(1,40);
        System.out.print(intList);


    }

}
