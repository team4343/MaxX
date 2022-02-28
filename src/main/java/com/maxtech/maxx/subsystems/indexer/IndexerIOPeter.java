package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class IndexerIOPeter implements IndexerIO {
    TalonFX left = new TalonFX(10);
    TalonFX right = new TalonFX(11);

    public IndexerIOPeter() {
        right.follow(left);
    }

    @Override
    public void setVoltage(double voltage) {}

    @Override
    public double getBusVoltage() {
        return left.getBusVoltage();
    }

    @Override
    public double getTemperature() {
        return left.getTemperature();
    }
}
