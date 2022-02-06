package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Basic extends CommandBase{

    private final double speed;
    private final double degrees;
    private final DriveSubsystem drivetrain;

    public Basic(double degrees, double speed, DriveSubsystem drivetrain) {
        this.speed = speed;
        this.degrees = degrees;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(speed, degrees);
    }

}






