package com.maxtech.maxx.subsystems.indexer;

import edu.wpi.first.wpilibj.smartdashboard.Field2d;

public interface IndexerIO {
    void set(double topMotor, double bottomMotor);

    IndexerSensors getSensors();
}
