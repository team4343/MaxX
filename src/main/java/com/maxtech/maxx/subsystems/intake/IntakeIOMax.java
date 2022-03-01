package com.maxtech.maxx.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class IntakeIOMax implements IntakeIO {
    private final VictorSPX pivotMotor = new VictorSPX(6);
    private final VictorSPX motor = new VictorSPX(7);

    @Override
    public void set(double value) {
        motor.set(ControlMode.PercentOutput, value);
    }
}
