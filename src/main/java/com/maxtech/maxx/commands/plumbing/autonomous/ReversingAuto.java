package com.maxtech.maxx.commands.plumbing.autonomous;

import com.maxtech.maxx.commands.plumbing.drivetrain.ArcadeDrive;
import com.maxtech.maxx.commands.plumbing.drivetrain.ArcadeDriveFor;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class ReversingAuto extends SequentialCommandGroup {
    public ReversingAuto() {
        addCommands(
                new ArcadeDriveFor(.5, 0, 6),
                new ArcadeDrive(0, 0, Drive.getInstance())
        );
    }
}
