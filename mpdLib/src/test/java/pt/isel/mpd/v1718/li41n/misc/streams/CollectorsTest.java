package pt.isel.mpd.v1718.li41n.misc.streams;


import org.junit.Test;
import pt.isel.mpd.v1718.li41n.stream.ListCollector;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.Arrays.asList;
import static junit.framework.TestCase.assertEquals;

public class CollectorsTest {

    @Test
    public void shouldCollectAStreamToAListWithACollector() {
        final int MAX = 50;

        final List<String> src = asList("1", "2", "3", "4", "5", "6", "7");

        final List<String> res =
//                IntStream.range(0, MAX)
//                .mapToObj(i -> i)
//                .collect(Collectors.toList())

                src
                .stream()
                .parallel()
                .collect(new ListCollector<>());

        assertEquals(MAX, res.size());

    }

    @Test
    public void shouldCollectAStreamToAListUsingCollectTreeArgumentsOverload() {

        final int MAX = 1_00;

        final ArrayList<Integer> identity = new ArrayList<>();
        System.out.println("Identity hashcode: " + identity.hashCode());
        final List<Integer> res = IntStream.range(0, MAX).mapToObj(i -> i).parallel()
                .collect(
                        ArrayList::new,
                        ArrayList::add,
                        ArrayList::addAll
                );

        assertEquals(MAX, res.size());
    }


    @Test
    public void shouldCollectAStreamToAListUsingReduce() {

        // This implementation is wrong because is not immutable and unpredicted behaviour can
        // occur on parallel streams
        final int MAX = 1_000_000;

        final ArrayList<Integer> identity = new ArrayList<>();
        System.out.println("Identity hashcode: " + identity.hashCode());
        final List<Integer> res = IntStream.range(0, MAX).mapToObj(i -> i).parallel()
                .reduce(
                        identity,
                        (l, t) -> {
                            l.add(t);
//                            System.out.println(MessageFormat.format("accumulator called with list {0} ({2}) and element {1}. equals identnty {3} ", l, t, l.hashCode(), l == identity));
//                            return l;
                            final ArrayList<Integer> elems = new ArrayList<>();
                            elems.addAll(l);
                            elems.add(t);
                            return elems;

                        },
                        (l1, l2) -> {
                            //System.out.println(MessageFormat.format("combiner called with list1 {0} ({2}) and list2 ({3}) {1} ", l1, l2, l1.hashCode(), l2.hashCode()));
//                            l1.addAll(l2);
//                            return l1;

                            final ArrayList<Integer> elems = new ArrayList<>();
                            elems.addAll(l1);
                            elems.addAll(l2);
                            return elems;
                        }
                );

        assertEquals(MAX, res.size());
    }
}

