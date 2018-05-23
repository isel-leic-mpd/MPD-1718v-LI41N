package util;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;

import java.util.concurrent.CompletableFuture;

import static util.Logging.log;

public class HttpRequest implements IRequest {
    private static final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public CompletableFuture<String> getBody(String url) {
        log("getBody");

        return asyncHttpClient.prepareGet(url)
                .execute().toCompletableFuture()
                .thenApply(r -> { log("responseBody"); return r; })
                .thenApply(Response::getResponseBody);


    }
}
