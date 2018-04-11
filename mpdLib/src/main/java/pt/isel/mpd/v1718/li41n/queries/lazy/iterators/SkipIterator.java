package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.Optional;

import static java.util.Optional.empty;
import static java.util.Optional.of;

public class SkipIterator<T> extends BaseIterator<T>  {
    private int skip;


    public SkipIterator(Iterator<T> prevIterator, int skip) {
        super(prevIterator);
        this.skip = skip;
    }

    @Override
    public Optional<T> tryAdvance() {
        while (skip != 0) {
            if(!hasNextFromPrev()) {
                return empty();
            }
            nextFromPrev();
            --skip;
        }
        return hasNextFromPrev() ? of(nextFromPrev()) : empty();
    }
}
