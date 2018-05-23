package pt.isel.mpd.v1718.li41n.queries;

import org.junit.Test;
import pt.isel.mpd.v1718.li41n.queries.eager.QueriesEager;

import java.util.Collection;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class QueriesEagerTest {
    private static Collection<String> elements = asList("Sport", "Lisboa", "e", "Benfica");


    @Test
    public void shouldFilter() {
        // Arrange

        // Act
        final Iterable<String> filtered = QueriesEager.filter(elements, s -> s.length() > 2);

        // Assert
        assertNotNull(filtered);
        assertTrue(Queries.count(filtered) == 3);




    }
}