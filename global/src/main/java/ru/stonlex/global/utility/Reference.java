package ru.stonlex.global.utility;

import com.google.common.base.Joiner;
import lombok.NonNull;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class Reference {
    
    public long measureDelay(Runnable command, @NonNull TimeUnit timeUnit) {
        if (command == null) {
            return -1;
        }

        Instant start = Instant.now();
        command.run();

        return timeUnit.convert(Instant.now().minusMillis(start.toEpochMilli()).toEpochMilli(),
                TimeUnit.MILLISECONDS);
    }
    
    @SneakyThrows
    public void dump(Object object) {
        if (object == null) {
            System.out.println("[DUMP]: obj == null");

            return;
        }
        
        Class<?> objectClass = object.getClass();
        System.out.println("[DUMP]: " + Modifier.toString(objectClass.getModifiers()) + " " + objectClass + ":");

        System.out.println(" Constructors (" + objectClass.getDeclaredConstructors().length + "):");
        for (Constructor constructor : objectClass.getDeclaredConstructors()) {

            System.out.println(" - " + Modifier.toString(constructor.getModifiers()) + " " + objectClass.getSimpleName() + "("
                    + Joiner.on(", ").join(constructor.getParameterTypes()) + ")");
        }

        System.out.println(" Annotations: (" + objectClass.getAnnotations().length + "):");
        for (Annotation annotation : objectClass.getDeclaredAnnotations()) {

            System.out.println(" - " + annotation.annotationType().getName());
        }
        
        System.out.println(" Fields (" + objectClass.getFields().length + "):");
        for (Field field : objectClass.getDeclaredFields()) {
            
            field.setAccessible(true);
            System.out.println(" - " + Modifier.toString(field.getModifiers()) + " " + field.getType().getName() + " " + field.getName() + "=" + field.get(object));
        }

        System.out.println(" Methods (" + objectClass.getMethods().length + "):");
        System.out.println(" - public java.lang.String #toString(): =" + object.toString());
        System.out.println(" - public native int #hashCode(): =" + object.hashCode());

        for (Method method : objectClass.getDeclaredMethods()) {
            method.setAccessible(true);

            System.out.println(" - " + Modifier.toString(objectClass.getModifiers()) + " " + method.getReturnType().getName() + " #" + method.getName() + "(" +
                    Joiner.on(", ").join(method.getParameterTypes()) + ")");
        }

        System.out.println(" Extend: " + objectClass.getSuperclass().getName());
        System.out.println(" Implements (" + objectClass.getInterfaces().length + "):");

        for (Class<?> interfaceClass : objectClass.getInterfaces()) {
            System.out.println(" - " + interfaceClass.getName());
        }
    }
}
