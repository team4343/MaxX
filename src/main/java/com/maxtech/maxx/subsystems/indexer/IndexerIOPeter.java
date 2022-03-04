package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IndexerIOPeter implements IndexerIO {
    private final VictorSPX topMotor = new VictorSPX(6);
    private final VictorSPX bottomMotor = new VictorSPX(7);

    private final DigitalInput topSensor = new DigitalInput(11);
    private final DigitalInput bottomSensor = new DigitalInput(10);

    @Override
    public void set(double bottom, double top) {
        topMotor.set(ControlMode.PercentOutput, top);
        bottomMotor.set(ControlMode.PercentOutput, bottom);
    }

    @Override
    public IndexerSensors getSensors() {
        return new IndexerSensors(topSensor.get(), bottomSensor.get());
    }
}
