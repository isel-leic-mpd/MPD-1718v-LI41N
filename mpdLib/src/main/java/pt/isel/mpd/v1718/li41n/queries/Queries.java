package pt.isel.mpd.v1718.li41n.queries;


import pt.isel.mpd.v1718.li41n.utils.StringIteratorFromInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

public class Queries {
    public static<T>  int count(Iterable<T> coll) {
        int count = 0;
        for (T t : coll) {
            ++count;
        }
        return count;
    }


    public static<T> Iterator<String> iterator(InputStream is) throws IOException {
        return new StringIteratorFromInputStream(is);
    }

}
