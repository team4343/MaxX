package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.plumbing.flywheel.SetFlywheelFarHigh;
import com.maxtech.maxx.commands.plumbing.flywheel.StopFlywheel;
import com.maxtech.maxx.commands.porcelain.intake.LowerIntakeFor;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import com.maxtech.maxx.commands.porcelain.shooter.ShootFarHighFor;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

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
                        new LowerIntakeFor(3)
                ),
                new ParallelDeadlineGroup(
                        new RunTrajectory(middle),
                        new SetFlywheelFarHigh()
                ),
                new ParallelDeadlineGroup(
                        new ShootFarHighFor(2.5),
                        new SequentialCommandGroup(
                                new WaitCommand(.5),
                                new RunTrajectory(middle2)
                        ),
                        new SetIntake(true)
                ),
                new StopFlywheel(),
                new RunTrajectory(toStation),
                new LowerIntakeFor(1),
                new RunTrajectory(end),
                new ShootHighFor(1)
        );
    }

    @Override
    public Pose2d getStartingPosition() {
        return start.getInitialPose();
    }
}
