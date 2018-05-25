package football;

import football.dto.LeagueDto;
import football.exceptions.FootballWebApiException;
import org.junit.Test;
import util.HttpRequest;
import util.IRequest;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static util.Logging.log;


public class FootballWebApiTest {

    private IRequest httpRequest = new HttpRequest();
    private FootballWebApi api = new FootballWebApi(httpRequest);
    @Test
    public void shouldGetAllLeaguesDto() throws ExecutionException, InterruptedException, FootballWebApiException {
        // Act
        log("shouldGetAllLeaguesDto");
        Stream<LeagueDto> leaguesStream = api.getLeagues().get();

        // Assert
        assertNotNull(leaguesStream);
        final List<LeagueDto> leagues = leaguesStream.collect(toList());

        int NUM_LEAGUES = 17;
        assertEquals(NUM_LEAGUES, leagues.size());
    }
}
