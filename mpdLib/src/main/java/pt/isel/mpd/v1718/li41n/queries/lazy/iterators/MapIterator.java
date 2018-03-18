package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class MapIterator<T, R> implements Iterator<R> {
    private final Iterator<T> prevIterator;
    private final Function<T, R> mapper;

    public MapIterator(Iterator<T> prevIterator, Function<T, R> mapper) {
        this.prevIterator = prevIterator;
        this.mapper = mapper;
    }

    @Override
    public boolean hasNext() {
        return prevIterator.hasNext();
    }

    @Override
    public R next() {
        return mapper.apply(prevIterator.next());
    }
}
