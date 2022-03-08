package com.maxtech.maxx;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.commands.NextLEDPattern;
import com.maxtech.maxx.commands.autonomous.Quick;
import com.maxtech.maxx.commands.climber.Extend;
import com.maxtech.maxx.commands.climber.Raise;
import com.maxtech.maxx.commands.intake.DumpIntake;
import com.maxtech.maxx.commands.intake.SetIntake;
import com.maxtech.maxx.commands.flywheel.SetFlywheel;
import com.maxtech.maxx.subsystems.indexer.IndexerIO;
import com.maxtech.maxx.subsystems.indexer.IndexerIOMax;
import com.maxtech.maxx.subsystems.indexer.IndexerIOPeter;
import com.maxtech.maxx.subsystems.intake.Intake;
import com.maxtech.maxx.subsystems.LEDs;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

import java.util.List;

/**
 * The bulk connector for our robot. This class unifies subsystems, commands, and button bindings under one place. This
 * is then called from {@link Robot}.
 */
public class RobotContainer {
    public static final int teamNumber = 4343;

    private static final RobotLogger logger = RobotLogger.getInstance();

    /**
     * A handle to an Xbox controller on port 0.
     */
    private final XboxController masterController = new XboxController(0);

    private final Drive drivetrain = Drive.getInstance();
    private final Flywheel flywheel = Flywheel.getInstance();
    private final Indexer indexer = Indexer.getInstance();
    private final Intake intake = Intake.getInstance();
    private final LEDs leds = LEDs.getInstance();

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
        drivetrain.setDefaultCommand(new RunCommand(() -> {
            double speed = masterController.getRightTriggerAxis() - masterController.getLeftTriggerAxis();
            double rotation = -(masterController.getLeftX() / 2);

            drivetrain.arcade(speed, rotation);
        }, drivetrain));

        indexer.setDefaultCommand(new InstantCommand(indexer::run, indexer));

        // TODO: review this method of binding commands to methods. It's almost certainly too verbose.
        new JoystickButton(masterController, XboxController.Button.kLeftBumper.value).whenPressed(new NextLEDPattern());
        new JoystickButton(masterController, Constants.Buttons.Intake)
                .whenPressed(new SetIntake(true))
                .whenReleased(new SetIntake(false));
        new JoystickButton(masterController, Constants.Buttons.ShootHigh).whenPressed( new SetFlywheel(Flywheel.FlywheelStates.ShootHigh)).whenReleased(new SetFlywheel(Flywheel.FlywheelStates.Idle));
        new JoystickButton(masterController, Constants.Buttons.ShootLow).whileHeld( new SetFlywheel(Flywheel.FlywheelStates.ShootLow)).whenReleased(new SetFlywheel(Flywheel.FlywheelStates.Idle));
        new JoystickButton(masterController, Constants.Buttons.Climb)
                .whenPressed(new Extend())
                .whenReleased(new Raise());
        new JoystickButton(masterController, Constants.Buttons.ToggleDriveDirection).whenPressed(new RunCommand(drivetrain::toggleDirection, drivetrain));
        new POVButton(masterController, Constants.Buttons.DumpPOV)
                .whenPressed(new DumpIntake())
                .whenReleased(new SetIntake(false));
    }

    /**
     * This method is used to get the command for autonomous, used in {@link Robot}.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new Quick();
    }

    /** All of these subsystems send telemetry. */
    public List<Subsystem> getTelemetrySubsystems() {
        return List.of(drivetrain, flywheel, intake);
    }

    /** Decide on the I/O based on the current team number. */
    public static <R, T extends R, U extends R> R decideIO(Class<T> m4343, Class<U> m914) {
        try {
            if (teamNumber == 4343) {
                return m4343.newInstance();
            } else if (teamNumber == 914) {
                return m914.newInstance();
            } else {
                logger.err("Could not pick I/O. Team number: %s, based on %s and %s.", teamNumber, m4343, m914);
            }
        } catch (InstantiationException | IllegalAccessException e) {
            logger.err("%s", e);
        }

        return null;
    }
}
