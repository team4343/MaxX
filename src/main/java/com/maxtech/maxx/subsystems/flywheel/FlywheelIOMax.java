package com.maxtech.maxx.subsystems.flywheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.maxtech.lib.logging.RobotLogger;
import com.maxtech.maxx.Constants;

public class FlywheelIOMax implements FlywheelIO {
    private final TalonFX motor = new TalonFX(Constants.Flywheel.id);

    public FlywheelIOMax() {
        motor.configVoltageCompSaturation(Constants.Flywheel.maxVoltage);
        motor.configClosedloopRamp(1);
        motor.configOpenloopRamp(1);
    }

    @Override
    public void setVoltage(double voltage) {
        RobotLogger.getInstance().dbg("Setting voltage to %s", voltage);
        motor.set(ControlMode.PercentOutput, voltage / Constants.Flywheel.maxVoltage);
    }

    @Override
    public double getVelocity() {
        return motor.getSelectedSensorVelocity() * 60 * 10 / 2048 / (36 / 16f);
    }

    @Override
    public double getVoltage() {
        return motor.getMotorOutputVoltage();
    }
}
