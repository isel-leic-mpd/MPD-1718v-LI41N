package pt.isel.mpd.v1718.li41n.queries;



import org.junit.Test;
import pt.isel.mpd.v1718.li41n.queries.lazy.QueriesLazy;
import pt.isel.mpd.v1718.li41n.queries.lazy.QueriesLazyWithInstanceMethods;

import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static pt.isel.mpd.v1718.li41n.queries.lazy.QueriesLazyWithInstanceMethods.of;

public class QueriesLazyWithInstanceMethodsTest {

    private QueriesLazyWithInstanceMethods<String> strings = of(asList("Sport", "Lisboa", "e", "Benfica"));

    @Test
    public void shouldFilter() {
        // Arrange

        // Act
        final Iterable<String> filteredStrings = strings.filter(s -> s.length() > 2);


        // Assert
        assertEquals(3, Queries.count(filteredStrings));

    }

    @Test
    public void shouldMap() {
        // Arrange

        // Act
        final Iterable<Integer> stringSizes = strings.map(s -> s.length());


        // Assert
        assertIterableEquals(asList(5,6, 1, 7), stringSizes);




    }

    @Test
    public void shouldTake() {
        // Arrange

        // Act
        final int TO_TAKE = 2;
        final Iterable<String> stringsTaken = QueriesLazy.limit(strings, TO_TAKE);


        // Assert
        assertIterableEquals(asList("Sport", "Lisboa"), stringsTaken);
    }

    @Test
    public void shouldSkip() {
        // Arrange

        // Act
        final int TO_SKIP = 3;
        final Iterable<String> stringsAfterSkipped = QueriesLazy.skip(strings, TO_SKIP);

        // Assert
        assertIterableEquals(asList("Benfica"), stringsAfterSkipped);


    }

    @Test
    public void shouldGetSecondPageAndMap() {
        // Arrange
        List<String> strings = asList("Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica", "Sport", "Lisboa", "e", "Benfica");

        // Act
        Iterable<Integer> res = of(strings)
                .skip(3)
                .limit(3)
                .map(String::length)
                .limit(2);

        // Assert
        assertIterableEquals(asList(7,5), res);
    }

}

