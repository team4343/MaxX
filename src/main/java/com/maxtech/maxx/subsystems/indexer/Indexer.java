package com.maxtech.maxx.subsystems.indexer;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends SubsystemBase {
    
    // === INSTANCES ===

    private static Indexer instance;

    public static Indexer getInstance() {
        if (instance == null) {
            instance = new Indexer();
        }

        return instance;
    }

    private Indexer() {
        // Create the I/O based on a SendableChooser.
        io.setDefaultOption("Max", new IndexerIOMax());
        io.addOption("Peter", new IndexerIOPeter());
        io.addOption("Simulation", new IndexerIOSim());

        SmartDashboard.putData("Indexer chooser", io);
    }

    // === I/O ===
    private SendableChooser<IndexerIO> io = new SendableChooser<>();

    // === PUBLIC METHODS ===

    public void start() {

    }

    public void stop() {

    }

    // === OVERRIDE METHODS ===

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Indexer Voltage", io.getSelected().getBusVoltage());
        SmartDashboard.putNumber("Indexer Temperature", io.getSelected().getTemperature());
    }
}
