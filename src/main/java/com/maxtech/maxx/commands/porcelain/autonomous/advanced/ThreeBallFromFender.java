package com.maxtech.maxx.commands.porcelain.autonomous.advanced;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import com.pathplanner.lib.PathPlanner;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class ThreeBallFromFender extends AutonomousSequentialCommandGroup {
    Trajectory start = PathPlanner.loadPath("Right Fender.0", 5, 2);
    Trajectory middle = PathPlanner.loadPath("Right Fender.1", 5, 2);
    Trajectory end = PathPlanner.loadPath("Right Fender.2", 5, 2);

    public ThreeBallFromFender() {
        addCommands(
                new ShootHighFor(2),
                new ParallelDeadlineGroup(
                        new RunTrajectory(start),
                        new SetIntake(true)
                ),
                new RunTrajectory(middle),
                new ParallelDeadlineGroup(
                        new RunTrajectory(end),
                        new SetIntake(false)
                ),
                new ShootHighFor(2)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return start.getInitialPose();
    }
}
