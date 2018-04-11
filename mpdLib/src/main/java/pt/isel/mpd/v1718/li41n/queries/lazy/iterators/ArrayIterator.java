package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;

public class ArrayIterator<T> extends BaseIterator<T> {

    public ArrayIterator(T[] values) {
        super(asList(values).iterator());
    }

    @Override
    protected Optional<T> tryAdvance() {
        return prevIterator.hasNext() ? of(nextFromPrevWithCast()) : empty();
    }
}
