package com.maxtech.maxx.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.lib.command.Subsystem;
import com.maxtech.lib.statemachines.StateMachine;
import com.maxtech.lib.statemachines.StateMachineMeta;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Intake extends Subsystem {
    private static Intake instance;

    public static Intake getInstance() {
        if (instance == null) {
            instance = new Intake();
        }

        return instance;
    }

    private enum IntakeState {
        Raised, Lowered,
    }

    StateMachine<IntakeState> statemachine = new StateMachine<>("Intake", IntakeState.Raised);

    private Intake() {
        statemachine.associateState(IntakeState.Raised, this::handleRaised);
        statemachine.associateState(IntakeState.Lowered, this::handleLowered);
        statemachine.start();
    }

    private final VictorSPX intakeMotor = new VictorSPX(Constants.intakeID);

    private void handleRaised(StateMachineMeta m) {

    }

    private void handleLowered(StateMachineMeta m) {

    }

    @Override
    public void sendTelemetry(String prefix) {
        SmartDashboard.putNumber(prefix + "voltage", intakeMotor.getBusVoltage());
        SmartDashboard.putNumber(prefix + "temperature", intakeMotor.getTemperature());
    }

    public void start() {
        intakeMotor.set(ControlMode.Velocity, 1);
    }

    public void stop() {
        intakeMotor.set(ControlMode.Velocity, 0);
    }
}