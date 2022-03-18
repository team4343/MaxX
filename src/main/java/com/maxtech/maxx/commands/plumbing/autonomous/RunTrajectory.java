package com.maxtech.maxx.commands.plumbing.autonomous;

import com.maxtech.maxx.commands.plumbing.drivetrain.ArcadeDrive;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.maxtech.maxx.Constants.Drive.*;
import static com.maxtech.maxx.Constants.Drive.kpDriveVelocity;

public class RunTrajectory extends SequentialCommandGroup {
    private final Drive drivetrain = Drive.getInstance();

    public RunTrajectory(Trajectory trajectory) {
        drivetrain.getField().getObject("trajectory").setTrajectory(trajectory);

        RamseteCommand command = new RamseteCommand(
                trajectory,
                drivetrain::getPose,
                new RamseteController(ramseteB, ramseteZeta),
                new SimpleMotorFeedforward(
                        ksVolts,
                        kvVolts,
                        kaVolts
                ),
                kinematics,
                drivetrain::getWheelSpeeds,
                new PIDController(kpDriveVelocity, 0, 0),
                new PIDController(kpDriveVelocity, 0, 0),
                drivetrain::tankDriveVolts,
                drivetrain
        );

        addCommands(command, new InstantCommand(drivetrain::stop, drivetrain));
    }
}
