package com.maxtech.maxx.subsystems.flywheel;

import com.revrobotics.CANSparkMax;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

public class FlywheelIOPeter implements FlywheelIO {
    private final CANSparkMax left = new CANSparkMax(8, kBrushless);
    private final CANSparkMax right = new CANSparkMax(9, kBrushless);

    public FlywheelIOPeter() {
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
