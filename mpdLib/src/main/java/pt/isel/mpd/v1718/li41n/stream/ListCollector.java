package pt.isel.mpd.v1718.li41n.stream;


import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListCollector<T> implements Collector<T, List<T>, List<T>> {

    @Override
    public Supplier<List<T>> supplier() {

        System.out.println("supplier method called");
        return () ->  {
            System.out.println("#supplier function called on thread " + Thread.currentThread().getId());
            return new ArrayList<>();
        };
        //return ArrayList::new;
    }

    @Override
    public BiConsumer<List<T>, T> accumulator() {
        System.out.println("accumulator method called");
        return (l, t) -> {
            System.out.println("#accumulator function called on thread " + Thread.currentThread().getId());
            l.add(t);
        };
        //return List::add;
    }

    @Override
    public BinaryOperator<List<T>> combiner() {
        System.out.println("combiner method called");
        return (l1, l2) -> {
            System.out.println("#combiner function called on thread " + Thread.currentThread().getId());

            l1.addAll(l2); return l1;
        };
    }

    @Override
    public Function<List<T>, List<T>> finisher() {
        System.out.println("finisher method  called");
        return l -> {
            System.out.println("#finisher function called on thread " + Thread.currentThread().getId());
            return l;
        };

        //return Function::identity;
    }

    @Override
    public Set<Characteristics> characteristics() {
        //System.out.println("characteristics method  called");
        return Collections.unmodifiableSet(EnumSet.of(
                Characteristics.CONCURRENT,
                Characteristics.UNORDERED,
                Characteristics.IDENTITY_FINISH
                )
        );
    }
}
