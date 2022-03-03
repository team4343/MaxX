package com.maxtech.maxx.subsystems.flywheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.Constants;

public class FlywheelIOMax implements FlywheelIO {
    private final TalonFX motor = new TalonFX(Constants.Flywheel.id);

    public FlywheelIOMax() {
        motor.configSelectedFeedbackSensor(FeedbackDevice.PulseWidthEncodedPosition,
                Constants.Flywheel.pidID,
                Constants.Flywheel.TimeoutMs);
        motor.setSensorPhase(Constants.Flywheel.SensorPhase);
        motor.setInverted(Constants.Flywheel.MotorInvert);

        /* Config Position Closed Loop gains in slot0, typically kF stays zero. */
        motor.config_kF(Constants.Flywheel.pidID, Constants.Flywheel.F, Constants.Flywheel.TimeoutMs);
        motor.config_kP(Constants.Flywheel.pidID, Constants.Flywheel.P, Constants.Flywheel.TimeoutMs);
        motor.config_kI(Constants.Flywheel.pidID, Constants.Flywheel.I, Constants.Flywheel.TimeoutMs);
        motor.config_kD(Constants.Flywheel.pidID, Constants.Flywheel.D, Constants.Flywheel.TimeoutMs);

        motor.set(ControlMode.PercentOutput, 0);
        motor.set(ControlMode.Velocity, 0);
    }

    @Override
    public void setVelocity(double velocity) {
        RobotLogger.getInstance().dbg("Setting velocity to %s", velocity);
        motor.set(ControlMode.Velocity, velocity * (36 / 16f) * 4096 / (60 * 10));
    }

    @Override
    public double getVelocity() {
        return motor.getSelectedSensorVelocity() * 60 * 10 / 4096 / (36 / 16f);
    }

    @Override
    public double getVoltage() {
        return motor.getMotorOutputVoltage();
    }
}
