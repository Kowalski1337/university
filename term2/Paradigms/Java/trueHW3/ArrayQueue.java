public class ArrayQueue {
    private int head;
    private int size;
    private Object[] elements = new Object[10];
    //immutable(n): (elements[i]' == elements[i] i = 0..n-1)

    //Pre:
    public int size() {
        return size;
    }
    //Post: ans = size && immutable(size)

    //Pre:
    public boolean isEmpty() {
        return (size == 0);
    }
    //Post: ans = (size == 0) && size = size' && immutable(size)

    //Pre: element != null
    public void enqueue(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        elements[tail()] = element;
        size++;
    }
    //Post: size = (size' + 1)  && immutable(size') && elements[size'] = element

    private void ensureCapacity(int capacity) {
        if (capacity <= elements.length) {
            return;
        } else {
            elements = copy(capacity * 2);
            head = 0;
        }
    }


    //Pre: size > 0
    public Object element() {
        assert size > 0;
        return elements[head];
    }
    //Post: ans = elements[0] && size' = size && immutable(size)

    //Pre: size > 0
    public Object dequeue() {
        assert size > 0;
        Object next = elements[head];
        head = (head + 1) % elements.length;
        size--;
        return next;
    }
    //Post: ans = elements[0] && size' = size - 1 && elements[i]' = elements[i+1] i = 0..size'-1

    //Pre:
    public void clear() {
        head = 0;
        size = 0;
        elements = new Object[10];
    }
    //size' = 0

    //Pre: size > 0
    public Object remove() {
        Object next = peek();
        size--;
        elements[tail()] = null;
        return next;
    }
    //Post: ans = elements[size - 1] && size' = size - 1 && immutable(size)

    //Pre: size > 0
    public Object peek() {
        assert size > 0;
        return elements[tail() > 0 ? tail() - 1 : elements.length - 1];
    }
    //Post: ans = elements[size-1] && size' = size && immutable(size)

    //Pre: element != null
    public void push(Object element) {
        assert element != null;
        ensureCapacity(size + 1);
        if (head > 0) {
            head--;
        } else {
            head = elements.length - 1;
        }
        elements[head] = element;
        size++;
    }
    //Post: size' = (size + 1) && elements[0]' = element && elements[i]' = elements[i-1] i = i...size'-1

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

    //Pre:
    public Object[] toArray() {
        return copy(size);
    }
    //Post: immutable(size') && result[i] = elements[i] i=0..size'-1

    private int tail() {
        return (head + size) % elements.length;
    }

}