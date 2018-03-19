import java.util.function.Function;
import java.util.function.Predicate;

public interface Queue {
    //immutable(n): (elements[i]' == elements[i] i = 0..n-1)

    //Pre: element != null
    void enqueue(Object element);
    //Post: size = (size' + 1)  && immutable(size') && elements[size'] = element

    //Pre: size > 0
    Object element();
    //Post: ans = elements[0] && size' = size && immutable(size)

    //Pre: size > 0;
    Object dequeue();
    //Post: ans = elements[0] && size' = size - 1 && elements[i]' = elements[i+1] i = 0..size'-1

    //Pre:
    int size();
    //Post: ans = size && immutable(size)

    //Pre:
    boolean isEmpty();
    //Post: ans = (size == 0) && size = size' && immutable(size)

    //Pre:
    void clear();
    //post: size = 0

    //Pre:
    Object[] toArray();
    //Post: immutable(size') && result.length = size' && result[i] = elements[i] i=0..size'-1

    // pre queue != null
    Queue filter(Predicate<Object> predicate);
    //post R = queue of elements of class instance matching predicate in the original order

    // pre queue != null
    Queue map(Function<Object, Object> func);
    //post R = queue of elements of class instance with func applied to them in the original order
}