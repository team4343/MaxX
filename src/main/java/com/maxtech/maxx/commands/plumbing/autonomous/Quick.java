package com.maxtech.maxx.commands.plumbing.autonomous;

import com.maxtech.maxx.commands.plumbing.drivetrain.ArcadeDrive;
import com.maxtech.maxx.commands.plumbing.drivetrain.ArcadeDriveFor;
import com.maxtech.maxx.commands.porcelain.flywheel.SetFlywheelFor;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

public class Quick extends SequentialCommandGroup {
    public Quick() {
        addCommands(
                new SetFlywheelFor(Flywheel.FlywheelStates.ShootHigh, 4),
                new ArcadeDriveFor(.5, 0, 6),
                new ArcadeDrive(0, 0, Drive.getInstance())
        );
    }
}
