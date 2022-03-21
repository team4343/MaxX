package com.maxtech.maxx.subsystems.flywheel;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.maxtech.lib.wrappers.ctre.PhoenixMotorControllerGroup;
import com.maxtech.maxx.Constants;

public class FlywheelIOMax implements FlywheelIO {
    private final TalonFX master = new TalonFX(Constants.Flywheel.masterID);
    private final TalonFX slave = new TalonFX(Constants.Flywheel.slaveID);

    public FlywheelIOMax() {
        //master.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor,
        //        Constants.Flywheel.pidID,
        //        Constants.Flywheel.TimeoutMs);
        master.setSensorPhase(Constants.Flywheel.SensorPhase);
        master.setInverted(Constants.Flywheel.MotorInvert);

        /* Config Position Closed Loop gains in slot0, typically kF stays zero. */
        //master.config_kF(Constants.Flywheel.pidID, Constants.Flywheel.F, Constants.Flywheel.TimeoutMs);
        //master.config_kP(Constants.Flywheel.pidID, Constants.Flywheel.P, Constants.Flywheel.TimeoutMs);
        //master.config_kI(Constants.Flywheel.pidID, Constants.Flywheel.I, Constants.Flywheel.TimeoutMs);
        //master.config_kD(Constants.Flywheel.pidID, Constants.Flywheel.D, Constants.Flywheel.TimeoutMs);

        master.set(ControlMode.PercentOutput, 0);
        master.set(ControlMode.Velocity, 0);

        slave.follow(master);
        slave.setInverted(InvertType.OpposeMaster);
    }

    @Override
    public void setVoltage(double voltage) {
        master.set(ControlMode.PercentOutput, voltage / 12);
    }

    @Override
    public void setVelocity(double velocity) {
        if (velocity == 0) {
            master.set(ControlMode.PercentOutput, 0);
        }

        master.set(TalonFXControlMode.Velocity, velocity * Constants.Flywheel.talonFXResolution / (60 * 10));
    }

    @Override
    public double getVelocity() {
        return ((master.getSelectedSensorVelocity(Constants.Flywheel.pidID) / Constants.Flywheel.talonFXResolution) * (60 * 10)) ;
    }

    @Override
    public double getVoltage() {
        return master.getMotorOutputVoltage();
    }
}
