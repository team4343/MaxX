package com.maxtech.maxx.subsystems.flywheel;

public interface FlywheelIO {
    void setVelocity(double velocity);

    double getVelocity();
    double getVoltage();
}
