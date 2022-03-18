package com.maxtech.maxx.commands.porcelain.drivetrain;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDrive extends CommandBase {
    private final double speed;
    private final double degrees;
    private final Drive drivetrain = Drive.getInstance();

    public ArcadeDrive(double speed, double degrees) {
        this.speed = speed;
        this.degrees = degrees;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(speed, degrees);
    }
}
