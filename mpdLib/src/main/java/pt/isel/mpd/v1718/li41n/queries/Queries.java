package pt.isel.mpd.v1718.li41n.queries;

public class Queries {
    public static <T> int count(Iterable<T> coll) {
        int count = 0;
        for (T t : coll) {
            ++count;
        }
        return count;
    }
}