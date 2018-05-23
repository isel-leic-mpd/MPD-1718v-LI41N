package pt.isel.mpd.v1718.li41n.queries.lazy;


import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.FilterIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.LimitIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.MapIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.SkipIterator;


import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class QueriesLazy  {

    public static <T> Iterable<T> filter(Iterable<T> iter, Predicate<T> pred) {
        return new QueriesIterable<T>(() -> new FilterIterator(iter.iterator(), pred));

    }


    public static <T, R> Iterable<R> map(Iterable<T> iter, Function<T, R> mapper) {
        return new QueriesIterable<R>(() -> new MapIterator(iter.iterator(), mapper));
    }

    public static <T> Iterable<T> limit(Iterable<T> iter, int limit) {
        return new QueriesIterable<T>(() -> new LimitIterator(iter.iterator(), limit));
    }

    public static <T> Iterable<T> skip(Iterable<T> iter, int limit) {
        return new QueriesIterable<T>(() -> new SkipIterator<>(iter.iterator(), limit));
    }

    public static <T, K> Map<K, Collection<T>> groupBy(Collection<T> coll, Function<T, K> keyExtractor) {
        return null;
    }


    private static class QueriesIterable<T> implements Iterable<T> {
        private Supplier<Iterator<T>> iteratorSupplier;

        public QueriesIterable(Supplier<Iterator<T>> iteratorSupplier) {
            this.iteratorSupplier = iteratorSupplier;
        }

        @Override
        public Iterator<T> iterator() {
            return iteratorSupplier.get();
        }
    }
}
