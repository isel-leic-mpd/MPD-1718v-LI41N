package pt.isel.mpd.v1718.li41n.queries.lazy;

import java.util.function.Consumer;

public interface Advancer<T> {
    boolean tryAdvance(Consumer<T> consumer);
}
