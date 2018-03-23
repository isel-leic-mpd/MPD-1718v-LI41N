package pt.isel.mpd.v1718.li41n.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class StreamProcessor<T> implements Supplier<T> {

    @Override
    public final T get()  {
        // Open file
        try(InputStream stream = getStream()) {
            // Process file
            return process(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected abstract InputStream getStream() throws IOException;


    protected abstract T process(InputStream stream) throws IOException;

}
