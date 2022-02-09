package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.subsystems.DriveSubsystem;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class Basic2 extends CommandBase {
    private final DriveSubsystem drivetrain;

    public Basic2(DriveSubsystem drivetrain){
        this.drivetrain = drivetrain;
        addRequirements(drivetrain);
    }

    @Override
    public void execute(){
        drivetrain.arcade(-1, 0);
    }
}
