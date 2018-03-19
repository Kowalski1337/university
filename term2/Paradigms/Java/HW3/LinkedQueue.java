public class LinkedQueue extends AbstractQueue {
    private Node tail;
    private Node head;
    //private int size;

    protected void doEnqueue(Object element) {
        if (size == 0) {
            head = new Node(element, null);
            tail = head;
        } else {
            tail.next = new Node(element, null);
            tail = tail.next;
        }
    }

    protected Object doElement() {
        //assert size > 0;
        return head.value;
    }

    protected void doDequeue() {
        //Object next = doElement();
        head.value = null;
        head = head.next;
        //return next;
    }

    protected void doClear() {
        Node node = head;
        while (node != null) {
            Node newNode = node.next;
            node = null;
            node = newNode;
        }
    }

    public Object[] toArray() {
        Node node = head;
        Object[] result = new Object[size];
        int i = 0;

        while (node != null) {
            result[i++] = node.value;
            node = node.next;
        }

        return result;
    }

    protected LinkedQueue copy() {
        LinkedQueue res = new LinkedQueue();
        Node node = head;
        while (node != null) {
            res.enqueue(node.value);
            node = node.next;
        }
        res.size = size;
        return res;
    }

}