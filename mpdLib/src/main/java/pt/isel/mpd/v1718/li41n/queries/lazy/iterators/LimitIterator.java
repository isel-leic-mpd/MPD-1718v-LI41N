package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Predicate;

public class LimitIterator<T> implements Iterator<T> {
    private final Iterator<T> prevIterator;
    private int limit;
    private T next = null;


    public LimitIterator(Iterator prevIterator, int limit) {
        this.prevIterator = prevIterator;
        this.limit = limit;
    }

    @Override
    public boolean hasNext() {
        if (--limit >= 0 && prevIterator.hasNext()) {
            next = prevIterator.next();
            return true;
        }
        return false;
    }

    @Override
    public T next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        return next;
    }
}
