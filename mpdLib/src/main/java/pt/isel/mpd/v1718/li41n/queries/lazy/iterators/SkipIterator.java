package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;

public class SkipIterator<T> extends BaseIterator<T>  {
    private int skip;


    public SkipIterator(Iterator<T> prevIterator, int skip) {
        super(prevIterator);
        this.skip = skip;
    }

    @Override
    public T tryAdvance() {
        return --skip >= 0 && hasNextFromPrev() ? nextFromPrev() : null;
    }
}
