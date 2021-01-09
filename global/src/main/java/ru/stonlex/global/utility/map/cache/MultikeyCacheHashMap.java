package ru.stonlex.global.utility.map.cache;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import ru.stonlex.global.utility.map.MultikeyMap;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MultikeyCacheHashMap<V> extends LinkedHashMap<Class<?>, MultikeyCacheHashMap.GitCacheMap<MultikeyCacheHashMap.Entry<V>>>
        implements MultikeyCacheMap<V>, Map<Class<?>, MultikeyCacheHashMap.GitCacheMap<MultikeyCacheHashMap.Entry<V>>> {

    private final Set<Entry<V>> entrySet = new HashSet<>();

    @Getter
    private final long duration;

    public MultikeyCacheHashMap(int duration, TimeUnit timeUnit) {
        this.duration = timeUnit.toNanos(duration);
    }

    @Override
    public void cleanUp() {
        long now = System.nanoTime();

        for (Entry<V> entry : entrySet) {
            if (entry.timestamp + duration < now) {
                delete(entry.v);
            }
        }
    }

    @Override
    public boolean isEmpty() {
        return entrySet.size() == 0;
    }

    @Override
    public <T> boolean contains(Class<?> registerClass, T t) {
        return get(registerClass).get(t) != null;
    }

    @Override
    public void put(V v) {
        Entry<V> entry = new Entry<>(System.nanoTime(), v);

        for (GitCacheMap<Entry<V>> entryType : values()) {
            entryType.put(entry);
        }

        entrySet.add(entry);
    }

    @Override
    public void delete(V v) {
        Entry<V> entry = new Entry<>(-1, v);

        for (GitCacheMap<Entry<V>> entryType : values()) {
            entryType.delete(entry);
        }

        entrySet.remove(entry);
    }

    @Override
    public void clearAll() {
        for (GitCacheMap<Entry<V>> mapType : values()) {
            mapType.clear();
        }
    }

    @Override
    public Collection<V> valueCollection() {
        return entrySet.stream().map(entry -> entry.v).collect(Collectors.toList());
    }

    @Override
    public <T> V computeIfAbsent(Class<T> registerClass, T t, Supplier<V> supplier) {
        return getOrDefault(registerClass, t, supplier.get());
    }

    @Override
    public <T> MultikeyCacheHashMap<V> register(Class<?> registerClass, Function<V, ?> function) {
        put(registerClass, new GMultiCacheType<>(function));

        return this;
    }

    @Override
    public <T> MultikeyMap<V> registerMulti(Class<?> registerClass, Function<V, Collection<?>> function) {
        return null;
    }

    @Override
    public <T> V get(Class<?> registerClass, T t) {
        GitCacheMap<Entry<V>> entryType = get(registerClass);

        if (entryType.get(t) == null) {
            return null;
        }

        return entryType.get(t).v;
    }

    @Override
    public <T> V getOrDefault(Class<?> registerClass, T t, V defaultObject) {
        V v = get(registerClass, t);

        return v == null ? defaultObject : v;
    }

    @Override
    public <T> Set<V> getAll(Class<T> cls, T t) {
        return null;
    }

    @ToString(exclude = "timestamp")
    @EqualsAndHashCode(exclude = "timestamp")
    @RequiredArgsConstructor
    public static class Entry<V> {

        private final long timestamp;
        private final V v;
    }

    public abstract static class GitCacheMap<V> {

        abstract void put(V v);
        abstract void delete(V v);

        abstract <T> V get(Object object);

        abstract void clear();
    }

    @RequiredArgsConstructor
    public static class GMultiCacheType<V> extends GitCacheMap<Entry<V>> {

        private final Map<Object, Entry<V>> entryMap = new HashMap<>();
        private final Function<V, ?> function;

        @Override
        public void put(Entry<V> entry) {
            entryMap.put(function.apply(entry.v), entry);
        }

        @Override
        public void delete(Entry<V> entry) {
            entryMap.remove(function.apply(entry.v));
        }

        @Override
        public Entry<V> get(Object object) {
            return entryMap.get(object);
        }

        @Override
        public void clear() {
            entryMap.clear();
        }
    }
}