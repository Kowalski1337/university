package ru.ifmo.rain.baydyuk.arrayset;

import java.util.*;

/**
 * Created by Rodion.
 */
public class ArraySet<E> extends AbstractSet<E> implements SortedSet<E> {
    private List<E> container;
    private Comparator<? super E> comparator;

    //Constructors
    public ArraySet() {
        this((Comparator<E>) null);
    }

    public ArraySet(Comparator<? super E> comparator) {
        container = Collections.emptyList();
        this.comparator = comparator;
    }

    public ArraySet(Collection<? extends E> container) {
        this(container, null);
    }

    public ArraySet(Collection<? extends E> container, Comparator<? super E> comparator) {

        this.comparator = comparator;
        TreeSet<E> tree = new TreeSet<>(comparator);
        tree.addAll(container);
        this.container = new ArrayList<>(tree);
    }

    public ArraySet(SortedSet<E> sortedSet) {
        container = new ArrayList<>(sortedSet);
        comparator = sortedSet.comparator();
    }

    private ArraySet(List<E> sortedSet, Comparator<? super E> comparator) {
        this.container = sortedSet;
        this.comparator = comparator;
    }

    //Set
    @Override
    @SuppressWarnings("unchecked")
    public boolean contains(Object o) {
        return Collections.binarySearch(container, (E) o, comparator) >= 0;
    }

    //AbstractSet

    @Override
    public Iterator<E> iterator() {
        return Collections.unmodifiableList(container).iterator();
    }

    @Override
    public int size() {
        return container == null ? 0 : container.size();
    }


    //SortedSet

    @Override
    public Comparator<? super E> comparator() {
        return comparator;
    }

    @Override
    public SortedSet<E> subSet(E fromElement, E toElement) {
        return subSet(fromElement, true, toElement, false);
    }

    @Override
    public SortedSet<E> headSet(E toElement) {
        return headSet(toElement, false);
    }

    @Override
    public SortedSet<E> tailSet(E fromElement) {

        return new ArraySet<>(container.subList(findFromInd(fromElement, true), container.size()), comparator);
    }

    @Override
    public E first() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return container.get(0);
    }

    @Override
    public E last() {
        if (size() == 0) {
            throw new NoSuchElementException();
        }
        return container.get(container.size() - 1);
    }

    private int findFromInd(E element, boolean inclusive) {
        int ind = Collections.binarySearch(container, element, comparator);
        if (ind < 0) {
            ind = ~ind;
        } else if (!inclusive) {
            ++ind;
        }
        return ind;
    }

    private int findToInd(E element, boolean inclusive) {
        int ind = Collections.binarySearch(container, element, comparator);
        if (ind < 0) {
            ind = ~ind - 1;
        } else if (!inclusive) {
            --ind;
        }
        return ind;
    }

    private SortedSet<E> subSet(E fromElement, boolean fromInc, E toElement, boolean toInc) {
        int fromI = findFromInd(fromElement, fromInc);
        int toI = findToInd(toElement, toInc) + 1;
        if (toI < fromI) {
            toI = fromI;
        }
        return new ArraySet<>(container.subList(fromI, toI), comparator);
    }


    private SortedSet<E> headSet(E toElement, boolean inc) {
        return new ArraySet<>(container.subList(0, findToInd(toElement, inc) + 1), comparator);
    }


}
