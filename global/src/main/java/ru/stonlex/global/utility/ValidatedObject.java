package ru.stonlex.global.utility;

import com.google.common.base.Preconditions;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ValidatedObject {

    private final Object object;

    public <T> T as(Class<T> typeClass) {
        if (object == null) {
            return null;
        }

        Preconditions.checkArgument(object.getClass().isAssignableFrom(typeClass),
                typeClass.getSimpleName() + " is`nt assignable with " + object.getClass().getSimpleName());

        return ((T) object);
    }

    public Object get() {
        return object;
    }

    public int asInt() {
        return as(int.class);
    }

    public Integer asInteger() {
        return as(Integer.class);
    }

    public Number asNumber() {
        return as(Number.class);
    }

    public double asDouble() {
        return as(double.class);
    }

    public float asFloat() {
        return as(float.class);
    }

    public long asLong() {
        return as(long.class);
    }

    public byte asByte() {
        return as(byte.class);
    }

    public short asShort() {
        return as(short.class);
    }

    public boolean asBoolean() {
        return as(boolean.class);
    }

    public String asString() {
        return as(String.class);
    }

}
