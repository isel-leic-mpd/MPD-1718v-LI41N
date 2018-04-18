package pt.isel.mpd.v1718.li41n.queries.lazy;

import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.FilterIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.LimitIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.MapIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.SkipIterator;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FountainSource<T> extends Iterable<T> {
    static <T> FountainSource<T> of(Iterable<T> iter) {
        return () -> iter.iterator();
    }

    @Override
    Iterator<T> iterator();

    default FountainSource<T> skip(int skip) {
        return () -> new SkipIterator<>(this.iterator(), skip);
    }

    default FountainSource<T> limit(int limit) {
        return () -> new LimitIterator(iterator(), limit);
    }


    default  <R> FountainSource<R> map(Function<T, R> mapper) {
        return () -> new MapIterator<>(iterator(), mapper);
    }

    default FountainSource<T> filter(Predicate<T> pred) {
        return () -> new FilterIterator<>(iterator(), pred);
    }
}
