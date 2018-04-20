package pt.isel.mpd.v1718.li41n.misc.streams;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class StreamTests {
    @Test
    void shpuldJointStreamElements() {
        final List<String> strings = asList("Sport", "Lisboa", "Benfica");

        join(strings, ",");
        join(strings.stream(), ",");


    }


    private <T> String join(Stream<T> stream, String delimiter) {
        return stream
                .map(Object::toString)
                .collect(joining(delimiter));
    }

    private <T> String join(Iterable<T> iter, String delimiter) {
        StringBuilder str = new StringBuilder();
        Iterator<T> it = iter.iterator();
        while (it.hasNext()) {
            T t = it.next();
            str.append(t.toString() + (it.hasNext() ? delimiter : ""));


        }

        return str.toString();
    }
}
