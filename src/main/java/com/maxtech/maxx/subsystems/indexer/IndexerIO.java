package com.maxtech.maxx.subsystems.indexer;

public interface IndexerIO {
    void set(Double bottom, Double top);
    IndexerSensors get();
}
