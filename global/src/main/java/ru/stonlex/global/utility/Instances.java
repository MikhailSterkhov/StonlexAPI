package ru.stonlex.global.utility;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class Instances {

    private final Map<Class<?>, Object> INSTANCE_OBJECTS = new HashMap<>();

    @SuppressWarnings("unchecked")
    public <T> T getInstance(@NonNull Class<T> instanceClass) {
        Object instanceObject = INSTANCE_OBJECTS.get(instanceClass);

        if (instanceObject != null) {
            return ((T) instanceObject);
        }

        return null;
    }

    public void addInstance(@NonNull Object instance) {
        INSTANCE_OBJECTS.put(instance.getClass(), instance);
    }

    public <T> void addInstance(@NonNull Class<T> instanceClass, @NonNull T instanceObject) {
        INSTANCE_OBJECTS.put(instanceClass, instanceObject);
    }

}
