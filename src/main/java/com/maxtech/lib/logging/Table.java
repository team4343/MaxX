package com.maxtech.lib.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.File;
import java.io.IOException;

public class Table {
    String name;

    public Table(String name) {
        this.name = name;
    }

    public void putDouble(String key, double value) {
        SmartDashboard.putNumber(name + "/" + key, value);
    }

    public double getDouble(String key, double defaultValue) {
        return SmartDashboard.getNumber(name + "/" + key, defaultValue);
    }

    /** Turn a Table into a CSV file. */
    public void serialize() throws IOException {
        NetworkTableInstance instance = NetworkTableInstance.getDefault();
        NetworkTable table = instance.getTable("");

        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(new File("log.json"), table);
    }

    /** Turn a CSV file into a Table. */
    public void deserialize() {}
}
