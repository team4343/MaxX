package com.maxtech.maxx;

import com.maxtech.lib.command.AutonomousSequentialCommandGroup;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.commands.plumbing.climber.*;
import com.maxtech.maxx.commands.porcelain.NextLEDPattern;
import com.maxtech.maxx.commands.porcelain.autonomous.*;
import com.maxtech.maxx.commands.porcelain.autonomous.advanced.*;
import com.maxtech.maxx.commands.porcelain.intake.SetIntakeAndIndexer;
import com.maxtech.maxx.commands.porcelain.shooter.*;
import com.maxtech.maxx.subsystems.intake.Intake;
import com.maxtech.maxx.subsystems.LEDs;
import com.maxtech.maxx.subsystems.drivetrain.Drive;
import com.maxtech.maxx.subsystems.flywheel.Flywheel;
import com.maxtech.maxx.subsystems.indexer.Indexer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import org.jetbrains.annotations.NotNull;
import com.maxtech.maxx.Constants.Buttons;

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

    private final SendableChooser<AutonomousSequentialCommandGroup> autonomousCommand = new SendableChooser<>();

    public RobotContainer() {
        configureAutonomousCommand();
        configureButtonBindings();
    }

    private void configureAutonomousCommand() {
        autonomousCommand.setDefaultOption("simple from fender", new ThreeBallFromFender());
        autonomousCommand.addOption("full right side", new ThreeBallOnRight());
        autonomousCommand.addOption("hijack other balls", new Hijacking());
        autonomousCommand.addOption("three ball fender 1 through middle", new ThreeBallThroughMiddle());
        autonomousCommand.addOption("three ball fender 1 through middle ALTERNATIVE", new ThreeBallThroughMiddleAlternative());
        autonomousCommand.addOption("two ball from fender", new TwoBallFromFender());
        autonomousCommand.addOption("two ball from corner", new TwoBallFromCorner());
        autonomousCommand.addOption("two ball from corner B", new TwoBallFromCornerB());
        autonomousCommand.addOption("no-op", new NoOp());
        autonomousCommand.addOption("backwards 4", new BackwardsThree());

        var tab = Shuffleboard.getTab("Main");
        tab.add("Autonomous selector", autonomousCommand);
    }

    /**
     * Our global button -> command mappings. This method binds joystick buttons to commands, and sets default commands
     * that are run in the background.
     *
     * Default commands:
     *   * Drive takes a default command that reads stick values and calculates a resulting arcade drive. See
     *     https://www.desmos.com/calculator/xmotljqdal for more details.
     *
     * Button mappings: see /assets/mappings.png for more details.
     */
    private void configureButtonBindings() {
        drivetrain.setDefaultCommand(new RunCommand(() -> {
            double speed = masterController.getRightTriggerAxis() - masterController.getLeftTriggerAxis();
            double rotation = Math.min(Math.max((-masterController.getLeftX() * -masterController.getLeftX() * -masterController.getLeftX()) * 2, -1), 1); // -masterController.getLeftX() ^ 3

            drivetrain.arcade(speed, rotation);
        }, drivetrain));

        new JoystickButton(masterController, XboxController.Button.kLeftBumper.value).whenPressed(new NextLEDPattern());

        new JoystickButton(masterController, Buttons.Intake)
                .whenPressed(new SetIntakeAndIndexer(true))
                .whenReleased(new SetIntakeAndIndexer(false));

        // Shooter
        new JoystickButton(masterController, Buttons.ShootHigh).whenPressed(decideIO(ShootHigh.class, ShootHigh.class)).whenReleased(new StopShot());
        new JoystickButton(masterController, Buttons.ShootLow).whenPressed(new ShootLow()).whenReleased(new StopShot());

        // Toggle Drive
        new JoystickButton(masterController, Buttons.ToggleDriveDirection)
                .whenPressed(new InstantCommand(drivetrain::toggleDirection, drivetrain));

        // Basic Climb
        new JoystickButton(masterController, Buttons.Climb)
                 .whenPressed(new Extend())
                 .whenReleased(new Raise());

        // Hang
        new POVButton(masterController, Buttons.ShootLowPOV) // Down
                .whenPressed(new ShootFarHigh())
                .whenReleased(new StopShot());

        new POVButton(masterController, Buttons.ShootLimelightHighPOV) // Up
        .whenPressed(decideIO(ShootHighLimelight.class, ShootHigh.class)).whenReleased(new StopShot());
    }

    /**
     * This method is used to get the command for autonomous, used in {@link Robot}.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        var polyAuto = new BackwardsThree();
        drivetrain.setStartingPosition(decide(autonomousCommand.getSelected().getStartingPosition(), polyAuto.getStartingPosition()));
        return decide(autonomousCommand.getSelected(), polyAuto);
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
