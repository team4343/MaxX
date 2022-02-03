package com.maxtech.lib.logging;

import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/** A wrapper for sending Shuffleboard event messages. */
public class Log {
    public static void error(String name, String description) {
        StackWalker w = StackWalker.getInstance();
        String additional = "\nFound at: " + w.getCallerClass();

        Shuffleboard.addEventMarker(name, description + additional, EventImportance.kCritical);
    }

    public static void warn(String name, String description) {
        StackWalker w = StackWalker.getInstance();
        String additional = "\nFound at: " + w.getCallerClass();

        Shuffleboard.addEventMarker(name, description + additional, EventImportance.kNormal);
    }

    public static void info(String name, String description) {
        StackWalker w = StackWalker.getInstance();
        String additional = "\nFound at: " + w.getCallerClass();

        Shuffleboard.addEventMarker(name, description + additional, EventImportance.kLow);
    }
}
