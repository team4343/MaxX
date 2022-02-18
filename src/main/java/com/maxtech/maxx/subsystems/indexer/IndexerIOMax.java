package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class IndexerIOMax implements IndexerIO {
    TalonFX left = new TalonFX(10);
    TalonFX right = new TalonFX(11);

    public IndexerIOMax() {
        right.follow(left);
    }

    @Override
    public double getBusVoltage() {
        return left.getBusVoltage();
    }

    @Override
    public double getTemperature() {
        return left.getTemperature();
    }
}
