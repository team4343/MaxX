package com.maxtech.maxx.commands.porcelain.drivetrain;

import com.maxtech.lib.command.TimedCommand;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj.Timer;

public class ArcadeDriveFor extends TimedCommand {
    private final Drive drivetrain = Drive.getInstance();

    private final double s;
    private final double r;

    public ArcadeDriveFor(double s, double r, double seconds) {
        this.s = s;
        this.r = r;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(s, r);
    }

    @Override
    public void end(boolean interrupted) {
        drivetrain.arcade(0, 0);
    }
}
