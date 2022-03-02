package com.maxtech.maxx.subsystems.indexer;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IndexerIOMax implements IndexerIO {
    private final VictorSPX bottomMotor = new VictorSPX(8);
    private final VictorSPX topMotor = new VictorSPX(9);

    // private final DigitalInput topSensor = new DigitalInput(0);
    private final DigitalInput bottomSensor = new DigitalInput(1);

    @Override
    public void set(double top, double bottom) {
        //topMotor.set(ControlMode.PercentOutput, top);
        bottomMotor.set(ControlMode.PercentOutput, bottom);
    }

    @Override
    public IndexerSensors get() {
        return new IndexerSensors(true, bottomSensor.get());
    }
}
