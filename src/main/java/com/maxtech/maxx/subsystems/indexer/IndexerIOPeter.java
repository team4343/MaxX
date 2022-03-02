package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IndexerIOPeter implements IndexerIO {
    private final VictorSPX topMotor = new VictorSPX(6);
    private final VictorSPX bottomMotor = new VictorSPX(7);

    private final DigitalInput topSensor = new DigitalInput(0);
    private final DigitalInput bottomSensor = new DigitalInput(0);

    @Override
    public void set(Double bottom, Double top) {
        topMotor.set(ControlMode.PercentOutput, top);
        bottomMotor.set(ControlMode.PercentOutput, bottom);
    }

    @Override
    public IndexerSensors get() {
        return new IndexerSensors(topSensor.get(), bottomSensor.get());
    }
}
