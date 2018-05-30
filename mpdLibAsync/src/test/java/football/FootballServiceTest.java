package football;

import football.model.Standing;
import org.junit.BeforeClass;
import org.junit.Test;
import util.HttpRequest;
import util.IRequest;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;
import static util.Logging.log;

public class FootballServiceTest {


    private static FootballService footballService;

    @BeforeClass
    public static void beforeClass() throws Exception {

        //final IRequest iRequest = FootballServiceTest.getIRequestWithLog(new HttpRequest());
        final IRequest iRequest = new HttpRequest();
        FootballWebApi api = new FootballWebApi(iRequest);
        footballService = new FootballService(api);

    }

    private static IRequest getIRequestWithLog(IRequest httpRequest) {
        return (url, headers) -> {
            log("Requesting url {0} with headers {1}", url, headers);
            return httpRequest.getBody(url, headers);

        };

    }

    @Test
    public void shouldGetFirstPlaceTeamOnALlLeagues() {

        // Act
        List<Standing> standings = footballService.getFirstPlaceOnALlLeagues()
                .join()
                .peek(s -> log(s.toString()))
                .collect(toList());

        // Assert

        assertNotNull(standings);
        int NUM_LEAGUES = 17;
        assertEquals(NUM_LEAGUES, standings.size());

        //standings.forEach(System.out::println);







    }

}
