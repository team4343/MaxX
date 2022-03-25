package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.DigitalInput;

public class IndexerIOMax implements IndexerIO {
    private final VictorSPX bottomMotor = new VictorSPX(Constants.Indexer.bottomID);
    private final VictorSPX topMotor = new VictorSPX(Constants.Indexer.topID);

    private final DigitalInput topSensor = new DigitalInput(Constants.Indexer.topSensorDIO);
    private final DigitalInput bottomSensor = new DigitalInput(Constants.Indexer.bottomSensorDIO);

    private static double topLast = 0;
    private static double botLast = 0;

    @Override
    public void set(double top, double bottom) {

        topMotor.set(ControlMode.PercentOutput, top);
        bottomMotor.set(ControlMode.PercentOutput, bottom);
    }

    @Override
    public IndexerSensors getSensors() {
        return new IndexerSensors(topSensor.get(), bottomSensor.get());
    }
}
