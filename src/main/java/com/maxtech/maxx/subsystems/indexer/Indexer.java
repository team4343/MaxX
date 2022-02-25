package com.maxtech.maxx.subsystems.indexer;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
    }

    private IndexerIO io;

    public void start() {

    }

    public void stop() {

    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putNumber(prefix + "voltage", io.getBusVoltage());
        SmartDashboard.putNumber(prefix + "temperature", io.getTemperature());
    }
}
