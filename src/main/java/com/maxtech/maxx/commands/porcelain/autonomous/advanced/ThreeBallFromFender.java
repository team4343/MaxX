package com.maxtech.maxx.commands.porcelain.autonomous.advanced;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import com.maxtech.maxx.commands.porcelain.intake.SetIntakeAndIndexer;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ThreeBallFromFender extends AutonomousSequentialCommandGroup {
    Trajectory start = loadPathweaverTrajectory("paths/simple.fender2.start.wpilib.json");
    Trajectory middle = loadPathweaverTrajectory("paths/simple.fender2.middle.wpilib.json");
    Trajectory end = loadPathweaverTrajectory("paths/simple.fender2.end.wpilib.json");

    public ThreeBallFromFender() {
        addCommands(
                new ShootHighFor(2),
                new ParallelDeadlineGroup(
                        new RunTrajectory(start),
                        new SetIntakeAndIndexer(true)
                ),
                new WaitCommand(1),
                new RunTrajectory(middle),
                new ParallelDeadlineGroup(
                        new RunTrajectory(end),
                        new SetIntakeAndIndexer(false)
                ),
                new WaitCommand(1),
                new ShootHighFor(2)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return start.getInitialPose();
    }
}
