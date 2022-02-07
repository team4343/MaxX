package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExampleRotateCommand extends CommandBase {
    private final double degrees;
    private final DriveSubsystem drivetrain;

    public ExampleRotateCommand(double degrees, DriveSubsystem drivetrain) {
        this.degrees = degrees;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(0, degrees);
    }
}
