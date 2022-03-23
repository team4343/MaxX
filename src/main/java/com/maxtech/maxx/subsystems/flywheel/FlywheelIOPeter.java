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
    public void setVelocity(double velocity) {
        left.set(velocity);
    }

    @Override
    public double getVelocity() {
        return left.getEncoder().getVelocity();
    }

    @Override
    public double getVoltage() {
        return left.getBusVoltage();
    }

    @Override
    public double getPercentOut() {
        return 0;
    }

    @Override
    public double getCurrent() {
        return 0;
    }
}
