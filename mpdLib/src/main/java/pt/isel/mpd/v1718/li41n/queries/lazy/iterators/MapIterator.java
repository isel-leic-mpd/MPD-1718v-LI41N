package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.function.Function;

public class MapIterator<T, R> extends BaseIterator<R> {
    private final Function<T, R> mapper;

    public MapIterator(Iterator<T> prevIterator, Function<T, R> mapper) {
        super(prevIterator);
        this.mapper = mapper;
    }


    @Override
    public R tryAdvance() {
        return hasNextFromPrev()
                ? mapper.apply(nextFromPrevWithCast())
                : null;
    }
}
