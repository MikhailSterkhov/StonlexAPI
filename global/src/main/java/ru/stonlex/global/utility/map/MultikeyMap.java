package ru.stonlex.global.utility.map;

import java.util.Collection;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

public interface MultikeyMap<V> {

    int size();

    boolean isEmpty();

    <T> boolean contains(Class<?> registerClass, T t);

    void put(V v);

    void delete(V v);

    void clearAll();

    Collection<V> valueCollection();

    <T> V computeIfAbsent(Class<T> registerClass, T t, Supplier<V> supplier);

    <T> MultikeyMap<V> register(Class<?> registerClass, Function<V, ?> function);

    <T> MultikeyMap<V> registerMulti(Class<?> registerClass, Function<V, Collection<?>> function);

    <T> V get(Class<?> registerClass, T t);

    <T> V getOrDefault(Class<?> registerClass, T t, V defaultObject);

    <T> Set<V> getAll(Class<T> cls, T t);
}
