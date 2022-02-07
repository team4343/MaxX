package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.logging.Log;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A drivetrain subsystem for Max X.
 */
public class DriveSubsystem extends SubsystemBase {
    private DriveIO io;

    public DriveSubsystem(DriveIO io) {
        this.io = io;
    }

    @Override
    public void periodic() {
        // Make sure that the Inputs type is up-to-date.
        io.updateInputs();
    }

    /**
     * Drive by arcade-style parameters.
     *
     * @param s speed to drive at
     * @param r rotation to drive at
     * @see DifferentialDrive
     */
    public void arcade(double s, double r) {
        io.drive(0, 1);
    }

    /**
     * Drive by tank-style parameters.
     *
     * @param ls left side speed
     * @param rs right side speed
     * @see DifferentialDrive
     */
    public void tank(double ls, double rs) {
        Log.info("TankInfo", "Starting tank drive.");
        io.drive(ls, rs);
    }
}
