package com.maxtech.maxx.subsystems.indexer;

public class IndexerIOPeter implements IndexerIO {
    @Override
    public IndexerSensors getSensors() {
        return new IndexerSensors(false, false);
    }
}
