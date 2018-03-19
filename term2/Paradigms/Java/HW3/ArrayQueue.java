public class ArrayQueue extends AbstractQueue {
    private int head;
    private Object[] elements = new Object[10];

    protected void doEnqueue(Object element) {
        ensureCapacity(size + 1);
        elements[tail()] = element;
    }

    public Object doElement() {
        return elements[head];
    }

    protected void doDequeue() {
        elements[head] = null;
        head = (head + 1) % elements.length;
    }

    protected void doClear() {
        head = 0;
        elements = new Object[10];
    }

    public Object[] toArray() {
        return copy(size);
    }

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        } else {
            elements = copy(check(capacity));
            head = 0;
        }
    }

    private int check(int a) {
        if (a >= 1 << 30) {
            return Integer.MAX_VALUE;
        } else return 2 * a;
    }

    private Object[] copy(int length) {
        Object[] newArr = new Object[length];
        if (head + size > elements.length) {
            System.arraycopy(elements, head, newArr, 0, size - tail());
            System.arraycopy(elements, 0, newArr, size - tail(), tail());

        } else {
            System.arraycopy(elements, head, newArr, 0, size);
        }
        return newArr;
    }

    private int tail() {
        return (head + size) % elements.length;
    }

    protected ArrayQueue copy() {
        ArrayQueue res = new ArrayQueue();
        res.elements = new Object[elements.length];
        System.arraycopy(elements, 0, res.elements, 0, elements.length);
        res.size = size;
        res.head = head;
        return res;
    }


}