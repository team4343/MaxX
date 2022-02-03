package com.maxtech.maxx;

import com.maxtech.lib.logging.Logger;
import com.maxtech.maxx.subsystems.drivetrain.DriveIOReal;
import com.maxtech.maxx.subsystems.drivetrain.DriveIOSim;
import com.maxtech.maxx.subsystems.drivetrain.DriveSubsystem;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

import java.lang.reflect.Field;

import static edu.wpi.first.wpilibj.RobotBase.isReal;

/**
 * The bulk connector for our robot. This class unifies subsystems, commands, and button bindings under one place. This
 * is then called from {@link Robot}.
 */
public class RobotContainer {
    /**
     * A handle to an Xbox controller on port 0.
     */
    public final XboxController masterController = new XboxController(0);

    /**
     * Our local Drive subsystem.
     */
    private final DriveSubsystem drivetrain;

    public RobotContainer() {
        if (isReal()) {
            drivetrain = new DriveSubsystem(new DriveIOReal());
        } else {
            drivetrain = new DriveSubsystem(new DriveIOSim());
        }

        // Configure the button bindings.
        configureButtonBindings();
    }

    /**
     * Our global button -> command mappings.
     * <p>
     * More documentation on how this is achieved at https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
     */
    private void configureButtonBindings() {
        // We set the default command for the drivetrain to arcade driving based on the controller values.
        drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.tank(-masterController.getLeftY(), -masterController.getLeftX()), drivetrain));
    }

    /**
     * This method is used to get the command for autonomous, used in {@link Robot}.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return (new RunCommand(() -> drivetrain.arcade(0.1, 0)));
    }
}
