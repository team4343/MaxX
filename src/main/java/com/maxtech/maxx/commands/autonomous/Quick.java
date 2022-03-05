package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.commands.drivetrain.ArcadeDrive;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Quick extends SequentialCommandGroup {
    public Quick() {
        addCommands(
                new ArcadeDrive(.3, 0)
        );
    }
}
