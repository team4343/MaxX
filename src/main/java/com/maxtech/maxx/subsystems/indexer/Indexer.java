package com.maxtech.maxx.subsystems.indexer;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends Subsystem {
    RobotLogger logger = RobotLogger.getInstance();

    private static Indexer instance;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }

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
    }

    private IndexerIO io;

    @Override
    public void sendTelemetry(String prefix) {

    }

    @Override
    public void periodic() {
        // If the bottom spot is full and the top spot is empty, move it up.
        if (!isBottomActive() && isTopActive()) {
            io.set(0, 0.5);
        } else {
            io.set(0, 0);
        }
    }

    public IndexerSensors getSensors() {
        return io.get();
    }

    public boolean isTopActive() {
        return getSensors().top;
    }

    public boolean isBottomActive() {
        return getSensors().bottom;
    }
}
