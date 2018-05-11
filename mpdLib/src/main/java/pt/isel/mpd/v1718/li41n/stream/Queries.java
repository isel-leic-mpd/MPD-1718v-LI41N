package pt.isel.mpd.v1718.li41n.stream;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Queries {
    public static <T> Stream<T> collapse(Stream<T> src) {
        return StreamSupport.stream(new CollapseSpliterator(src.spliterator()), false);
    }


    public static <T, U, K, R> Stream<R> join(Stream<T> str1, Stream<U> str2, BiFunction<T, U, R> mapper, Function<T, K> tkExtractor, Function<U, K> ukExtractor) {
        return StreamSupport.stream(new JoinSpliterator(str1.spliterator(), str2.spliterator(), mapper, tkExtractor, ukExtractor), false);
    }

    public static <T, U, K, R> Stream<R> join1(Stream<T> str1, Stream<U> str2, BiFunction<T, U, R> mapper, Function<T, K> tkExtractor, Function<U, K> ukExtractor) {
        final Stream<Pair<T,U>> stream = StreamSupport.stream(new JoinInPairsSpliterator(str1.spliterator(), str2.spliterator(),  tkExtractor, ukExtractor), false)
                .distinct();
        return stream
                .map(pair -> mapper.apply(pair.t, pair.u));
    }


    private static class CollapseSpliterator<T> extends Spliterators.AbstractSpliterator<T> {

        Optional<T> prev = Optional.empty();
        private Spliterator<T> src;

        public CollapseSpliterator(Spliterator<T> src) {
            super(src.estimateSize(), src.characteristics() & Spliterator.SIZED);

            this.src = src;
        }

        @Override
        public boolean tryAdvance(Consumer<? super T> action) {
            Optional<T> prevOld = prev;
            while (
                    src.tryAdvance(t -> {
                        if (!prev.isPresent() || !Objects.equals(t, prev.get())) {
                            action.accept(t);
                            prev = Optional.ofNullable(t);
                        }
                    })
                            &&
                            prevOld == prev
                    ) ;
            return prevOld != prev;

        }
    }

    private static class JoinSpliterator<T, U, K, R> extends Spliterators.AbstractSpliterator<R> {
        private final Spliterator<T> spliterator1;
        private final Spliterator<U> spliterator2;
        private final BiFunction<T, U, R> mapper;
        private final Function<T, K> tkExtractor;
        private final Function<U, K> ukExtractor;
        private Map<K, Pair<T, U>> mappedValues;
        private Spliterator<Pair<T, U>> mappedValuesSpliterator;


        public JoinSpliterator(Spliterator<T> spliterator1, Spliterator<U> spliterator2, BiFunction<T, U, R> mapper, Function<T, K> tkExtractor, Function<U, K> ukExtractor) {
            super(spliterator1.estimateSize()+spliterator2.estimateSize(), spliterator1.characteristics() & spliterator2.characteristics() & ~Spliterator.SIZED);

            this.spliterator1 = spliterator1;
            this.spliterator2 = spliterator2;
            this.mapper = mapper;
            this.tkExtractor = tkExtractor;
            this.ukExtractor = ukExtractor;
        }

        @Override
        public boolean tryAdvance(Consumer<? super R> action) {
            if(mappedValues == null) {
                fillMappedValues();
            }

            if(advanceInSpliterator1(action)) {
                return true;
            }

            return advanceInMappedValuesSpliterator(action);
        }

        private boolean advanceInMappedValuesSpliterator(Consumer<? super R> action) {
            if(mappedValuesSpliterator == null)
                mappedValuesSpliterator = mappedValues.values().stream().filter(p -> p.t == null).spliterator();

            return mappedValuesSpliterator.tryAdvance(pair -> action.accept(mapper.apply(pair.t, pair.u)));
        }

        private boolean advanceInSpliterator1(Consumer<? super R> action) {
            final boolean[] joined = {false};
            while (spliterator1.tryAdvance(t -> joined[0] = joinPair(t, action)) && !joined[0]);
            return joined[0];
        }

        private boolean joinPair(T t, Consumer<? super R> action) {
            final K key = tkExtractor.apply(t);
            Pair<T, U> pair = mappedValues.get(key);
            if(pair != null) {
                if(pair.t == null) {
                    pair.t = t;
                } else {
                    // repeated value
                    return false;
                }
            } else {
                pair = new Pair<>(t, null);
                mappedValues.put(key, pair);
            }
            action.accept(mapper.apply(pair.t, pair.u));
            return true;

        }

        private void fillMappedValues() {
            mappedValues = new HashMap<>();
            while (spliterator2.tryAdvance(u -> mappedValues.putIfAbsent(ukExtractor.apply(u), new Pair<>(null, u))));
        }

    }

    private static class JoinInPairsSpliterator<T, U, K> extends Spliterators.AbstractSpliterator<Pair<T,U>> {
        private final Spliterator<T> spliterator1;
        private final Spliterator<U> spliterator2;

        private final Function<T, K> tkExtractor;
        private final Function<U, K> ukExtractor;
        private Map<K, Pair<T, U>> mappedValues;
        private Spliterator<Pair<T, U>> mappedValuesSpliterator;


        public JoinInPairsSpliterator(Spliterator<T> spliterator1, Spliterator<U> spliterator2, Function<T, K> tkExtractor, Function<U, K> ukExtractor) {
            super(spliterator1.estimateSize()+spliterator2.estimateSize(), spliterator1.characteristics() & spliterator2.characteristics() & ~Spliterator.SIZED);

            this.spliterator1 = spliterator1;
            this.spliterator2 = spliterator2;
            this.tkExtractor = tkExtractor;
            this.ukExtractor = ukExtractor;
        }

        @Override
        public boolean tryAdvance(Consumer<? super Pair<T,U>> action) {
            return false;
        }

    }

    private static class Pair<T, U> {
        public T t;
        public U u;

        public Pair(T t, U u) {
            this.t = t;
            this.u = u;
        }

        @Override
        public boolean equals(Object o) {
            // TODO
            return true;
        }

        @Override
        public int hashCode() {
            // TODO
            return 0;
        }
    }

}
