package com.maxtech.maxx.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.maxtech.maxx.Constants;

public class IntakeIOMax implements IntakeIO {
    private final TalonSRX pivotMotor = new TalonSRX(6);
    private final VictorSPX wheels = new VictorSPX(7);
    public int absolutePosition = 0;

    public IntakeIOMax() {
        pivotMotor.configFactoryDefault();
        pivotMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.Intake.pidID,
                Constants.Intake.TimeoutMs);
        pivotMotor.setSensorPhase(Constants.Intake.SensorPhase);
        pivotMotor.setInverted(Constants.Intake.MotorInvert);

        /* Config the peak and nominal outputs, 12V means full */
        pivotMotor.configNominalOutputForward(0, Constants.Intake.TimeoutMs);
        pivotMotor.configNominalOutputReverse(0, Constants.Intake.TimeoutMs);
        pivotMotor.configPeakOutputForward(1, Constants.Intake.TimeoutMs);
        pivotMotor.configPeakOutputReverse(-1, Constants.Intake.TimeoutMs);


        pivotMotor.configAllowableClosedloopError(0, Constants.Intake.pidID, Constants.Intake.TimeoutMs);

        /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
        pivotMotor.config_kF(Constants.Intake.pidID, Constants.Intake.F, Constants.Intake.TimeoutMs);
        pivotMotor.config_kP(Constants.Intake.pidID, Constants.Intake.P, Constants.Intake.TimeoutMs);
        pivotMotor.config_kI(Constants.Intake.pidID, Constants.Intake.I, Constants.Intake.TimeoutMs);
        pivotMotor.config_kD(Constants.Intake.pidID, Constants.Intake.D, Constants.Intake.TimeoutMs);

        absolutePosition = pivotMotor.getSensorCollection().getPulseWidthPosition();
    }

    @Override
    public void setPos(double value) {
        pivotMotor.set(ControlMode.Position, value);
    }

    @Override
    public void setWheels(double value) {
        wheels.set(ControlMode.PercentOutput, value);
    }
}
