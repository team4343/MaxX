package com.maxtech.lib.logging;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Table {
    String name;

    public Table(String name) {
        this.name = name;
    }

    public void putDouble(String key, double value) {
        SmartDashboard.putNumber(name + "/" + key, value);
    }

    public double getDouble(String key, double defaultValue) {
        return SmartDashboard.getNumber(key, defaultValue);
    }
}
