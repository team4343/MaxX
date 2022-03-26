package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.porcelain.drivetrain.ArcadeDriveFor;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import edu.wpi.first.math.geometry.Pose2d;

public class BackwardsThree extends AutonomousSequentialCommandGroup {
    public BackwardsThree() {
        addCommands(
                new ShootHighFor(4),
                new ArcadeDriveFor(-0.5, 0, 2.5));
    }

    @Override
    public Pose2d getStartingPosition() {
        return new Pose2d();
    }
}
