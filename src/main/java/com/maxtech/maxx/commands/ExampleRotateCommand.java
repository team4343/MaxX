package com.maxtech.maxx.commands;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ExampleRotateCommand extends CommandBase {
    private final double degrees;
    private final Drive drivetrain;

    public ExampleRotateCommand(double degrees, Drive drivetrain) {
        this.degrees = degrees;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(0, degrees);
    }
}
