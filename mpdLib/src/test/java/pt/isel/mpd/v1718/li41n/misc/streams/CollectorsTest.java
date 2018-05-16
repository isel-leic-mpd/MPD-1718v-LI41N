package pt.isel.mpd.v1718.li41n.misc.streams;


import org.junit.Test;
import pt.isel.mpd.v1718.li41n.stream.ListCollector;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Comparator.comparingInt;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.*;
import static junit.framework.TestCase.assertEquals;

public class CollectorsTest {

    private String loremIpsum = "Lorem ipsum dolor sit amet consectetuer adipiscing elit. Nascetur sit lacus laoreet class metus sodales. Eni iaculis velit pretium et pretium fringilla gravida massa. In neque mollis placerat viverra. Iaculis tortor vestibulum vitae habitasse enim nam taciti hymenaeos condimentum ridiculus fusce eni nunc quam.\n" +
            "\n" +
            "Vel euismod taciti ut vitae duis nibh posuere molestie. Class malesuada elit. Consequat facilisi ornare cum. Commodo. Praesent. Aliquet dapibus diam fusce maecenas ve pellentesque class aliquam cum vivamus interdum. Scelerisque ultricies condimentum vitae. Luctus felis. Ante. Viverra platea adipiscing ultrices. Posuere sodales pede ut eget sapien ve enim at duis dictumst scelerisque tellus. Ipsum nulla penatibus nam velit vivamus sed. Fermentum vehicula fusce est natoque lacus tellus curabitur semper hymenaeos vel leo. Eu felis consectetuer parturient felis malesuada venenatis. Magna nisl sed dictum tristique non dictum. Platea orci primis leo habitasse lorem vitae penatibus fermentum libero.\n" +
            "\n" +
            "Fames leo litora venenatis gravida per id porta dictumst consectetuer leo. Eu suscipit maecenas orci cras aptent class. Scelerisque pretium fusce nunc ut mattis leo augue varius dignissim est ridiculus dui montes eni. Nostra mi tempus sociosqu auctor cubilia tempor ornare scelerisque cras. Ligula ve aliquam egestas montes vestibulum scelerisque platea ad lacinia netus parturient ornare luctus iaculis. Proin torquent integer natoque fames amet blandit est luctus pede fames parturient imperdiet. Odio hac nunc tellus mi pretium sollicitudin torquent eleifend commodo.\n" +
            "\n" +
            "Leo magnis enim rhoncus eni! Cursus nullam? Maecenas risus et odio elit hymenaeos ac ve taciti hymenaeos vestibulum adipiscing erat. Neque nostra eu torquent. Duis auctor id senectus blandit sodales taciti ac ac sit erat ad sollicitudin lobortis.\n" +
            "\n" +
            "Ve tellus mus adipiscing aliquet volutpat hymenaeos duis lorem urna aenean venenatis diam pellentesque sed cubilia. Morbi elit lorem. Hymenaeos donec erat dolor. Nulla elit primis phasellus ante commodo nisi tristique risus felis. Ad arcu tempus nonummy sit habitasse ac tristique tortor dictum lectus aptent! Fames ad nunc mauris placerat conubia adipiscing eget senectus potenti hendrerit congue magna rutrum penatibus. Senectus vivamus tempor dis posuere. Convallis eni convallis. Aenean inceptos per lacus pretium proin felis feugiat eni potenti ipsum euismod ac egestas venenatis. Aliquam velit mi class tellus facilisi sodales potenti nullam vestibulum proin malesuada quis. Sem congue phasellus scelerisque malesuada massa per erat ante.";
    private String WORD_SEPARATOR_REG_EXP = "[,|(\\s+)|\\.|!|\\?]";

    private final String[] words = loremIpsum.split(WORD_SEPARATOR_REG_EXP);

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

    @Test
    public void shouldReduceAStreamToAMap() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        final Map<Integer, String> res = strings.stream().collect(
                toMap(String::length, identity(), (s1, s2) -> s1 + s2, TreeMap::new)
        );


        assertEquals(strings.size(), res.keySet().size());
        assertEquals(strings.size(), res.values().size());


    }

    @Test
    public void shouldJoinElmentsinAStringWithJoiningCollector() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        String res = strings.stream().collect(joining());

        assertEquals("SportLisboaeBenficae", res);
    }

    @Test
    public void shouldJoinElmentsinAStringWithJoiningCollectorWithCustomSeparator() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        String res = strings.stream().collect(joining("##"));

        assertEquals("Sport##Lisboa##e##Benfica#e", res);
    }

    @Test
    public void shouldJoinElmentsinAStringWithJoiningCollectorWithCustomSeparatorPrefixAndSufix() {
        final List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "e");

        String res = strings.stream().collect(joining("#", "[", "]"));

        assertEquals("[Sport#Lisboa#e#Benfica#e]", res);
    }

    @Test
    public void shouldGroupByStringSize() {
        // String -> Map<Integer, List<String>>>

        Map<Integer, List<String>> res = Arrays.stream(loremIpsum.split(WORD_SEPARATOR_REG_EXP))
                .collect(groupingBy(String::length));

    }

    @Test
    public void shouldGroupByStringSizeAndCount() {
        // String -> Map<Integer, List<String>>>

        final String[] words = loremIpsum.split(WORD_SEPARATOR_REG_EXP);

        Map<Integer, Long> res = Arrays.stream(words)
                .collect(groupingBy(String::length, counting()));


        String str = res.entrySet().stream().map(Object::toString).
                collect(joining(","));
        System.out.println(str);


    }

    @Test
    public void shouldGroupByFirstCharAndThenByWordSizeAndCount() {
        // String -> Map<Char, Map<Integer, Long>>>

        Map<Character, Map<Character, Map<Integer, Long>>> res = Arrays.stream(words).collect(
                groupingBy(
                        s -> s.length() != 0 ? s.toLowerCase().charAt(0) : '#',
                        groupingBy(
                                s -> s.length() != 0 ? s.toLowerCase().charAt(1) : '#',
                                groupingBy(String::length, counting())
                        )
                )
        );


        String str = res.entrySet().stream().map(Object::toString).collect(joining(","));
        System.out.println(str);
    }

    @Test
    public void shouldGroupByFirstCharValueIsTheLargestWord() {
        Map<Character, String> res = Arrays.stream(words).collect(
                groupingBy(s -> s.length() != 0 ? s.toLowerCase().charAt(0) : '#',
                        collectingAndThen(
                                reducing((s1, s2) -> s1.length() > s2.length() ? s1 : s2),
                                Optional::get
                        )
                )
        );


        // And now... Something completelly different!!!
//        Map<Character, String> res1 = Arrays.stream(words).collect(
//                groupingBy(s -> s.length() != 0 ? s.toLowerCase().charAt(0) : '#',
//                        collectingAndThen(
//                                maxBy(comparingInt(String::length)),
//                                Optional::get
//                        )
//                )
//        );
        String str = res.entrySet().stream().map(Object::toString).collect(joining(","));
        System.out.println(str);
    }

    @Test
    public void shouldCountTheNumberOfCommonWordsInTwoFiles() throws IOException {
        // Step 1: Get the file words stream
        String FILE1 = ClassLoader.getSystemResource("file1.txt").getFile().replace("%20", " ");
        Stream<String> words1 = Files.lines(Paths.get(FILE1))
                .flatMap(s -> Arrays.stream(s.split(WORD_SEPARATOR_REG_EXP)))
                ;
        String FILE2 = ClassLoader.getSystemResource("file2.txt").getFile().replace("%20", " ");

        Stream<String> words2 = Files.lines(Paths.get(FILE2))
                .flatMap(s -> Arrays.stream(s.split(WORD_SEPARATOR_REG_EXP)))
                ;


//        words1 = asList("abc", "ab", "abc", "ab", "abd").stream();
//        words2 = asList("abc", "abc", "abd", "def", "de", "defg", "ab").stream();

        // Step 2: Group each words and count its number of occurrences
        Map<String, Long> words1Count = words1.collect(
                groupingBy(identity(), counting())
        );

        Map<String, Long> words2Count = words2.collect(
                groupingBy(identity(), counting())
        );


        // Step 3: Join all words from both files (without repetition in each file,
        // meaning a word occurs only once or twice in the resulting stream)
        Map<String, Long> allWords = Stream.concat(words1Count.keySet().stream(), words2Count.keySet().stream())
            .collect(groupingBy(identity(), counting()))
        ;

        // Step 4: Filter all words with two occurrences (means the exist at least once in both files)
        final Stream<String> commonWordsOccurrences = allWords.entrySet().stream().
                filter(e -> e.getValue() == 2)
                .map(Map.Entry::getKey);

        // Step 5: Get the desired result, transforming the filtered stream in a map
        // where the key is the string and the value is the sum of its occurrences in
        // both files
        final Map<String, Long> res = commonWordsOccurrences.collect(
                toMap(identity(), s -> words1Count.get(s) + words2Count.get(s))
        );

        String str = res.entrySet().stream().map(Object::toString).collect(joining(","));
        System.out.println(str);


    }
}

