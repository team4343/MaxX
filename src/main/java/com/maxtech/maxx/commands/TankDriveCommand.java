package com.maxtech.maxx.commands;

import com.maxtech.lib.logging.Log;
import com.maxtech.maxx.subsystems.drivetrain.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

import java.util.function.DoubleSupplier;

public class TankDriveCommand extends CommandBase {
    private final DoubleSupplier ls;
    private final DoubleSupplier rs;
    private final DriveSubsystem drivetrain;

    public TankDriveCommand(DoubleSupplier ls, DoubleSupplier rs, DriveSubsystem drivetrain) {
        Log.info("TankDriveCommand", "Created a TankDriveCommand.");
        this.ls = ls;
        this.rs = rs;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        Log.info("TankDriveCommand", "Executing a TankDriveCommand.");
        drivetrain.tank(ls.getAsDouble(), rs.getAsDouble());
    }
}