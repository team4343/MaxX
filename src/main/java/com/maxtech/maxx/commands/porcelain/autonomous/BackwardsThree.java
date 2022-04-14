package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.porcelain.drivetrain.ArcadeDrive;
import com.maxtech.maxx.commands.porcelain.shooter.ShootFarHighFor;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

import static com.maxtech.maxx.RobotContainer.decide;

public class BackwardsThree extends AutonomousSequentialCommandGroup {
    public BackwardsThree() {
        addCommands(
                new ShootFarHighFor(4),
                new ParallelDeadlineGroup(
                        new WaitCommand(2.5),
                        new ArcadeDrive(decide(1, -1) * 0.5, 0)
                )
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return new Pose2d();
    }
}
