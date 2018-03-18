package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SkipIterator<T> implements Iterator<T> {
    private final Iterator<T> prevIterator;
    private int skip;
    private T next = null;


    public SkipIterator(Iterator prevIterator, int skip) {
        this.prevIterator = prevIterator;
        this.skip = skip;
    }

    @Override
    public boolean hasNext() {
        if (--skip >= 0 && prevIterator.hasNext()) {
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
