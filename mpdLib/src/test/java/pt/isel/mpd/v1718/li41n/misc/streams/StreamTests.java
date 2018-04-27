package pt.isel.mpd.v1718.li41n.misc.streams;

import org.junit.*;


import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class StreamTests {

    private final static List<String> STRINGS = asList("Sport", "Lisboa", "e", "Benfica");
    private Stream<String> stringStream;

    @BeforeClass
    public static void beforeClass() {
        System.out.println("Before Class");

    }

//    @BeforeClass
//    public static void beforeClass1() {
//        System.out.println("Before Class1");
//
//    }

    @AfterClass
    public static void afterClass() {
        System.out.println("After Class");
    }

    @Before
    public void beforeTest() {
        System.out.println("Before");
        System.out.println("this hashcode: " + this.hashCode());

        stringStream = STRINGS.stream();

    }

    @After
    public void afterTest() {
        System.out.println("After");
    }

    @Test
    public void shouldJointStreamElements() {
        final List<String> strings = asList("Sport", "Lisboa", "Benfica");

        join(strings, ",");
        join(strings.stream(), ",");


    }

    @Test(expected = IllegalStateException.class)
    public void cannotIterateStreamMoreThanOnce() {
        // Arrange
        stringStream.forEach(System.out::println);

        // Act
        stringStream.forEach(System.out::println);


    }

    @Test
    public void usingPeek() {
        // Arrange
        Stream<Integer> res = stringStream
                .peek(s -> System.out.println("Initial peek " +  s))
                .filter(s -> s.length() > 3)
                .peek(s -> System.out.println("After filter " +  s))
                .map(String::length)
                .peek(i -> System.out.println("After map " + i ))
                ;


        System.out.println("Before forEach");
        // Act

        res.forEach(System.out::println);
        //final Spliterator<Integer> spliterator = res.spliterator();

        //while (spliterator.tryAdvance(System.out::println));




    }


    @Test
    public void shouldIterate() {
        final Stream<String> stream1 = STRINGS.stream();
        final Stream<String> stream2 = STRINGS.stream().parallel();

        System.out.println("Stream1");
        stream1.forEach(System.out::println);

        System.out.println("Stream2");
        stream2.forEachOrdered(System.out::println);
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
