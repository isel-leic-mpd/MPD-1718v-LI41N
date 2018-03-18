package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Function;
import java.util.function.Predicate;

public class FilterIterator<T> implements Iterator<T> {
    private final Iterator<T> prevIterator;
    private Predicate<T> pred;
    private T next = null;


    public FilterIterator(Iterator prevIterator, Predicate<T> pred) {
        this.prevIterator = prevIterator;
        this.pred = pred;
    }

    @Override
    public boolean hasNext() {
        while (prevIterator.hasNext()) {
            if(pred.test(next = prevIterator.next())) {
                return true;
            }
        }
        next = null;
        return false;
    }

    @Override
    public T next() {
        if(next == null) {
            throw new NoSuchElementException();
        }
        return next;
    }
}
