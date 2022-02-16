package com.maxtech.lib.logging;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;

public class RobotLogger {

    // === INSTANCES ===

    private static RobotLogger instance;

    public static RobotLogger getInstance() {
        if (instance == null) {
            instance = new RobotLogger();
        }

        return instance;
    }

    private RobotLogger() {
        notifier = new Notifier(this::send);
    }

    // === STATES ===

    private enum Level {
        Debug("DEBUG"), Info("INFO"), Warning("WARNING"), Error("ERROR");

        public final String name;

        Level(String name) {
            this.name = name;
        }
    }

    // === BUFFERS ===

    double period;
    private ArrayList<String> buffer = new ArrayList<>();
    private NetworkTable ntTable = NetworkTableInstance.getDefault().getTable("Rubie");
    private Notifier notifier;

    // === TIME ===
    double startTime = System.currentTimeMillis() / 1000.;

    // === PUBLIC METHODS ===

    /** Start the periodic logger with a default period of 20 milliseconds. */
    public void start() {
        start(.20);
    }

    public void start(double period) {
        this.period = period;
        notifier.startPeriodic(period);
    }

    /** Log an info-level message. */
    public void log(String msg, Object... args) {
        StackTraceElement lastMethod = Thread.currentThread().getStackTrace()[2];
        write(lastMethod, Level.Info, msg, args);
    }

    /** Log a warning message */
    public void warn(String msg, Object... args) {
        StackTraceElement lastMethod = Thread.currentThread().getStackTrace()[2];
        write(lastMethod, Level.Warning, msg, args);
    }

    /** Log a debug message */
    public void dbg(String msg, Object... args) {
        StackTraceElement lastMethod = Thread.currentThread().getStackTrace()[2];
        write(lastMethod, Level.Debug, msg, args);
    }

    // === HELPFUL METHODS ===

    private void write(StackTraceElement lastMethod, Level lvl, String msgF, Object... args) {
        // Build the log text.
        String message = String.format(msgF, args);
        double time = System.currentTimeMillis() / 1000. - startTime;

        String log = String.format("[%s] (%.2fs), %s: %s", lvl.name, time, getPackageName(lastMethod), message);

        // Send the log to the buffer
        buffer.add(log);
    }

    /** Send logs to the remote connection. If successful, clear the buffer. */
    private void send() {
        // Get the current cycle number. This is the elapsed time since start, divided by cycle time.
        double cycle = (System.currentTimeMillis() / 1000. - startTime) / period;

        for (String log : (ArrayList<String>) buffer.clone()) {
            System.out.println(log);
            ntTable.getEntry(String.valueOf(cycle)).setStringArray(buffer.toArray(new String[0]));

            buffer.remove(log);
        }
    }

    /**
     * Gets a friendly package name for a StackTraceElement.
     *
     * Credit: https://github.com/frc5024/lib5k/blob/269f02b55b3ff264d8481b93ffca3d3c5d34fce7/lib5k/src/main/java/io/github/frc5024/lib5k/logging/RobotLogger.java#L220-L264
     *
     * @param element element to get the name for
     * @return friendly name
     */
    protected static String getPackageName(StackTraceElement element) {

        // Build the package and method string
        String fullPackageName = element.getClassName();
        String[] packageSegments = fullPackageName.split("[.]");
        StringBuilder packageNameBuilder = new StringBuilder();

        // Handle the package name being too short
        if (packageSegments.length <= 3) {

            // Just build the full package name
            packageNameBuilder.append(fullPackageName);

        } else {
            // The idea here is to turn a package name that might look like:
            // io.github.frc5024.y2020.darthraider.commands.autonomous.actions.cells.SetShooterOutput
            // Into one that looks like:
            // io...cells.SetShooterOutput

            // Append the root package
            packageNameBuilder.append(packageSegments[0]);

            // Append the seperator
            packageNameBuilder.append("...");

            // Append the class's parent package
            packageNameBuilder.append(packageSegments[packageSegments.length - 2]);
            packageNameBuilder.append(".");

            // Append the class name
            packageNameBuilder.append(packageSegments[packageSegments.length - 1]);
        }

        // Get the method name
        String methodName = element.getMethodName();

        return String.format("%s::%s()", packageNameBuilder.toString(), methodName.toString());
    }
}
