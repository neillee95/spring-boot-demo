package com.example.springbootdemo.servletweb.extension;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Extension for {@link CompletableFuture}.
 *
 * @author lee
 */
public class CompletableFutureExtension {

    public static <T1, T2> CompletableFuture<Tuple2<T1, T2>> sequence(CompletableFuture<T1> f1,
                                                                      CompletableFuture<T2> f2) {
        return CompletableFuture.allOf(f1, f2)
                .thenApply(v -> {
                    T1 t1 = f1.join();
                    T2 t2 = f2.join();
                    return new Tuple2<>(t1, t2);
                });
    }

    public static <T1, T2, T3> CompletableFuture<Tuple3<T1, T2, T3>> sequence(CompletableFuture<T1> f1,
                                                                              CompletableFuture<T2> f2,
                                                                              CompletableFuture<T3> f3) {
        return CompletableFuture.allOf(f1, f2, f3)
                .thenApply(v -> {
                    T1 t1 = f1.join();
                    T2 t2 = f2.join();
                    T3 t3 = f3.join();
                    return new Tuple3<>(t1, t2, t3);
                });
    }

    public static <T1, T2, T3, T4> CompletableFuture<Tuple4<T1, T2, T3, T4>> sequence(CompletableFuture<T1> f1,
                                                                                      CompletableFuture<T2> f2,
                                                                                      CompletableFuture<T3> f3,
                                                                                      CompletableFuture<T4> f4) {
        return CompletableFuture.allOf(f1, f2, f3, f4)
                .thenApply(v -> {
                    T1 t1 = f1.join();
                    T2 t2 = f2.join();
                    T3 t3 = f3.join();
                    T4 t4 = f4.join();
                    return new Tuple4<>(t1, t2, t3, t4);
                });
    }

    public static CompletableFuture<Tuple> sequence(CompletableFuture<?>... cs) {
        List<CompletableFuture<?>> completableFutures = Arrays.asList(cs);
        return CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]))
                .thenApply(v -> {
                    List<Object> results = completableFutures.stream()
                            .map(CompletableFuture::join)
                            .collect(Collectors.toList());
                    return new Tuple(results);
                });
    }

    public static class Tuple {

        protected final List<Object> results;

        Tuple(List<Object> results) {
            this.results = Objects.requireNonNull(results);
        }

        public Object get(int index) {
            if (index < 0 || index >= results.size()) {
                throw new ArrayIndexOutOfBoundsException("Bad index " + index);
            }
            return results.get(index);
        }

        public <T> T get(int index, Class<T> type) {
            if (index < 0 || index >= results.size()) {
                throw new ArrayIndexOutOfBoundsException("Bad index " + index);
            }
            Object obj = results.get(index);
            Class<?> objClass = obj.getClass();
            if (type.isAssignableFrom(objClass)) {
                //noinspection unchecked
                return (T) obj;
            }
            throw new ClassCastException(type.getName() + " is not assignable from " + obj.getClass().getName());
        }

    }

    public static class Tuple2<T1, T2> {

        protected T1 t1;
        protected T2 t2;

        Tuple2(T1 t1, T2 t2) {
            this.t1 = t1;
            this.t2 = t2;
        }

        public T1 getT1() {
            return t1;
        }

        public T2 getT2() {
            return t2;
        }

    }

    public static class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {

        protected T3 t3;

        Tuple3(T1 t1, T2 t2, T3 t3) {
            super(t1, t2);
            this.t3 = t3;
        }

        public T3 getT3() {
            return t3;
        }

    }

    public static class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3> {

        protected T4 t4;

        Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
            super(t1, t2, t3);
            this.t4 = t4;
        }

        public T4 getT4() {
            return t4;
        }

    }

}
