package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static com.maxtech.maxx.RobotContainer.decideIO;

public class Flywheel extends Subsystem {
    private static Flywheel instance;

    private FlywheelIO io = decideIO(FlywheelIOMax.class, FlywheelIOPeter.class);
    private final StateMachine<State> statemachine = new StateMachine<>("Flywheel", State.Idle);

    private static final RobotLogger logger = RobotLogger.getInstance();
    private final SimpleFlywheelController controller = new SimpleFlywheelController(1, 1);

    private enum State {
        Idle, Spinning, SpinningAtGoal,
    }

    public static Flywheel getInstance() {
        if (instance == null) {
            instance = new Flywheel();
        }

        return instance;
    }

    private Flywheel() {
        var tab = Shuffleboard.getTab("Flywheel");
        tab.addString("state", statemachine::currentStateName);
        tab.addNumber("desired", controller::getDesiredVelocity);
        tab.addBoolean("at goal", this::atGoal);
        tab.addNumber("velocity", this::getVelocity);

        // Associate handlers for states.
        statemachine.associateState(State.Idle, this::handleIdle);
        statemachine.associateState(State.Spinning, this::handleSpinning);
        statemachine.associateState(State.SpinningAtGoal, this::handleSpinningAtGoal);
        statemachine.runCurrentHandler();
    }

    private void handleIdle(StateMachineMeta meta) {
        // Set the velocity to zero, unless we have a goal.
        io.setVoltage(0);

        if (!atGoal()) {
            statemachine.toState(State.Spinning);
        }
    }

    private void handleSpinning(StateMachineMeta m) {
        // We are not at the goal, so spin to it.
        io.setVoltage(controller.computeNextVoltage(getVelocity()));

        if (atGoal()) {
            statemachine.toState(State.SpinningAtGoal);
        }
    }

    private void handleSpinningAtGoal(StateMachineMeta m) {
        // Make sure we stay at the goal.
        if (!atGoal()) {
            statemachine.toState(State.Spinning);
        }
    }

    public void run() {
        statemachine.runCurrentHandler();
    }

    public void stop() {
        statemachine.toState(State.Idle);
    }

    public void setGoal(double rpm) {
        controller.setDesiredVelocity(rpm);
    }

    public boolean atGoal() {
        return controller.withinEpsilon(getVelocity());
    }

    public double getVelocity() {
        return io.getVelocity();
    }

    @Override
    public void periodic() {
        statemachine.runCurrentHandler();
    }
}
