package pt.isel.mpd.v1718.li41n.queries;


import org.junit.Test;
import pt.isel.mpd.v1718.li41n.queries.lazy.Advancer;
import pt.isel.mpd.v1718.li41n.queries.lazy.Fountain;
import pt.isel.mpd.v1718.li41n.queries.lazy.QueriesLazy;

import java.util.Iterator;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static pt.isel.mpd.v1718.li41n.queries.lazy.Fountain.of;

public class FountainSourceTest {

    private Fountain<String> strings = of(asList("Sport", "Lisboa", "e", "Benfica"));

    @Test
    public void shouldFilter() {
        // Arrange

        // Act
        final Fountain<String> filteredStrings = strings.filter(s -> s.length() > 2);

        System.out.println("External iteration");
        // External iteration
        while (filteredStrings.tryAdvance(System.out::println));

        System.out.println("Internal iteration");
        // Internal iteration
        filteredStrings.forEach(System.out::println);


        // Assert
        //assertIterableEquals(asList("Sport", "Lisboa", "Benfica"), filteredStrings);

    }

    @Test
    public void shouldMap() {
        // Arrange

        // Act
        final Iterator<Integer> stringSizes = strings.map(String::length).iterator();


        // Assert
        //assertIterableEquals(asList(5,6, 1, 7), stringSizes);

    }

    @Test
    public void shouldLimit() {
        // Arrange

        // Act
        final int TO_TAKE = 2;
        final Advancer<String> stringsTaken = strings.limit(TO_TAKE);

    }

}

