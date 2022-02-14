package com.maxtech.maxx;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.commands.SetFlywheelCommand;
import com.maxtech.maxx.commands.autonomous.ExamplePath;
import com.maxtech.maxx.subsystems.DriveSubsystem;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;

/**
 * The bulk connector for our robot. This class unifies subsystems, commands, and button bindings under one place. This
 * is then called from {@link Robot}.
 */
public class RobotContainer {
    /**
     * A handle to an Xbox controller on port 0.
     */
    public final XboxController masterController = new XboxController(0);

    private final DriveSubsystem drivetrain = new DriveSubsystem();
    private final Flywheel flywheel = Flywheel.getInstance();

    public RobotContainer() {
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
        drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.arcade(masterController.getLeftY(), masterController.getRightX()), drivetrain));
    }

    /**
     * This method is used to get the command for autonomous, used in {@link Robot}.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new SetFlywheelCommand(100);
        // return new ExamplePath(drivetrain);
    }
}
