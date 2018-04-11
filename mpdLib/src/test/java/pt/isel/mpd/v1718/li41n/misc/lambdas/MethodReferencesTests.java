package pt.isel.mpd.v1718.li41n.misc.lambdas;

import org.junit.Test;
import pt.isel.mpd.v1718.li41n.weather.dataAccess.DailyWeatherInfoDto;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

public class MethodReferencesTests {
    @Test
    public void methodReferncesTests() {
        final String slb = "SLB";


        // Using lambda syntax
        Function<String, Integer> sToI = s -> Integer.parseInt(s);

        int i1 = sToI.apply("23");
        int i2 = sToI.apply("abc");

        // Using method reference to static method syntax
        Function<String, Integer> sToi1 = Integer::parseInt;

        i1 = sToI.apply("23");
        i2 = sToI.apply("abc");



        sToI = s -> s.length();
        i1 = sToI.apply("23");
        i2 = sToI.apply("abc");

        sToI = String::length;

        BiFunction<String, Integer, String> siToS = (s, i) -> s.substring(i);
        siToS = String::substring;

        BiFunction<Integer, String, String> isToS =  (i, s) -> s.substring(i);


        Supplier<Integer> supI = () -> slb.length();
        supI = slb::length;

        BiFunction<Integer, Integer, Point> iiToPoint = (x, y) -> new Point(x, y);
        iiToPoint = Point::new;

        Supplier<Point>  supPoint = () -> new Point();
        supPoint = Point::new;


        final Point point = supPoint.get();
        final Point point1 = supPoint.get();


        Function<Integer, String[]> iToAs = (i) -> new String[i];
        iToAs = String[]::new;

        Function<Integer, int[]> iToAi = (i) -> new int[i];
        iToAi = int[]::new;
    }

}

class Point {
    private final int x;
    private final int y;

    Point(int x, int y) {

        this.x = x;
        this.y = y;
    }

    Point() {
        this(0,0);
    }
}
