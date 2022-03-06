package com.maxtech.maxx.commands.drivetrain;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ArcadeDriveFor extends CommandBase {
    private final Drive drivetrain = Drive.getInstance();

    private final double time;

    private final double s;
    private final double r;
    private final double seconds;

    public ArcadeDriveFor(double s, double r, double seconds) {
        this.s = s;
        this.r = r;
        this.seconds = seconds;

        time = Timer.getFPGATimestamp();

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.arcade(s, r);
    }

    @Override
    public boolean isFinished() {
        double current = Timer.getFPGATimestamp();
        RobotLogger.getInstance().dbg("current: %s, start time: %s", current, time);
        return current - time > seconds;
    }

    @Override
    public void end(boolean interrupted) {
        // TODO: drivetrain.stop()
        drivetrain.arcade(0, 0);
    }
}
