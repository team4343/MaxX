package com.maxtech.maxx;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.commands.plumbing.autonomous.OneBallAuto;
import com.maxtech.maxx.commands.plumbing.autonomous.ReversingAuto;
import com.maxtech.maxx.commands.plumbing.climber.Rotate;
import com.maxtech.maxx.commands.porcelain.NextLEDPattern;
import com.maxtech.maxx.commands.porcelain.ShootHigh;
import com.maxtech.maxx.commands.porcelain.ShootLow;
import com.maxtech.maxx.commands.porcelain.StopShot;
import com.maxtech.maxx.commands.plumbing.autonomous.TwoBallAuto;
import com.maxtech.maxx.commands.plumbing.climber.Extend;
import com.maxtech.maxx.commands.plumbing.climber.Raise;
import com.maxtech.maxx.commands.porcelain.flywheel.SetFlywheelLow;
import com.maxtech.maxx.commands.porcelain.flywheel.StopFlywheel;
import com.maxtech.maxx.commands.porcelain.indexer.RunIndexer;
import com.maxtech.maxx.commands.porcelain.intake.DumpIntake;
import com.maxtech.maxx.commands.porcelain.intake.SetIntake;
import com.maxtech.maxx.subsystems.intake.Intake;
import com.maxtech.maxx.subsystems.LEDs;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import org.jetbrains.annotations.NotNull;
import com.maxtech.maxx.Constants.Buttons;

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

    private final SendableChooser<Command> autonomousCommand = new SendableChooser<>();

    public RobotContainer() {
        configureAutonomousCommand();
        configureButtonBindings();
    }

    private void configureAutonomousCommand() {
        autonomousCommand.setDefaultOption("reverse + pickup + shoot", new TwoBallAuto());
        autonomousCommand.addOption("shoot + reverse", new OneBallAuto());
        autonomousCommand.addOption("reverse", new ReversingAuto());
    }

    /**
     * Our global button -> command mappings.
     * <p>
     * More documentation on how this is achieved at https://docs.wpilib.org/en/stable/docs/software/commandbased/binding-commands-to-triggers.html
     */
    private void configureButtonBindings() {
        drivetrain.setDefaultCommand(new RunCommand(() -> {
            double speed = masterController.getRightTriggerAxis() - masterController.getLeftTriggerAxis();
            double rotation = Math.min(Math.max(Math.pow(-masterController.getLeftX(), 3) * 2, -1), 1);
            // https://www.desmos.com/calculator/xmotljqdal

            drivetrain.arcade(speed, rotation);
        }, drivetrain));

        // Start Indexer system
        indexer.setDefaultCommand(new RunIndexer());

        new JoystickButton(masterController, XboxController.Button.kLeftBumper.value).whenPressed(new NextLEDPattern());
        new JoystickButton(masterController, Buttons.Intake)
                .whenPressed(new SetIntake(true))
                .whenReleased(new SetIntake(false));

        // Shooter
        new JoystickButton(masterController, Buttons.ShootHigh).whenPressed(new ShootHigh()).whenReleased(new StopShot());
        new JoystickButton(masterController, Buttons.ShootLow).whenPressed(new ShootLow()).whenReleased(new StopShot());

        // Toggle Drive
        new JoystickButton(masterController, Buttons.ToggleDriveDirection)
                .whenPressed(new InstantCommand(drivetrain::toggleDirection, drivetrain));

        // Dump Intake TODO Add a debounce
        new POVButton(masterController, Buttons.DumpPOV)
                .whenPressed(new DumpIntake())
                .whenReleased(new SetIntake(false));

        // Basic Climb
        //new JoystickButton(masterController, Buttons.Climb)
        //        .whenPressed(new Extend())
        //        .whenReleased(new Raise());

        // TODO Add a debounce to the climber.
        // Extend Climb
        //new POVButton(masterController, Buttons.ExtendClimbPOV)
        //        .whenPressed(new Extend());

        // Retract Winch
        //new POVButton(masterController, Buttons.ReleaseClimbPOV)
        //        .whenPressed(new Raise());

        // Initiate Climber
        new POVButton(masterController, Buttons.HangClimbPOV)
                .whenPressed(new Rotate());

    }

    /**
     * This method is used to get the command for autonomous, used in {@link Robot}.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return autonomousCommand.getSelected();
    }

    /** All of these subsystems send telemetry. */
    public List<Subsystem> getTelemetrySubsystems() {
        return List.of(drivetrain, flywheel, intake);
    }

    /** Decide on the I/O based on the current team number. */
    public static <R, T extends R, U extends R> @NotNull R decideIO(Class<T> m4343, Class<U> m914) {
        try {
            return decide(m4343, m914).newInstance();
        } catch (NullPointerException | InstantiationException | IllegalAccessException e) {
            logger.err("%s", e);
            return null;
        }
    }

    /** Given a value for 4343 and a value for 914, return this robot's choice. */
    public static <R, T extends R, U extends R> R decide(T m4343, U m914) {
        if (teamNumber == 4343) {
            return m4343;
        } else if (teamNumber == 914) {
            return m914;
        } else {
            logger.err("Could not choose between given variants. Team number: %s, based on %s and %s. " +
                    "Defaulting to m4343.", teamNumber, m4343, m914);
            return m4343;
        }
    }
}
