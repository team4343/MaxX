package com.maxtech.lib.command;

import com.maxtech.lib.logging.RobotLogger;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class TimedCommand extends CommandBase {
    private double start;
    private Double length;

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public void initialize() {
        start = Timer.getFPGATimestamp();
    }

    @Override
    public boolean isFinished() {
        if (length == null) return false;
        RobotLogger.getInstance().dbg("%s %s %s", start, Timer.getFPGATimestamp(), length);
        return Timer.getFPGATimestamp() - start > length;
    }
}
