package com.maxtech.lib.logging;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static edu.wpi.first.wpilibj.shuffleboard.EventImportance.*;

public class Log {
    public static void error(String message) {
        DriverStation.reportError(message, true);
        Shuffleboard.addEventMarker(message, kCritical);
    }

    public static void info(String message) {
        DriverStation.reportWarning(message, true);
        Shuffleboard.addEventMarker(message, kTrivial);
    }
}
