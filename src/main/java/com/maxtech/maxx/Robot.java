package com.maxtech.maxx;

import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.managers.DashboardValuesManager;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * A class, scheduled by the Java VM, to run.
 */
public class Robot extends TimedRobot {
    private RobotContainer robotContainer;
    private Command autonomousCommand;

    private final RobotLogger logger = RobotLogger.getInstance();
    private final DashboardValuesManager manager = DashboardValuesManager.getInstance();

    /**
     * This method is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
        // Start our logger.
        logger.start();

        // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
        // autonomous chooser on the dashboard.
        robotContainer = new RobotContainer();

        // Add telemetry subsystems to the manager, and start it.
        manager.addSubsystems(robotContainer.getTelemetrySubsystems());
        //manager.start();

        CommandScheduler.getInstance().onCommandInitialize((Command c) -> {
            logger.dbg("Running %s", c.getName());
        });
    }

    /**
     * This method is called every robot packet, no matter the mode. Use this for items like
     * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
     *
     * <p>This runs after the mode specific periodic methods, but before LiveWindow and
     * SmartDashboard integrated updating.
     */
    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    /**
     * This method is called once each time the robot enters disabled mode.
     */
    @Override
    public void disabledInit() {
    }

    /**
     * This method is called once per scheduler run during disabled mode.
     */
    @Override
    public void disabledPeriodic() {
    }

    /**
     * RunIndexer the autonomous command selected by {@link RobotContainer}.
     */
    @Override
    public void autonomousInit() {
        logger.log("entered autonomous");
        autonomousCommand = robotContainer.getAutonomousCommand();

        // schedule the autonomous command (example)
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    /**
     * This method is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() {
    }

    @Override
    public void teleopInit() {
        // This makes sure that the autonomous stops running when
        // teleop starts running. If you want the autonomous to
        // continue until interrupted by another command, remove
        // this line or comment it out.
        if (autonomousCommand != null) {
            autonomousCommand.cancel();
        }
    }

    /**
     * This method is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() {
    }

    @Override
    public void testInit() {
        // Cancels all running commands at the start of test mode.
        CommandScheduler.getInstance().cancelAll();
    }

    /**
     * This method is called periodically during test mode.
     */
    @Override
    public void testPeriodic() {
    }
}
