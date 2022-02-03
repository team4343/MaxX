package com.maxtech.lib.logging;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.reflections.Reflections;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static org.reflections.scanners.Scanners.FieldsAnnotated;

public class Logger {
    public Set<Field> fields;

    public Logger() {
    }

    public void run() {
        Reflections reflections = new Reflections("com.maxtech.maxx", FieldsAnnotated);
        fields = reflections.getFieldsAnnotatedWith(Log.class);

        for (Field field : fields) {
            Object value;

            try {
                value = field.get(field.getDeclaringClass().getDeclaredConstructor().newInstance());
            } catch (IllegalAccessException ignore) {
                System.out.println("Failed to set double value, defaulting to 0.");
                value = 0;
            } catch (InstantiationException ignore) {
                System.out.println("Failed to instantiate object, defaulting to 0.");
                value = 0;
            } catch (InvocationTargetException e) {
                System.out.println("Couldn't invoke a target: " + e.getTargetException());
                value = 0;
            } catch (NoSuchMethodException ignore) {
                System.out.println("Failed to create a method, defaulting to 0. ");
                value = 0;
            }

            // Based on the type, we will do different things:
            if (field.getType().getTypeName() == "double") {
                // We have a double - cast to it and send.
                SmartDashboard.putNumber(field.getAnnotation(Log.class).name(), (Double) value);
            } else if (field.getType().isInstance(Sendable.class)) {
                SmartDashboard.putData(field.getAnnotation(Log.class).name(), (Sendable) value);
            } else {
                // We don't know how to send this type!
                System.out.println("Failed to send type - I don't know how to: " + field.getType());
            }
        }
    }
}
