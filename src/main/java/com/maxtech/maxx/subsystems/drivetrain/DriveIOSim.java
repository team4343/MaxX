package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.lib.logging.Table;

import java.io.IOException;

public class DriveIOSim implements DriveIO {
    public void drive(double ls, double rs) {
        System.out.println("Running simulation drivetrain.");

        Table table = new Table("Drivetrain");
        try {
            table.serialize();
        } catch (IOException e) {
            System.out.println("Failed to write to file while creating drivetrain simulation JSON:");
            System.out.println(e);
        }
    }
}
