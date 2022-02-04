package com.maxtech.lib.logging;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.EventImportance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/** A wrapper for sending Shuffleboard event messages. */
public class Log {
    public static void error(String name, String description) {
        DriverStation.reportError("[ERROR]" + name + "\n" + description, false);
        Shuffleboard.addEventMarker(name, description, EventImportance.kCritical);
    }

    public static void warn(String name, String description) {
        DriverStation.reportWarning("[WARN]" + name + "\n" + description, false);
        Shuffleboard.addEventMarker(name, description, EventImportance.kNormal);
    }

    public static void info(String name, String description) {
        DriverStation.reportWarning("[INFO]" + name + "\n" + description, false);
        Shuffleboard.addEventMarker(name, description, EventImportance.kLow);
    }
}
