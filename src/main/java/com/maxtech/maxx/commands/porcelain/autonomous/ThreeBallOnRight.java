package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.LowerIntakeFor;
import com.maxtech.maxx.commands.porcelain.shooter.ShootFarHighFor;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

public class ThreeBallOnRight extends AutonomousSequentialCommandGroup {
    Trajectory start = loadPathweaverTrajectory("paths/corner to ball A.wpilib.json");
    Trajectory middle = loadPathweaverTrajectory("paths/ball A to ball B.wpilib.json");
    Trajectory middle2 = loadPathweaverTrajectory("paths/ball A to ball B part 2.wpilib.json");
    Trajectory toStation = loadPathweaverTrajectory("paths/ball B to STATION.wpilib.json");
    Trajectory end = loadPathweaverTrajectory("paths/STATION to fender.wpilib.json");

    public ThreeBallOnRight() {
        addCommands(
                new ParallelDeadlineGroup(
                        new RunTrajectory(start),
                        new LowerIntakeFor(5)
                ),
                new RunTrajectory(middle),
                new RunTrajectory(middle2),
                new ShootFarHighFor(2),
                new ParallelDeadlineGroup(
                        new ShootFarHighFor(2),
                        new LowerIntakeFor(2)
                ),
                new RunTrajectory(toStation),
                new LowerIntakeFor(2),
                new RunTrajectory(end),
                new ShootHighFor(2)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return start.getInitialPose();
    }
}
