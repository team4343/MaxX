package com.maxtech.maxx.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.maxx.Constants;

public class IntakeIOMax implements IntakeIO {
    private final TalonSRX pivotMotor = new TalonSRX(Constants.Intake.pivotID);
    private final VictorSPX wheels = new VictorSPX(Constants.Intake.wheelsID);
    public int absolutePosition = 0;
    private static double lastPos = 0;

    public IntakeIOMax() {
        pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                Constants.Intake.pidID,
                Constants.Intake.TimeoutMs);
        pivotMotor.setSensorPhase(Constants.Intake.SensorPhase);
        pivotMotor.setInverted(Constants.Intake.MotorInvert);

        /* Config Position Closed Loop gains in slot0, typically kF stays zero. */
        pivotMotor.config_kF(Constants.Intake.pidID, Constants.Intake.F, Constants.Intake.TimeoutMs);
        pivotMotor.config_kP(Constants.Intake.pidID, Constants.Intake.P, Constants.Intake.TimeoutMs);
        pivotMotor.config_kI(Constants.Intake.pidID, Constants.Intake.I, Constants.Intake.TimeoutMs);
        pivotMotor.config_kD(Constants.Intake.pidID, Constants.Intake.D, Constants.Intake.TimeoutMs);

        absolutePosition = pivotMotor.getSensorCollection().getPulseWidthPosition();

        pivotMotor.set(ControlMode.PercentOutput, 0);
        pivotMotor.set(ControlMode.Position, 0);
    }

    @Override
    public void setPos(double value) {
        //System.out.println(pivotMotor.getSensorCollection().getPulseWidthPosition());
        if (lastPos == value)
            return;
        else
            lastPos=value;
        pivotMotor.set(TalonSRXControlMode.Position, value);
    }

    @Override
    public void setWheels(double value) {
        wheels.set(ControlMode.PercentOutput, value);
    }
}

