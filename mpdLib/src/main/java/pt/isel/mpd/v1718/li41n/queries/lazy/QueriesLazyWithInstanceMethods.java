package pt.isel.mpd.v1718.li41n.queries.lazy;

import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.FilterIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.LimitIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.MapIterator;
import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.SkipIterator;

import java.util.Iterator;
import java.util.function.Function;
import java.util.function.Predicate;

public class QueriesLazyWithInstanceMethods<T> implements Iterable<T> {
    private Iterable<T> iter;

    public QueriesLazyWithInstanceMethods(Iterable<T> iter) {

        this.iter = iter;
    }

    @Override
    public Iterator<T> iterator() {
        return iter.iterator();
    }

    public static <T> QueriesLazyWithInstanceMethods<T> of(Iterable<T> iter) {
        return new QueriesLazyWithInstanceMethods<>(iter);
    }

    public QueriesLazyWithInstanceMethods<T> skip(int skip) {
        return new QueriesLazyWithInstanceMethods<T>(
                () -> new SkipIterator<>(iter.iterator(), skip)
        );
    }

    public QueriesLazyWithInstanceMethods<T> limit(int limit) {
        return new QueriesLazyWithInstanceMethods<T>(
                () -> new LimitIterator(iter.iterator(), limit)
        );

    }

    public <R> QueriesLazyWithInstanceMethods<R> map(Function<T, R> mapper) {
        return new QueriesLazyWithInstanceMethods<R>(
                () -> new MapIterator<>(iter.iterator(), mapper)
        );
    }

    public QueriesLazyWithInstanceMethods<T> filter(Predicate<T> pred) {
        return new QueriesLazyWithInstanceMethods<T>(
                () -> new FilterIterator<>(iter.iterator(), pred)
        );
    }
}
