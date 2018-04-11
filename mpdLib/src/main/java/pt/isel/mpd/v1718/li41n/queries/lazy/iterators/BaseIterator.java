package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.empty;

public abstract class BaseIterator<T> implements Iterator<T> {
    protected final Iterator<?> prevIterator;
    protected Optional<T> curr;

    public BaseIterator(Iterator<?> prevIterator) {
        this.prevIterator = prevIterator;
        curr = empty();
    }

    @Override
    public final boolean hasNext() {
        if(!curr.isPresent()) {
            curr = tryAdvance();
        }
        return curr.isPresent();
    }



    @Override
    public final T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        T ret = curr.get();
        curr = empty();
        return ret;
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

    protected abstract Optional<T> tryAdvance();
}
