package pt.isel.mpd.v1718.li41n.queries;



import org.junit.Test;
import pt.isel.mpd.v1718.li41n.queries.lazy.QueriesLazy;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

public class QueriesLazyTest {

    private List<String> strings = asList("Sport", "Lisboa", "e", "Benfica");

    @Test
    public void shouldFilter() {
        // Arrange

        // Act
        final Iterable<String> filteredStrings = QueriesLazy.filter(strings, s -> s.length() > 2);


        // Assert
        assertEquals(3, Queries.count(filteredStrings));

    }

    @Test
    public void shouldMap() {
        // Arrange

        // Act
        final Iterable<Integer> stringSizes = QueriesLazy.map(strings, s -> s.length());


        // Assert
        assertEquals(strings.size(), Queries.count(stringSizes));

        // Verify sizes
        int i = 0;
        for (Integer size : stringSizes) {
            assertEquals(strings.get(i++).length(), (int)size);
        }
    }

    @Test
    public void shouldTake() {
        // Arrange

        // Act
        final int TO_TAKE = 2;
        final Iterable<String> stringsTaken = QueriesLazy.limit(strings, TO_TAKE);


        // Assert
        assertEquals(TO_TAKE, Queries.count(stringsTaken));

        // Verify sizes
        int i = 0;
        for (String str : stringsTaken) {
            assertEquals(strings.get(i++), str);
        }
    }

    @Test
    public void shouldSkip() {
        // Arrange

        // Act
        final int TO_SKIP = 3;
        final Iterable<String> stringsAfterSkipped = QueriesLazy.skip(strings, TO_SKIP);


        // Assert
        assertEquals(strings.size()-TO_SKIP, Queries.count(stringsAfterSkipped));

        // Verify sizes
        int i = TO_SKIP;
        for (String str : stringsAfterSkipped) {
            assertEquals(strings.get(i++), str);
        }
    }

    @Test
    public void shouldGetSecondPageAndMap() {
        // Arrange
        List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica");

        // Act
        final Iterable<Integer> res = QueriesLazy.map(
                QueriesLazy.limit(
                        QueriesLazy.skip(strings, 3),
                        3),
                String::length);


        // Assert
        assertIterableEquals(asList(7,5,6), res);
    }

}

