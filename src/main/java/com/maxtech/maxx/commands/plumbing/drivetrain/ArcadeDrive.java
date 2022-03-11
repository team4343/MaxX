package com.maxtech.maxx.commands.plumbing.drivetrain;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
    private final double speed;
    private final double degrees;
    private final Drive drivetrain;

    public ArcadeDrive(double speed, double degrees, Drive drivetrain) {
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
