package com.maxtech.maxx.subsystems.flywheel;

import com.maxtech.lib.annotations.Tested;
import com.maxtech.lib.logging.RobotLogger;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

@Tested
public class FlywheelIOSim implements FlywheelIO {
    private RobotLogger logger = RobotLogger.getInstance();

    private FlywheelSim simulator = new FlywheelSim(DCMotor.getFalcon500(2), 1, 0.005);

    @Override
    public void setVoltage(double voltage) {
        logger.log("Trying to set voltage to %s.", voltage);
        simulator.setInputVoltage(12);
    }

    @Override
    public double getVelocity() {
        return simulator.getAngularVelocityRPM();
    }
}
