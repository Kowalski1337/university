package queue;

import md5.Test;

import java.util.ArrayList;

public class GovnoTests {
    private static final Object[] m = {1, "2", new ArrayList<Integer>(), 4, 5};

    public static void main(String[] args) {
        ArrayQueue arrayQueue = new ArrayQueue();
        ArrayQueueADT arrayQueueADT = new ArrayQueueADT();

        System.out.println("Test enqueue");
        for (Object i : m) {
            arrayQueue.enqueue(i);
            ArrayQueueModule.enqueue(i);
            ArrayQueueADT.enqueue(arrayQueueADT, i);
        }

        System.out.println("Test size/isEmpty");
        System.out.println(arrayQueue.size());
        System.out.println(arrayQueue.isEmpty());
        System.out.println(ArrayQueueADT.size(arrayQueueADT));
        System.out.println(ArrayQueueADT.isEmpty(arrayQueueADT));
        System.out.println(ArrayQueueModule.size());
        System.out.println(ArrayQueueModule.isEmpty());

        System.out.println("Test toArray");
        Object[] objects1 = arrayQueue.toArray();
        Object[] objects2 = ArrayQueueModule.toArray();
        Object[] objects3 = ArrayQueueADT.toArray(arrayQueueADT);

        for (Object object : objects1) {
            System.out.println(object);
        }

        for (Object object : objects2) {
            System.out.println(object);
        }

        for (Object object : objects3) {
            System.out.println(object);
        }

        System.out.println("Test immutable/order/element/dequeue");

        while (!arrayQueue.isEmpty()){
            System.out.println(arrayQueue.element() + " " + arrayQueue.dequeue());
        }

        while (!ArrayQueueADT.isEmpty(arrayQueueADT)){
            System.out.println(ArrayQueueADT.element(arrayQueueADT) + " " + ArrayQueueADT.dequeue(arrayQueueADT));
        }

        while (!ArrayQueueModule.isEmpty()){
            System.out.println(ArrayQueueModule.element() + " " + ArrayQueueModule.dequeue());
        }

        System.out.println("Test clear");

        for (Object i : m) {
            arrayQueue.enqueue(i);
            ArrayQueueModule.enqueue(i);
            ArrayQueueADT.enqueue(arrayQueueADT, i);
        }

        arrayQueue.clear();
        ArrayQueueModule.clear();
        ArrayQueueADT.clear(arrayQueueADT);

        System.out.println(arrayQueue.size());
        System.out.println(ArrayQueueModule.size());
        System.out.println(ArrayQueueADT.size(arrayQueueADT));

        System.out.println(arrayQueue.isEmpty());
        System.out.println(ArrayQueueModule.isEmpty());
        System.out.println(ArrayQueueADT.isEmpty(arrayQueueADT));

        System.out.println("Check getting array from empty class");

        System.out.println(arrayQueue.toArray().length == 0);
        System.out.println(ArrayQueueADT.toArray(arrayQueueADT).length == 0);
        System.out.println(ArrayQueueModule.toArray().length == 0);
    }
}
