package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;

import static com.revrobotics.CANSparkMax.ControlType.kVelocity;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

public class FlywheelIOPeter implements FlywheelIO {
    private final CANSparkMax master = new CANSparkMax(5, kBrushless);
    private final CANSparkMax slave = new CANSparkMax(6, kBrushless);

    public FlywheelIOPeter() {
        master.setInverted(true);
        slave.follow(master, true);

        master.getPIDController().setP(Constants.Flywheel.P);
        master.getPIDController().setI(Constants.Flywheel.I);
        master.getPIDController().setD(Constants.Flywheel.D);
        master.getPIDController().setFF(Constants.Flywheel.F);
    }

    @Override
    public void setVoltage(double voltage) {
        master.set(voltage / 12);
    }

    @Override
    public void setVelocity(double velocity) {
        if (velocity == 0) master.set(0);
        master.getPIDController().setReference((velocity / 600), kVelocity);
    }

    @Override
    public double getVelocity() {
        return master.getEncoder().getVelocity();
    }

    @Override
    public double getVoltage() {
        return master.getBusVoltage();
    }

    @Override
    public double getPercentOut() {
        return master.getOutputCurrent();
    }

    @Override
    public double getCurrent() {
        return getVoltage();
    }
}
