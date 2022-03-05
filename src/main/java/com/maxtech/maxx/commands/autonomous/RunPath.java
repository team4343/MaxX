package com.maxtech.maxx.commands.autonomous;

import com.maxtech.maxx.commands.drivetrain.TankDrive;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import java.io.IOException;
import java.nio.file.Path;

import static com.maxtech.maxx.Constants.Drive.*;

public class RunPath extends SequentialCommandGroup {
    private final Drive drivetrain = Drive.getInstance();

    RamseteCommand command;

    public RunPath(Trajectory trajectory) {
        addRequirements(drivetrain);

        // Finally, create our actual command.
        RamseteCommand command = new RamseteCommand(
                trajectory,
                drivetrain::getPose,
                new RamseteController(ramseteB, ramseteZeta),
                new SimpleMotorFeedforward(
                        ksVolts,
                        kvVoltSecondsPerRadian,
                        kaVoltSecondsSquaredPerRadian
                ),
                kinematics,
                drivetrain::getWheelSpeeds,
                new PIDController(kpDriveVelocity, 0, 0),
                new PIDController(kpDriveVelocity, 0, 0),
                drivetrain::tank,
                drivetrain
        );

        addCommands(command, new TankDrive(0, 0, drivetrain));
    }
}
