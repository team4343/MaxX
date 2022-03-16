package com.maxtech.maxx.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.TalonSRXConfiguration;
import com.kauailabs.navx.frc.AHRS;
import com.maxtech.maxx.Constants;
import static com.maxtech.maxx.Constants.Climber.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import static java.lang.Math.round;

public class ClimberIOMax implements ClimberIO{
    private CANSparkMax winchR = new CANSparkMax(rightWinchID, MotorType.kBrushless);
    private CANSparkMax winchL = new CANSparkMax(leftWinchID,  MotorType.kBrushless);
    private TalonSRX pivotR = new TalonSRX(rightPivotID);
    private TalonSRX pivotL = new TalonSRX(leftPivotID);
    private double absolutePivotPositionL = 0;
    private double absolutePivotPositionR = 0;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    private final AHRS gyro = new AHRS();

    public ClimberIOMax() {
        // Factory defaults
        winchL.restoreFactoryDefaults();
        winchR.restoreFactoryDefaults();
        pivotL.configFactoryDefault();
        pivotR.configFactoryDefault();

        /* ****** WINCH ******* */
        // Basic Setup
        winchL.setInverted(true);
        winchR.setInverted(true);
        winchR.setIdleMode(CANSparkMax.IdleMode.kBrake);
        winchL.setIdleMode(CANSparkMax.IdleMode.kBrake);

        // Control
        winchR.follow(winchL, true);
        pidController = winchL.getPIDController();
        encoder = winchL.getEncoder();

        // Soft limits
        winchL.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        winchL.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        winchR.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        winchR.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        winchL.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, winchForwardSoftLimit);
        winchR.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, winchForwardSoftLimit);
        winchL.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, winchReverseSoftLimit);
        winchR.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, winchReverseSoftLimit);

        // Extend PID
        pidController.setP(up_P , upPidID);
        pidController.setI(up_I , upPidID);
        pidController.setIZone(up_Iz, upPidID);
        pidController.setD(up_D , upPidID);
        pidController.setFF(up_F , upPidID);

        // Climb/Retract PID
        pidController.setP(down_P , downPidID);
        pidController.setI(down_I ,  downPidID);
        pidController.setIZone(down_Iz, downPidID);
        pidController.setD(down_D ,  downPidID);
        pidController.setFF(down_F ,  downPidID);

        // Soft Limits
        pidController.setOutputRange(minOutputUp, maxOutputUp, upPidID);
        pidController.setOutputRange(minOutputDown, maxOutputDown, downPidID);

        /* ****** PIVOT ******* */
        // Soft limits
        pivotL.setNeutralMode(NeutralMode.Brake);
        pivotR.setNeutralMode(NeutralMode.Brake);
        pivotR.configNominalOutputForward(maxPivotOutputForward);
        pivotR.configNominalOutputReverse(maxPivotOutputReverse);
        pivotL.configNominalOutputForward(maxPivotOutputForward);
        pivotL.configNominalOutputReverse(maxPivotOutputReverse);
        pivotR.configForwardSoftLimitEnable(true);
        pivotR.configReverseSoftLimitEnable(true);
        pivotR.configForwardSoftLimitThreshold(maxPivotPosForward);
        pivotR.configReverseSoftLimitThreshold(maxPivotPosReverse);
        pivotL.configForwardSoftLimitEnable(true);
        pivotL.configReverseSoftLimitEnable(true);
        pivotL.configForwardSoftLimitThreshold(maxPivotPosForward);
        pivotL.configReverseSoftLimitThreshold(maxPivotPosReverse);

        // Sensor Config
        pivotR.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                pivotpidID,
                pivotTimeoutMs);
        pivotR.setSensorPhase(pivotSensorPhase);
        pivotR.setInverted(pivotMotorInvert);
        pivotL.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                pivotpidID,
                pivotTimeoutMs);
        pivotL.setSensorPhase(pivotSensorPhase);
        pivotL.setInverted(pivotMotorInvert);

        // PID
        pivotR.config_kF(pivotpidID, pivotF, pivotTimeoutMs);
        pivotR.config_kP(pivotpidID, pivotP, pivotTimeoutMs);
        pivotR.config_kI(pivotpidID, pivotI, pivotTimeoutMs);
        pivotR.config_kD(pivotpidID, pivotD, pivotTimeoutMs);
        pivotL.config_kF(pivotpidID, pivotF, pivotTimeoutMs);
        pivotL.config_kP(pivotpidID, pivotP, pivotTimeoutMs);
        pivotL.config_kI(pivotpidID, pivotI, pivotTimeoutMs);
        pivotL.config_kD(pivotpidID, pivotD, pivotTimeoutMs);

        // Cleanup
        absolutePivotPositionR = pivotR.getSensorCollection().getPulseWidthPosition();
        absolutePivotPositionL = pivotL.getSensorCollection().getPulseWidthPosition();

        pivotR.set(ControlMode.PercentOutput, 0);
        pivotR.set(ControlMode.Position, 0);
        pivotL.set(ControlMode.PercentOutput, 0);
        pivotL.set(ControlMode.Position, 0);

        // Left Follows Right and should be mirrored (inverted)
        pivotL.setInverted(true);
        pivotL.follow(pivotR);

        gyro.reset();
    }

    @Override
    public void setPivotPos(double pos) {
        // TODO Still unsure if each side should be independent, probably not.
        // pivotL.set(TalonSRXControlMode.Position, pos);
        pivotR.set(TalonSRXControlMode.Position, pos);
    }

    @Override
    public void setWinchPos(double pos) {
        if (pos < winchDownPos - 5 || pos > winchUpPos + 10)
            return;

        if (pos < getWinchPos())
            pidController.setReference(pos, CANSparkMax.ControlType.kPosition, downPidID);
        else
            pidController.setReference(pos, CANSparkMax.ControlType.kPosition, upPidID);
    }

    @Override
    public double getWinchPos() {
        return encoder.getPosition();
    }

    @Override
    public double getPivotPos() {
        return pivotR.getSelectedSensorPosition();
    }

    @Override
    public void halt() {
        pidController.setReference(Math.round(getWinchPos()), CANSparkMax.ControlType.kPosition);

    }

}
