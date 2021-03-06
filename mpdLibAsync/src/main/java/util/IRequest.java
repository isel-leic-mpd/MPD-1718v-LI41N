package util;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public interface IRequest {
    public CompletableFuture<String> getBody(String competitionsUrl, Map<String, String> headers);
}
