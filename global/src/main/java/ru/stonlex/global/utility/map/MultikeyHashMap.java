package ru.stonlex.global.utility.map;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;
import lombok.RequiredArgsConstructor;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public class MultikeyHashMap<V> extends LinkedHashMap<Class<?>, MultikeyHashMap.GitMap<V>>
        implements MultikeyMap<V>, Map<Class<?>, MultikeyHashMap.GitMap<V>> {

    private final Set<V> entrySet = new HashSet<>();

    @Override
    public boolean isEmpty() {
        return entrySet.size() == 0;
    }

    @Override
    public <T> boolean contains(Class<?> registerClass, T t) {
        GitMap<V> gitMap = get(registerClass);

        if (gitMap instanceof GObjectMap) {
            return ((GObjectMap<V>) gitMap).get(t) != null;
        } else {
            return ((GMultiObjectType<V>) gitMap).get(t) != null;
        }
    }

    @Override
    public void put(V v) {
        for (GitMap<V> gitMap : values()) {
            gitMap.put(v);
        }

        entrySet.add(v);
    }

    @Override
    public void delete(V v) {
        for (GitMap<V> gitMap : values()) {
            gitMap.delete(v);
        }

        entrySet.remove(v);
    }

    @Override
    public void clearAll() {
        for (GitMap<V> gitMap : values()) {
            gitMap.clear();
        }
    }

    @Override
    public Collection<V> valueCollection() {
        return Collections.unmodifiableCollection(entrySet);
    }

    @Override
    public <T> V computeIfAbsent(Class<T> registerClass, T t, Supplier<V> supplier) {
        return getOrDefault(registerClass, t, supplier.get());
    }

    @Override
    public <T> MultikeyMap<V> register(Class<?> registerClass, Function<V, ?> function) {
        put(registerClass, new GObjectType<>(function));

        return this;
    }

    @Override
    public <T> MultikeyMap<V> registerMulti(Class<?> registerClass, Function<V, Collection<?>> function) {
        put(registerClass, new GMultiObjectType<>(function));

        return this;
    }

    @Override
    public <T> V get(Class<?> registerClass, T t) {
        GitMap<V> gitMap = get(registerClass);

        return ((GObjectMap<V>) gitMap).get(t);
    }

    @Override
    public <T> V getOrDefault(Class<?> registerClass, T t, V defaultObject) {
        V v = get(registerClass, t);

        return v == null ? defaultObject : v;
    }

    @Override
    public <T> Set<V> getAll(Class<T> cls, T t) {
        GitMap<V> gitMap = get(cls);

        return ((GMultiObjectType<V>) gitMap).get(t);
    }

    public abstract static class GitMap<V> {

        abstract void put(V v);
        abstract void delete(V v);

        abstract void clear();
    }

    @RequiredArgsConstructor
    static abstract class GObjectMap<V> extends GitMap<V> {
        final Function<V, ?> function;

        abstract V get(Object object);
    }

    @RequiredArgsConstructor
    public static class GMultiObjectType<V> extends GitMap<V> {

        private final Function<V, Collection<?>> function;
        private final SetMultimap<Object, V> objectTMap = HashMultimap.create();

        @Override
        public void put(V v) {
            objectTMap.put(function.apply(v), v);
        }

        @Override
        public void delete(V v) {
            objectTMap.remove(function.apply(v), v);
        }

        public Set<V> get(Object object) {
            return objectTMap.get(object);
        }

        @Override
        public void clear() {
            objectTMap.clear();
        }
    }

    public static class GObjectType<V> extends GObjectMap<V> {

        private final Map<Object, V> objectTMap = new LinkedHashMap<>();

        public GObjectType(Function<V, ?> function) {
            super(function);
        }

        @Override
        public void put(V v) {
            objectTMap.put(function.apply(v), v);
        }

        @Override
        public void delete(V v) {
            objectTMap.remove(function.apply(v), v);
        }

        @Override
        public V get(Object object) {
            return objectTMap.get(object);
        }

        @Override
        public void clear() {
            objectTMap.clear();
        }
    }
}
