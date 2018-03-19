import java.util.function.Function;
import java.util.function.Predicate;

abstract public class AbstractQueue implements Queue {

    protected int size;

    public void enqueue(Object element){
        assert element != null;
        doEnqueue(element);
        size++;
    }

    protected abstract void doEnqueue(Object element);

    public Object element(){
        assert size > 0;
        return doElement();
    }

    protected abstract Object doElement();

    public Object dequeue(){
        Object x = element();
        size--;
        doDequeue();
        return x;
    }

    protected abstract void doDequeue();

    public void clear(){
        size = 0;
        doClear();
    }

    protected abstract void doClear();

    abstract public Object[] toArray();

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Queue filter(Predicate<Object> predicate) {
        Queue ret = copy();
        int s = ret.size();

        for (int i = 0; i < s; i++) {
            Object elem = ret.dequeue();
            if (predicate.test(elem)) {
                ret.enqueue(elem);
            }
        }

        return ret;
    }

    public Queue map(Function<Object, Object> func) {
        Queue ret = copy();
        int s = ret.size();
        for (int i = 0; i < s; i++) {
            ret.enqueue(func.apply(ret.dequeue()));
        }

        return ret;
    }

    protected abstract Queue copy();
}
