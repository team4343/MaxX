package com.maxtech.maxx.commands.Autonomous;

import com.maxtech.maxx.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Basic1 extends CommandBase {
    private final DriveSubsystem drivetrain;

    public Basic1 (DriveSubsystem drivetrain) {
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(1, 0);
    }
}
