package com.maxtech.maxx.subsystems.flywheel;

import com.revrobotics.CANSparkMax;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

public class FlywheelIOMax implements FlywheelIO {
    private final CANSparkMax left = new CANSparkMax(0, kBrushless);
    private final CANSparkMax right = new CANSparkMax(1, kBrushless);

    public FlywheelIOMax() {
        right.follow(left);
    }

    @Override
    public void setVoltage(double voltage) {
        left.setVoltage(voltage);
    }

    @Override
    public double getVelocity() {
        return left.getEncoder().getVelocity();
    }
}
