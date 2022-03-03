package com.maxtech.maxx.subsystems.indexer;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.Constants;
import com.maxtech.maxx.RobotContainer;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends Subsystem {
    RobotLogger logger = RobotLogger.getInstance();
    private static Indexer instance;
    private final Flywheel flywheel;
    private IndexerIO io;

    public static Indexer getInstance() {
        if (instance == null)
            instance = new Indexer();
        return instance;
    }

    private Indexer() {
        switch(RobotContainer.teamNumber) {
            case 4343: io = new IndexerIOMax(); break;
            case 914: io = new IndexerIOPeter(); break;
            default: logger.err("Could not pick I/O, no matches."); break;
        }

        var tab = Shuffleboard.getTab("Indexer");
        tab.addBoolean("top sensor", this::isTopActive);
        tab.addBoolean("bottom sensor", this::isBottomActive);

        flywheel = Flywheel.getInstance();
    }

    @Override
    public void sendTelemetry(String prefix) {}

    public IndexerSensors getSensors() {
        return io.getSensors();
    }

    public boolean isTopActive() {
        return getSensors().top;
    }

    public boolean isBottomActive() {
        return getSensors().bottom;
    }

    public void run(boolean passThrough) {
        // If the bottom spot is full and the top spot is empty, move it up.
        // However, if we need to pass it through to the shooter ignore the sensor.
        double topOut = 0.0;
        double botOut = Constants.Indexer.maxOutput;

        if (passThrough || (!isBottomActive() && isTopActive())) {
            topOut = Constants.Indexer.maxOutput;
        }
        if (flywheel.getState() == Flywheel.FlywheelStates.ShootHigh) {
            topOut = Constants.Indexer.maxOutput;
        }

        io.set(topOut, botOut);
    }

    public void dump() {
        this.stop();
        io.set(-Constants.Indexer.maxOutput, -Constants.Indexer.maxOutput);
    }

    public void stop() {
        io.set(0,0);
    }
}
