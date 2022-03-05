package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Quick extends CommandBase {
    private double ticker = 0;

    @Override
    public void execute() {
        ticker += 1;
        Drive.getInstance().arcade(-1, 0);
    }

    @Override
    public boolean isFinished() {
        return ticker >= 10;
    }
}
