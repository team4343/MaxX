package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class IndexerIOMax implements IndexerIO {
    private final VictorSPX top = new VictorSPX(6);

    public IndexerIOMax() {
    }

    @Override
    public void setVoltage(double voltage) {

    }

    @Override
    public double getBusVoltage() {
        return top.getBusVoltage();
    }

    @Override
    public double getTemperature() {
        return top.getTemperature();
    }
}
