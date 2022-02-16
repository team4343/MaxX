package com.maxtech.maxx.subsystems;

import com.ctre.phoenix.motorcontrol.VictorSPXControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A drivetrain subsystem for Max X.
 */
public class Indexer extends SubsystemBase {
    private final VictorSPX motor1 = new VictorSPX(20);
    private final VictorSPX motor2 = new VictorSPX(21);

    private final DigitalInput breamBreak1 = new DigitalInput(Constants.Beam1ID);
    private final DigitalInput breamBreak2 = new DigitalInput(Constants.Beam2ID);

    public Indexer() {
        // Set up the second motor to follow the first.
        motor2.follow(motor1);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Motor1/Indexer Voltage", motor1.getBusVoltage());
        SmartDashboard.putNumber("Motor2/Indexer Voltage", motor2.getBusVoltage());

        SmartDashboard.putNumber("Left1/Drive Temperature", motor1.getTemperature());
        SmartDashboard.putNumber("Left2/Drive Temperature", motor2.getTemperature());
    }

    public void start() {
        motor1.set(VictorSPXControlMode.Velocity, 1);
    }

    public void stop() {
        motor1.set(VictorSPXControlMode.Velocity, 0);
    }
}