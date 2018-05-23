package football;

import com.google.common.util.concurrent.Futures;
import com.google.gson.Gson;
import football.dto.LeagueDto;
import football.exceptions.FootballWebApiException;
import util.IRequest;
import util.MyImmediateFuture;

import java.util.Arrays;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Stream;


/**
 * Instances of this class are responsible to make requests to  www.football-data.org API and transforms
 * its responses in DTOs, defined in {@link football.dto} package.
 */
public class FootballWebApi {
    private final static String BASE_URL = "https://www.football-data.org/v1/";
    private final static String COMPETITIONS_URL = BASE_URL + "competitions";
    private final static Gson gson = new Gson();
    private IRequest req;


    public FootballWebApi(IRequest req) {
        this.req = req;
    }

    public Future<Stream<LeagueDto>> getLeagues() throws FootballWebApiException {
        // Get the url response String
        Future<String> resp = req.getBody(COMPETITIONS_URL);


        try {
            // Wait for the response string to be available
            final String respStr = resp.get();

            // Map String to Stream<LeagueDtos>
            final Stream<LeagueDto> leagueDtosStream = getLeagueDtos(respStr);


            // Create and return Future
            return new MyImmediateFuture(leagueDtosStream);

        } catch (InterruptedException | ExecutionException e) {
            throw new FootballWebApiException(e);
        }
    }

    private Stream<LeagueDto> getLeagueDtos(String resp) throws InterruptedException, ExecutionException {
        return Arrays.stream(gson.fromJson(resp, LeagueDto[].class));
    }
}
