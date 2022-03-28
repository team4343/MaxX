package com.maxtech.maxx.commands.porcelain.autonomous;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.maxx.commands.plumbing.autonomous.RunTrajectory;
import com.maxtech.maxx.commands.porcelain.intake.LowerIntakeFor;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import com.maxtech.maxx.commands.porcelain.shooter.ShootHighFor;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;

import static com.maxtech.maxx.RobotContainer.decide;

public class TwoBallFromCornerB extends AutonomousSequentialCommandGroup {
    private final Drive drivetrain = Drive.getInstance();

    public TwoBallFromCornerB() {
        addRequirements(drivetrain);

        Trajectory start = loadPathweaverTrajectory("paths/corner to ball B.wpilib.json");
        Trajectory finish = loadPathweaverTrajectory("paths/ball B to fender.wpilib.json");

        addCommands(
                new InstantCommand(decide(
                        () -> drivetrain.setDirection(false),
                        () -> drivetrain.setDirection(true)),
                        drivetrain),
                new ParallelDeadlineGroup(
                        new RunTrajectory(start),
                        new SetIntake(true)
                ),
                new LowerIntakeFor(.5),
                new RunTrajectory(finish),
                new ShootHighFor(4)
        );
    }


    @Override
    public Pose2d getStartingPosition() {
        return new Pose2d(5.879, 5.383, new Rotation2d(-0.097, 0.065));
    }
}
