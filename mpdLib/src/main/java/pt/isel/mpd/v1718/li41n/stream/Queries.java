package pt.isel.mpd.v1718.li41n.stream;

import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Queries {
    public static <T> Stream<T> collapse(Stream<T> src) {
        return StreamSupport.stream(new CollapseSpliterator(src.spliterator()), false);
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
                        if(!prev.isPresent() || !Objects.equals(t, prev.get())) {
                            action.accept(t);
                            prev = Optional.ofNullable(t);
                        }
                    })
                    &&
                    prevOld == prev
             );
            return prevOld != prev;

        }
    }
}
