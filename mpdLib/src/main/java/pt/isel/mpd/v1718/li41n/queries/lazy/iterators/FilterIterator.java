package pt.isel.mpd.v1718.li41n.queries.lazy.iterators;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;

import static java.util.Optional.empty;

public class FilterIterator<T> extends BaseIterator<T> {
    private Predicate<T> pred;


    public FilterIterator(Iterator<T> prevIterator, Predicate<T> pred) {
        super(prevIterator);
        this.pred = pred;
    }

    @Override
    public Optional<T> tryAdvance() {
        Optional<T> nextElement = empty();
        while (hasNextFromPrev()) {
            T curr = nextFromPrev();
            if(pred.test(curr)) {
                nextElement = Optional.of(curr);
                break;
            }
        }
        return nextElement;
    }
}
