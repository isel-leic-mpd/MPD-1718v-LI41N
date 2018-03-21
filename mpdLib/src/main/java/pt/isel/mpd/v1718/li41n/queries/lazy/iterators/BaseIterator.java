package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class BaseIterator<T> implements Iterator<T> {
    private final Iterator<?> prevIterator;
    protected T nextElement = null;

    public BaseIterator(Iterator<?> prevIterator) {
        this.prevIterator = prevIterator;
    }

    @Override
    public final boolean hasNext() {
        nextElement = tryAdvance();
        return nextElement != null;
    }



    @Override
    public final T next() {
        if (nextElement == null) {
            throw new NoSuchElementException();
        }
        return nextElement;
    }

    protected  T nextFromPrev() {
        return (T)prevIterator.next();
    }

    protected boolean hasNextFromPrev() {
        return prevIterator.hasNext();
    }

    protected <R> R nextFromPrevWithCast() {
        return (R)prevIterator.next();
    }

    protected abstract T tryAdvance();
}
