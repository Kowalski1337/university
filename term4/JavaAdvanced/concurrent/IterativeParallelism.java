package ru.ifmo.rain.baydyuk.concurrent;

import info.kgeorgiy.java.advanced.concurrent.ListIP;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * The class computes some function for a list of elements.
 *
 * @author Vadim Baydyuk
 */
public class IterativeParallelism implements ListIP {

    private <T> List<Stream<? extends T>> toPart(final int threads, final List<? extends T> list) {
        int threadsNumber = threads == 0 ? 1 : threads > list.size() ? list.size() : threads;
        List<Stream<? extends T>> parts = new ArrayList<>();
        int left = 0;
        int length = list.size() / threadsNumber;
        int t = list.size() % threadsNumber;
        int right = length + (t-- > 0 ? 1 : 0);
        for (int i = 0; i < threadsNumber; i++) {
            parts.add(list.subList(left, right).stream());
            left = right;
            right += length + (t-- > 0 ? 1 : 0);
        }
        return parts;
    }

    private static abstract class ParallelWorker<R> implements Runnable {

        private R result;

        void setResult(R result) {
            this.result = result;
        }

        R getResult() {
            return result;
        }

    }

    private <T, R> R parallelism(int threads, List<? extends T> values,
                                 Function<Stream<? extends T>, R> action,
                                 Function<? super Stream<R>, R> resultAction) throws InterruptedException {
        List<Stream<? extends T>> parts = toPart(threads, values);
        List<Thread> threadList = new ArrayList<>();
        List<ParallelWorker<R>> workers = new ArrayList<>();
        for (Stream<? extends T> part : parts) {
            ParallelWorker<R> worker = new ParallelWorker<>() {
                @Override
                public void run() {
                    setResult(action.apply(part));
                }
            };
            workers.add(worker);
            Thread thread = new Thread(worker);
            threadList.add(thread);
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
        return resultAction.apply(workers.stream().map(ParallelWorker::getResult));
    }

    /**
     * Returns the concatenation of the string representation of the list elements.
     *
     * @param threads number of threads
     * @param values  list of elements
     * @return concatenation of the string representation of the list elements
     * @throws InterruptedException if threads has failed
     */
    @Override
    public String join(int threads, List<?> values) throws InterruptedException {
        StringBuilder result = new StringBuilder();
        map(threads, values, Object::toString).forEach(result::append);
        return result.toString();
    }

    /**
     * Returns a list containing the elements satisfy the predicate.
     *
     * @param threads   number of threads
     * @param values    list of elements
     * @param predicate predicate for list elements
     * @param <T>       type of list elements
     * @return list of the elements satisfy the predicate
     * @throws InterruptedException if threads has failed
     */
    @Override
    public <T> List<T> filter(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return parallelism(threads, values, o -> o.filter(predicate).collect(Collectors.toList()),
                o -> o.reduce(new ArrayList<>(), (o1, o2) -> {
                    o1.addAll(o2);
                    return o1;
                }));
    }

    /**
     * Returns a list containing the results of the function.
     *
     * @param threads number of threads
     * @param values  list of elements
     * @param f       function for list elements
     * @param <T>     type of list elements
     * @param <U>     type of function's result
     * @return list of the results of the function
     * @throws InterruptedException if threads has failed
     */
    @Override
    public <T, U> List<U> map(int threads, List<? extends T> values, Function<? super T, ? extends U> f) throws InterruptedException {
        return parallelism(threads, values, o -> o.map(f).collect(Collectors.toList()),
                o -> o.reduce(new ArrayList<>(), (o1, o2) -> {
                    o1.addAll(o2);
                    return o1;
                }));
    }

    /**
     * Returns the greatest element of the list elements.
     *
     * @param threads    number of threads
     * @param values     list of elements
     * @param comparator comparator for search maximum
     * @param <T>        type of list elements
     * @return maximum of the list elements
     * @throws InterruptedException if threads has failed
     */
    @Override
    public <T> T maximum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        Function<Stream<? extends T>, T> func = o -> o.max(comparator).orElse(null);
        return parallelism(threads, values, func, func);
    }

    /**
     * Returns the least element of the list elements.
     *
     * @param threads    number of threads
     * @param values     list of elements
     * @param comparator comparator for search minimum
     * @param <T>        type of list elements
     * @return minimum of the list elements
     * @throws InterruptedException if threads has failed
     */
    @Override
    public <T> T minimum(int threads, List<? extends T> values, Comparator<? super T> comparator) throws InterruptedException {
        return maximum(threads, values, comparator.reversed());
    }

    /**
     * Checks that all the list elements satisfy the predicate.
     *
     * @param threads   number of threads
     * @param values    list of elements
     * @param predicate predicate for list elements
     * @param <T>       type of list elements
     * @return if all the elements satisfy the predicate true, else false
     * @throws InterruptedException if threads has failed
     */
    @Override
    public <T> boolean all(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return parallelism(threads, values, o -> o.allMatch(predicate), o -> o.allMatch(Predicate.isEqual(true)));
    }

    /**
     * Checks that there is an element of the list that satisfies the predicate.
     *
     * @param threads   number of threads
     * @param values    list of elements
     * @param predicate predicate for list elements
     * @param <T>       type of list elements
     * @return if any list element satisfies the predicate true, else false
     * @throws InterruptedException if threads has failed
     */
    @Override
    public <T> boolean any(int threads, List<? extends T> values, Predicate<? super T> predicate) throws InterruptedException {
        return !all(threads, values, predicate.negate());
    }
}
