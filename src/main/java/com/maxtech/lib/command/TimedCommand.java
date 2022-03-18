package com.maxtech.lib.command;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;

public abstract class TimedCommand extends CommandBase {
    private double start = Timer.getFPGATimestamp();
    private Double length;

    public void setLength(double length) {
        this.length = length;
    }

    @Override
    public boolean isFinished() {
        if (length == null) return false;
        return Timer.getFPGATimestamp() - start > length;
    }
}
