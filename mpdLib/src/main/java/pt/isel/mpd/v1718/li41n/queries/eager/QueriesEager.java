package pt.isel.mpd.v1718.li41n.queries.eager;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;

public class QueriesEager {

    public static <T> Iterable<T> filter(Iterable<T> coll, Predicate<T> pred) {
        Collection<T> resColl = new ArrayList<>();
        for (T t : coll) {
            if(pred.test(t)) {
                resColl.add(t);
            }
        }
        return resColl;
    }


    public static <T, R> Collection<R> map(Collection<T> coll, Function<T, R> mapper) {
        Collection<R> resColl = new ArrayList<>();
        for (T t : coll) {
            resColl.add(mapper.apply(t));

        }
        return resColl;
    }


    public static <T, K> Map<K, Collection<T>> groupBy(Collection<T> coll, Function<T, K> keyExtractor) {
        Map<K, Collection<T>> map = new HashMap<>();
        for (T t : coll) {
            K key = keyExtractor.apply(t);
            Collection<T> group = map.get(key);
            if(group == null) {
                group = new ArrayList<>();
                map.put(key, group);
            }
            group.add(t);
        }
        return map;
    }


    public static <T> Iterable<T> limit(Iterable<T> coll, int limit) {
        Collection<T> resColl = new ArrayList<>();
        for (T t : coll) {
            if(--limit == 0) {
                break;
            }
            resColl.add(t);
        }
        return resColl;
    }

    public static <T> Iterable<T> skip(Iterable<T> coll, int toSkip) {
        Collection<T> resColl = new ArrayList<>();

        for (T t : coll) {
            if(--toSkip >= 0) {
                continue;
            }
            resColl.add(t);
        }
        return resColl;
    }


}
