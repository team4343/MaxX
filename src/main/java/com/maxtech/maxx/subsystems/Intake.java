package com.maxtech.maxx.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.maxx.Constants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    private final VictorSPX intakemotor = new VictorSPX(Constants.intakeID);
    private final VictorSPX pivot_motor = new VictorSPX(Constants.pivotID);

    @Override
    public void periodic() {
        SmartDashboard.putNumber("pivot motor voltage", pivot_motor.getBusVoltage());
        SmartDashboard.putNumber("intake motor voltage", intakemotor.getBusVoltage());
        SmartDashboard.putNumber("pivot motor temp", pivot_motor.getTemperature());
        SmartDashboard.putNumber("intake motor temp", intakemotor.getTemperature());
    }

    public void start() {
        pivot_motor.set(ControlMode.Velocity, 1);
        intakemotor.set(ControlMode.Velocity, 1);
    }

    public void stop() {
        pivot_motor.set(ControlMode.Velocity, 0);
        intakemotor.set(ControlMode.Velocity, 0);
    }
}