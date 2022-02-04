package com.maxtech.lib.logging;

import com.maxtech.lib.logging.attributes.Log;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.reflections.Reflections;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import static com.maxtech.lib.logging.Log.*;
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
                warn("LogAttributeExpansion", "Failed to set double value, defaulting to 0.");
                value = 0;
            } catch (InstantiationException ignore) {
                warn("LogAttributeExpansion", "Failed to instantiate, defaulting to 0.");
                value = 0;
            } catch (InvocationTargetException e) {
                warn("LogAttributeExpansion", "Failed to invoke the target, defaulting to 0.");
                value = 0;
            } catch (NoSuchMethodException ignore) {
                warn("LogAttributeExpansion", "Failed to create a method, defaulting to 0.");
                value = 0;
            }

            // Based on the type, we will do different things:
            if (field.getType().getTypeName().equals("double")) {
                SmartDashboard.putNumber(field.getAnnotation(Log.class).name(), (Double) value);
            } else if (field.getType().isInstance(Sendable.class)) {
                SmartDashboard.putData(field.getAnnotation(Log.class).name(), (Sendable) value);
            } else {
                // We don't know how to send this type!
                error("LogAttributeCall","Failed to send type - I don't know how to: " + field.getType());
            }
        }
    }
}
