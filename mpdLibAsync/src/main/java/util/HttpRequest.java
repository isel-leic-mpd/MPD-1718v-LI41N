package util;

import com.google.common.util.concurrent.Futures;
import football.exceptions.FootballWebApiException;
import org.asynchttpclient.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class HttpRequest implements IRequest {
    private static final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public Future<String> getBody(String url) {
        try {
            final Response response = asyncHttpClient.executeRequest(Dsl.get(url).build()).get();
            return Futures.immediateFuture(response.getResponseBody());
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }


    }
}
