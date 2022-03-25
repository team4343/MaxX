package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.controllers.SimpleFlywheelController;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;

import static com.maxtech.maxx.RobotContainer.decide;
import static com.maxtech.maxx.RobotContainer.decideIO;

public class Flywheel extends Subsystem {
    private static Flywheel instance;

    private final FlywheelIO io = decideIO(FlywheelIOMax.class, FlywheelIOPeter.class);
    private final StateMachine<State> statemachine = new StateMachine<>("Flywheel", State.Idle);

    private final SimpleFlywheelController controller = new SimpleFlywheelController(decide(0.018371, 0.020706), decide(0.0022809, 0.00074233));

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
        tab.addNumber("percentout", this::getPercentOut);
        tab.addNumber("current", this::getCurrent);

        // Associate handlers for states.
        statemachine.associateState(State.Idle, this::handleIdle);
        statemachine.associateState(State.Spinning, this::handleSpinning);
        statemachine.associateState(State.SpinningAtGoal, this::handleSpinningAtGoal);
    }

    private double getCurrent() {
        return io.getCurrent();
    }

    private double getPercentOut() {
        return io.getPercentOut();
    }

    private void handleIdle(StateMachineMeta meta) {
        // Set the voltage to zero, unless we have a goal.
        io.setVoltage(0);

        if (!atGoal()) {
            statemachine.toState(State.Spinning);
        }
    }

    private void handleSpinning(StateMachineMeta m) {
        // We are not at the goal, so spin to it.
        io.setVoltage(controller.computeNextVoltage(getVelocity()));

        if (controller.getDesiredVelocity() == 0) {
            statemachine.toState(State.Idle);
        }

        if (atGoal()) {
            statemachine.toState(State.SpinningAtGoal);
        }
    }

    private void handleSpinningAtGoal(StateMachineMeta m) {
        if (controller.getDesiredVelocity() == 0) {
            statemachine.toState(State.Idle);
        }

        // Make sure we stay at the goal.
        if (!atGoal()) {
            statemachine.toState(State.Spinning);
        }
    }

    public void stop() {
        controller.setDesiredVelocity(0);
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
