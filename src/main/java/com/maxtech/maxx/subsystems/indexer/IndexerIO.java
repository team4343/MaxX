package com.maxtech.maxx.subsystems.indexer;

public interface IndexerIO {
    void set(double top, double bottom);
    IndexerSensors get();
}
