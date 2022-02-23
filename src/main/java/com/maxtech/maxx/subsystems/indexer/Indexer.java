package com.maxtech.maxx.subsystems.indexer;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.RobotContainer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends SubsystemBase {
    RobotLogger logger = RobotLogger.getInstance();
    
    // === INSTANCES ===

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

    // === I/O ===
    private IndexerIO io;

    // === PUBLIC METHODS ===

    public void start() {

    }

    public void stop() {

    }

    // === OVERRIDE METHODS ===

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Indexer Voltage", io.getBusVoltage());
        SmartDashboard.putNumber("Indexer Temperature", io.getTemperature());
    }
}
