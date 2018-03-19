public class ArrayQueueModule {
    private static int head;
    private static int size;
    private static Object[] elements = new Object[10];
    //immutable(n): (elements[i]' == elements[i] i = 0..n-1)

    //Pre:
    public static int size() {
        return size;
    }
    //Post: ans = size && immutable(size)

    //Pre: element != null
    public static void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        elements[tail()] = element;
        size++;
    }
    //Post: size = (size' + 1)  && immutable(size') && elements[size'] = element

    //Pre:
    public static Object[] toArray() {
        return copy(size);
    }
    //Post: immutable(size') && result[i] = elements[i] i=0..size'-1

    private static void ensureCapacity(int capacity) {
        if (capacity < elements.length) {
            return;
        } else {
            elements = copy(2 * capacity);
            head = 0;
        }
    }

    //Pre: size != 0
    public static Object element() {
        assert elements.length > 0;
        return elements[head];
    }
    //Post: ans = elements[0] && size' = size && immutable(size)

    //Pre: size != 0
    public static Object dequeue() {
        assert elements.length > 0;
        Object next = elements[head];
        elements[head] = null;
        head = (head + 1) % elements.length;
        size--;
        return next;
    }
    //Post: ans = elements[0] && size' = size - 1 && elements[i]' = elements[i+1] i = 0..size'-1

    //Pre:
    public static boolean isEmpty() {
        return size == 0;
    }
    //Post: ans = (size == 0) && size = size' && immutable(head', tail')

    //Pre:
    public static void clear() {
        head = 0;
        size = 0;
        elements = new Object[10];
    }
    //size' = 0

    //Pre: size != 0
    public static Object remove() {
        Object next = peek();
        size--;
        elements[tail()] = null;
        return next;
    }
    //Post: ans = elements[size - 1] && size' = size - 1 && immutable(size)

    //Pre: size != 0
    public static Object peek() {
        assert size > 0;
        return elements[tail() == 0 ? elements.length - 1 : tail() - 1];
    }
    //Post: ans = elements[size-1] && size' = size && immutable(size)

    //Pre: element != null
    public static void push(Object element) {
        assert element != null;
        ensureCapacity(ArrayQueueModule.size() + 1);
        if (head > 0) {
            head--;
        } else {
            head = elements.length - 1;
        }
        elements[head] = element;
        size++;
    }
    //Post: size' = (size + 1) && elements[0]' = element && elements[i]' = elements[i-1] i = i...size'-1

    private static Object[] copy(int length) {
        Object[] newArr = new Object[length];
        if (head + size > elements.length) {
            System.arraycopy(elements, head, newArr, 0, size - tail());
            System.arraycopy(elements, 0, newArr, size - tail(), tail());

        } else {
            System.arraycopy(elements, head, newArr, 0, size);
        }
        return newArr;
    }

    private static int tail() {
        return (head + size) % elements.length;
    }
}