package ru.stonlex.global.utility;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public final class ReflectionUtil {

    /**
     * Опять же, этот код старый, и переписывать его мне было
     * попросту лень, да и тем более, он прекрасно работает.
     *
     * Если кому-то он неудобен, то система как бы не особо сложная,
     * поэтому можно и самому ее написать
     */

    public static /* varargs */ Constructor<?> getConstructor(Class<?> clazz, Class<?> ... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (DataType.compare(DataType.getPrimitive(constructor.getParameterTypes()), primitiveTypes)) continue;
            return constructor;
        }
        throw new NoSuchMethodException("There is no such constructor in this class with the specified parameter types");
    }

    public static Object getValue(Object instance, Class<?> clazz, boolean declared, String fieldName) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
        return ReflectionUtil.getField(clazz, declared, fieldName).get(instance);
    }

    public static Field getField(Class<?> clazz, boolean declared, String fieldName) throws NoSuchFieldException, SecurityException {
        Field field = declared ? clazz.getDeclaredField(fieldName) : clazz.getField(fieldName);
        field.setAccessible(true);
        return field;
    }

    public static Method getMethod(Class<?> clazz, String methodName, Class<?> ... parameterTypes) throws NoSuchMethodException {
        Class<?>[] primitiveTypes = DataType.getPrimitive(parameterTypes);
        for (Method method : clazz.getMethods()) {
            if (!method.getName().equals(methodName) || DataType.compare(DataType.getPrimitive(method.getParameterTypes()), primitiveTypes)) continue;
            return method;
        }
        throw new NoSuchMethodException("There is no such method in this class with the specified name and parameter types");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static void setField(Object object, String field, Object fieldValue) {
        Field f = null;
        Class<?> clazz = object.getClass();
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            f.set(object, fieldValue);
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        finally {
            f.setAccessible(false);
        }
    }

    public static <T> T getField(Class clazz, Object object, String field, Class<T> fieldType) {
        return (T)ReflectionUtil.getField(clazz, object, field);
    }

    public static void setValue(Object instance, String fieldName, Object value) throws Exception {
        Field field = instance.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(instance, value);
    }

    public static void setValue(Object instance, boolean declared, String fieldName, Object value) throws Exception {
        Field field;
        if (declared) {
            field = instance.getClass().getDeclaredField(fieldName);
        } else {
            field = instance.getClass().getField(fieldName);
        }
        field.setAccessible(true);
        field.set(instance, value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public static Object getField(Class clazz, Object object, String field) {
        Field f = null;
        try {
            f = clazz.getDeclaredField(field);
            f.setAccessible(true);
            Object object2 = f.get(object);
            return object2;
        }
        catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            Object var5_6 = null;
            return var5_6;
        }
        finally {
            f.setAccessible(false);
        }
    }

    public enum DataType {
        BYTE(Byte.TYPE, Byte.class),
        SHORT(Short.TYPE, Short.class),
        INTEGER(Integer.TYPE, Integer.class),
        LONG(Long.TYPE, Long.class),
        CHARACTER(Character.TYPE, Character.class),
        FLOAT(Float.TYPE, Float.class),
        DOUBLE(Double.TYPE, Double.class),
        BOOLEAN(Boolean.TYPE, Boolean.class);
        
        private static final Map<Class<?>, DataType> CLASS_MAP;
        private final Class<?> primitive;
        private final Class<?> reference;

        DataType(Class<?> primitive, Class<?> reference) {
            this.primitive = primitive;
            this.reference = reference;
        }

        public Class<?> getPrimitive() {
            return this.primitive;
        }

        public Class<?> getReference() {
            return this.reference;
        }

        public static DataType fromClass(Class<?> clazz) {
            return CLASS_MAP.get(clazz);
        }

        public static Class<?> getPrimitive(Class<?> clazz) {
            DataType type = DataType.fromClass(clazz);
            return type == null ? clazz : type.getPrimitive();
        }

        public static Class<?> getReference(Class<?> clazz) {
            DataType type = DataType.fromClass(clazz);
            return type == null ? clazz : type.getReference();
        }

        public static Class<?>[] getPrimitive(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getPrimitive(classes[index]);
            }
            return types;
        }

        public static Class<?>[] getReference(Class<?>[] classes) {
            int length = classes == null ? 0 : classes.length;
            Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getReference(classes[index]);
            }
            return types;
        }

        public static Class<?>[] getPrimitive(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getPrimitive(objects[index].getClass());
            }
            return types;
        }

        public static Class<?>[] getReference(Object[] objects) {
            int length = objects == null ? 0 : objects.length;
            Class[] types = new Class[length];
            for (int index = 0; index < length; ++index) {
                types[index] = DataType.getReference(objects[index].getClass());
            }
            return types;
        }

        public static boolean compare(Class<?>[] primary, Class<?>[] secondary) {
            if (primary == null || secondary == null || primary.length != secondary.length) {
                return true;
            }
            for (int index = 0; index < primary.length; ++index) {
                Class<?> primaryClass = primary[index];
                Class<?> secondaryClass = secondary[index];
                if (primaryClass.equals(secondaryClass) || primaryClass.isAssignableFrom(secondaryClass)) continue;
                return true;
            }
            return false;
        }

        static {
            CLASS_MAP = new HashMap<>();
            for (DataType type : DataType.values()) {
                CLASS_MAP.put(type.primitive, type);
                CLASS_MAP.put(type.reference, type);
            }
        }
    }

}

