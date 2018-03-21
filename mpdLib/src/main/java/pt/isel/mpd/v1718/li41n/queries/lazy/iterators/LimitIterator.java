package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;

public class LimitIterator<T> extends BaseIterator<T> {
    private int limit;

    public LimitIterator(Iterator<T> prevIterator, int limit) {
        super(prevIterator);
        this.limit = limit;
    }

    @Override
    public T tryAdvance() {
        return --limit >= 0 && hasNextFromPrev() ? nextFromPrev() : null;
    }
}
