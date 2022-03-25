package com.maxtech.maxx.subsystems.flywheel;

import com.revrobotics.CANSparkMax;

import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;

public class FlywheelIOPeter implements FlywheelIO {
    private final CANSparkMax master = new CANSparkMax(5, kBrushless);
    private final CANSparkMax slave = new CANSparkMax(6, kBrushless);

    public FlywheelIOPeter() {
        master.setInverted(true);
        slave.follow(master, true);
    }

    @Override
    public void setVoltage(double voltage) {
        master.setVoltage(voltage);
    }

    @Override
    public void setVelocity(double velocity) {
        //master.set(velocity);
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
        return 0;
    }

    @Override
    public double getCurrent() {
        return 0;
    }
}
