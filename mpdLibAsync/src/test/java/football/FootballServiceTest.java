package football;

import football.model.Standing;
import org.junit.BeforeClass;
import org.junit.Test;
import util.HttpRequest;
import util.IRequest;
import util.Logging;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class FootballServiceTest {


    private static FootballService footballService;

    @BeforeClass
    public static void beforeClass() throws Exception {

        FootballWebApi api = new FootballWebApi(FootballServiceTest.getIRequestWithLog(new HttpRequest()));
        footballService = new FootballService(api);

    }

    private static IRequest getIRequestWithLog(IRequest httpRequest) {
        return (url, headers) -> {
            Logging.log("Requesting url {0} with headers {1}", url, headers);
            return httpRequest.getBody(url, headers);

        };

    }

    @Test
    public void shouldGetFirstPlaceTeamOnALlLeagues() {

        // Act
        List<Standing> standings = footballService.getFirstPlaceOnALlLeagues().join().collect(toList());

        // Assert

        assertNotNull(standings);
        int NUM_LEAGUES = 17;
        assertEquals(NUM_LEAGUES, standings.size());

        standings.forEach(System.out::println);







    }

}
