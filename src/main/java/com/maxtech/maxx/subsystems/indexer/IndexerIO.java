package com.maxtech.maxx.subsystems.indexer;

public interface IndexerIO {
    void set(double topMotor, double bottomMotor);
    IndexerSensors get();
}
