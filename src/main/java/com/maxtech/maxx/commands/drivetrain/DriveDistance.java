package com.maxtech.maxx.commands.drivetrain;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class DriveDistance extends CommandBase {
    private final double speed;
    private final double degrees;
    private final double distance;
    private final Drive drivetrain;

    public DriveDistance(double speed, double degrees, double distance, Drive drivetrain) {
        this.speed = speed;
        this.degrees = degrees;
        this.drivetrain = drivetrain;
        this.distance = distance;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        if (drivetrain.getDistanceTravelled() < this.distance)
            drivetrain.arcade(speed, degrees);
        else
            drivetrain.arcade(0,0);
    }
}
