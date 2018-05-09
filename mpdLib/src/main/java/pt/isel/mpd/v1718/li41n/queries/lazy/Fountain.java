package pt.isel.mpd.v1718.li41n.queries.lazy;

import pt.isel.mpd.v1718.li41n.queries.lazy.iterators.BaseIterator;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import static java.util.Optional.empty;

public interface Fountain<T> extends Advancer<T> {
    static <T> Fountain<T> of(Iterable<T> iter) {
        final Iterator<T> iterator = iter.iterator();
        return consumer -> {
            if(iterator.hasNext()) {
                consumer.accept(iterator.next());
                return true;
            }
            return false;
        };
    }



    default Fountain<T> skip(final int skip) {
        int[] skipped = {0};
        return consumer -> {
            while (skipped[0] != skip) {
                if(!this.tryAdvance(__ -> { })) {
                    return false;
                }
                ++skipped[0];
            }
            return tryAdvance(consumer);
        };
    }

    default Fountain<T> limit(int limit) {
        int[] count = {0};
        return consumer -> count[0]++ < limit && tryAdvance(consumer);
    }


    default  <R> Fountain<R> map(Function<T, R> mapper) {
        return consumer -> this.tryAdvance(t -> consumer.accept(mapper.apply(t)));
    }

    default Fountain<T> filter(Predicate<T> pred) {
        return consumer -> {
            boolean[] passed = {false};
            while(!passed[0] && this.tryAdvance(t -> {
                if(pred.test(t)) {
                    passed[0] = true;
                    consumer.accept(t);
                }
            }));

            return passed[0];
        };
    }


    default void forEach(Consumer<T> consumer) {
        while (this.tryAdvance(consumer));
    }

    default Iterator<T> iterator() {
        return new BaseIterator(null) {
            @Override
            protected Optional<T> tryAdvance() {
                Optional<T>[] ret = new Optional[]{empty()};

                Fountain.this.tryAdvance(t -> ret[0] = Optional.of(t));
                return ret[0];
            }
        };
    }
}
