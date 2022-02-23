package com.maxtech.maxx.commands;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDriveCommand extends CommandBase {
    private final double ls;
    private final double rs;
    private final Drive drivetrain;

    public TankDriveCommand(double ls, double rs, Drive drivetrain) {
        this.ls = ls;
        this.rs = rs;
        this.drivetrain = drivetrain;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        RobotLogger.getInstance().dbg("");
        drivetrain.tank(ls, rs);
    }
}