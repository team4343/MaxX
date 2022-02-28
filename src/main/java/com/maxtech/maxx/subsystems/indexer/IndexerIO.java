package com.maxtech.maxx.subsystems.indexer;

public interface IndexerIO {
    void setVoltage(double voltage);

    double getBusVoltage();
    double getTemperature();
}
