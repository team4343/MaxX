package com.maxtech.maxx.commands.plumbing.drivetrain;

import com.maxtech.maxx.subsystems.drivetrain.Drive;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.wpilibj2.command.RamseteCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;

import static com.maxtech.maxx.Constants.Drive.*;

/** Given a Trajectory, run it with the RamseteCommand. */
public class DriveTrajectory extends SequentialCommandGroup {
    Trajectory trajectory;

    Drive drivetrain = Drive.getInstance();

    public DriveTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;

        // Create a voltage constraint, which we will use in the configuration below.
        DifferentialDriveVoltageConstraint voltageConstraint =
                new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(
                                ksVolts,
                                kvVoltSecondsPerRadian,
                                kaVoltSecondsSquaredPerRadian),
                        kinematics,
                        maxVoltage);

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
