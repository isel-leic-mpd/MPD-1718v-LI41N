package pt.isel.mpd.v1718.li41n.queries;

import com.sun.tools.javac.util.List;
import org.junit.Test;
import pt.isel.mpd.v1718.li41n.queries.eager.QueriesEager;

import java.util.Collection;

import static org.junit.Assert.*;

public class QueriesEagerTest {
    private static Collection<String> elements = List.of("Sport", "Lisboa", "e", "Benfica");


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