package com.maxtech.maxx.subsystems.flywheel;

public interface FlywheelIO {
    void setVoltage(double voltage);

    double getVelocity();
    double getVoltage();
}
