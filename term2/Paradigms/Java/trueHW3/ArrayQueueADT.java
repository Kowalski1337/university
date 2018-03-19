import java.util.AbstractQueue;

public class ArrayQueueADT {
    private int head;
    private int size;
    private Object[] elements = new Object[10];
    //immutable(n): (elements[i]' == elements[i] i = 0..n-1)

    //Pre: queue != null
    public static int size(ArrayQueueADT queue) {
        return queue.size;
    }
    //Post: ans = size && immutable(size)

    //Pre: queue != null
    public static Object[] toArray(ArrayQueueADT queue) {
        return copy(queue, queue.size);
    }
    //Post: immutable(size') && result[i] = elements[i] i=0..size'-1

    //Pre: element != null && queue != null
    public static void enqueue(ArrayQueueADT queue, Object element) {
        assert element != null;
        ensureCopasity(queue, queue.size + 1);
        queue.elements[tail(queue)] = element;
        queue.size++;
    }
    //Post: size = (size' + 1)  && immutable(size') && elements[size'] = element

    private static void ensureCopasity(ArrayQueueADT queue, int copasity) {
        if (copasity < queue.elements.length) {
            return;
        } else {
            queue.elements = copy(queue, copasity * 2);
            queue.head = 0;
        }
    }

    //Pre: size != 0 && queue != null
    public static Object element(ArrayQueueADT queue) {
        assert queue.elements.length > 0;
        return queue.elements[queue.head];
    }
    //Post: ans = elements[0] && size' = size && immutable(size)

    //Pre: size != 0 && queue != null
    public static Object dequeue(ArrayQueueADT queue) {
        assert queue.elements.length > 0;
        Object next = queue.elements[queue.head];
        queue.head = (queue.head + 1) % queue.elements.length;
        queue.size--;
        return next;
    }
    //Post: ans = elements[0] && size' = size - 1 && elements[i]' = elements[i+1] i = 0..size'-1

    //Pre: queue != null
    public static boolean isEmpty(ArrayQueueADT queue) {
        return queue.size == 0;
    }
    //Post: ans = (size == 0) && size = size' && immutable(head', tail')

    //Pre: queue != null
    public static void clear(ArrayQueueADT queue) {
        queue.head = 0;
        queue.size = 0;
        queue.elements = new Object[10];
    }
    //size' = 0

    //Pre: size != 0
    public static Object remove(ArrayQueueADT queue) {
        Object next = peek(queue);
        queue.size--;
        queue.elements[tail(queue)] = null;
        return next;
    }
    //Post: ans = elements[size - 1] && size' = size - 1 && immutable(size)

    //Pre: size != 0
    public static Object peek(ArrayQueueADT queue) {
        assert queue.size > 0;
        return queue.elements[tail(queue) > 0 ? tail(queue) - 1 : queue.elements.length - 1];
    }
    //Post: ans = elements[size-1] && size' = size && immutable(size)

    //Pre: element != null
    public static void push(ArrayQueueADT queue, Object element) {
        assert element != null;
        ensureCopasity(queue, queue.size + 1);
        if (queue.head > 0) {
            queue.head--;
        } else {
            queue.head = queue.elements.length - 1;
        }
        queue.elements[queue.head] = element;
        queue.size++;

    }
    //Post: size' = (size + 1) && elements[0]' = element && elements[i]' = elements[i-1] i = i...size'-1

    private static Object[] copy(ArrayQueueADT queue, int length) {
        Object[] newArr = new Object[length];
        if (queue.head + queue.size > queue.elements.length) {
            System.arraycopy(queue.elements, queue.head, newArr, 0, queue.size - tail(queue));
            System.arraycopy(queue.elements, 0, newArr, queue.size - tail(queue), tail(queue));

        } else {
            System.arraycopy(queue.elements, queue.head, newArr, 0, queue.size);
        }
        return newArr;
    }

    private static int tail(ArrayQueueADT queue) {
        return (queue.head + queue.size) % queue.elements.length;
    }

}