package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.function.Predicate;

public class FilterIterator<T> extends BaseIterator<T> {
    private Predicate<T> pred;


    public FilterIterator(Iterator<T> prevIterator, Predicate<T> pred) {
        super(prevIterator);
        this.pred = pred;
    }

    @Override
    public T tryAdvance() {
        T nextElement = null;
        while (hasNextFromPrev()) {
            if(pred.test(nextElement = nextFromPrev())) {
                break;
            }
        }
        return nextElement;
    }
}
