package util;

import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.BoundRequestBuilder;
import org.asynchttpclient.Dsl;
import org.asynchttpclient.Response;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static util.Logging.log;

public class HttpRequest implements IRequest {
    private static final AsyncHttpClient asyncHttpClient = Dsl.asyncHttpClient();

    @Override
    public CompletableFuture<String> getBody(String url, Map<String, String> headers) {
        log("getBody for {0}", url);


        final BoundRequestBuilder boundRequestBuilder = asyncHttpClient.prepareGet(url);
        headers
                .entrySet()
                .stream()
                .forEach(e -> boundRequestBuilder.addHeader(e.getKey(), e.getValue()));

        return boundRequestBuilder
                .execute().toCompletableFuture()
                .thenApply(r -> {
                    log("responseBody for {0}", url);
                    return r;
                })
                .thenApply(Response::getResponseBody);


    }
}
