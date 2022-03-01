package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IndexerIOMax implements IndexerIO {
    private final VictorSPX topMotor = new VictorSPX(6);

    private final DigitalInput topSensor = new DigitalInput(0);

    public IndexerIOMax() {
    }

    @Override
    public IndexerSensors getSensors() {
        return new IndexerSensors(true, false);
    }
}
