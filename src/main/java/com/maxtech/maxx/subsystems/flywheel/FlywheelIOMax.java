package com.maxtech.maxx.subsystems.flywheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.maxtech.maxx.Constants;

public class FlywheelIOMax implements FlywheelIO {
    private final TalonFX motor = new TalonFX(Constants.Flywheel.id);

    public FlywheelIOMax() {
        motor.configVoltageCompSaturation(Constants.Flywheel.maxVoltage);
    }

    @Override
    public void setVoltage(double voltage) {
        motor.set(ControlMode.PercentOutput, voltage * 1 / Constants.Flywheel.maxVoltage);
    }

    @Override
    public double getVelocity() {
        return motor.getMotorOutputVoltage();
    }
}
