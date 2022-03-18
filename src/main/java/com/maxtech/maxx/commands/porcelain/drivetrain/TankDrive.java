package com.maxtech.maxx.commands.porcelain.drivetrain;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class TankDrive extends CommandBase {
    private final double ls;
    private final double rs;
    private final Drive drivetrain = Drive.getInstance();

    public TankDrive(double ls, double rs) {
        this.ls = ls;
        this.rs = rs;

        addRequirements(drivetrain);
    }

    @Override
    public void execute() {
        drivetrain.tank(ls, rs);
    }
}
