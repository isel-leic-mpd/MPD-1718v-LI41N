package util;

import java.util.concurrent.Future;

public interface IRequest {
    public Future<String> getBody(String competitionsUrl);
}
