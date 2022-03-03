package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.annotations.Tested;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

@Tested
public class FlywheelIOSim implements FlywheelIO {
    private FlywheelSim simulator = new FlywheelSim(DCMotor.getFalcon500(1), 2048, 0.05);

    @Override
    public void setVelocity(double voltage) {
        simulator.setInputVoltage(voltage);
        simulator.update(0.2);
    }

    @Override
    public double getVelocity() {
        return simulator.getAngularVelocityRPM();
    }

    @Override
    public double getVoltage() {
        return 0;
    }
}
