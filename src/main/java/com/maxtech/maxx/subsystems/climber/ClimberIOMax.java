package com.maxtech.maxx.subsystems.climber;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

import static java.lang.Math.round;

public class ClimberIOMax implements ClimberIO{
    private CANSparkMax winchR = new CANSparkMax(Constants.Climber.rightWinchID, MotorType.kBrushless);
    private CANSparkMax winchL = new CANSparkMax(Constants.Climber.leftWinchID,  MotorType.kBrushless);
    private TalonSRX pivotR = new TalonSRX(Constants.Climber.rightPivotID);
    private TalonSRX pivotL = new TalonSRX(Constants.Climber.leftPivotID);
    private double absolutePosition = 0;
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    public ClimberIOMax() {
        // Factory defaults
        winchL.restoreFactoryDefaults();
        winchR.restoreFactoryDefaults();
        pivotL.configFactoryDefault();
        pivotR.configFactoryDefault();

        // WINCH
        winchL.setInverted(true);
        winchR.setInverted(true);
        winchR.setIdleMode(CANSparkMax.IdleMode.kBrake);
        winchL.setIdleMode(CANSparkMax.IdleMode.kBrake);

        winchR.follow(winchL, true);

        pidController = winchL.getPIDController();
        encoder = winchL.getEncoder();

        winchL.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        winchL.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        winchR.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        winchR.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

        winchL.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.Climber.winchForwardSoftLimit);
        winchR.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, Constants.Climber.winchForwardSoftLimit);
        winchL.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, Constants.Climber.winchReverseSoftLimit);
        winchR.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, Constants.Climber.winchReverseSoftLimit);

        pidController.setP(Constants.Climber.up_P , Constants.Climber.upPidID);
        pidController.setI(Constants.Climber.up_I , Constants.Climber.upPidID);
        pidController.setIZone(Constants.Climber.up_Iz, Constants.Climber.upPidID);
        pidController.setD(Constants.Climber.up_D , Constants.Climber.upPidID);
        pidController.setFF(Constants.Climber.up_F , Constants.Climber.upPidID);

        pidController.setP(Constants.Climber.down_P , Constants.Climber.downPidID);
        pidController.setI(Constants.Climber.down_I ,  Constants.Climber.downPidID);
        pidController.setIZone(Constants.Climber.down_Iz, Constants.Climber.downPidID);
        pidController.setD(Constants.Climber.down_D ,  Constants.Climber.downPidID);
        pidController.setFF(Constants.Climber.down_F ,  Constants.Climber.downPidID);

        pidController.setOutputRange(Constants.Climber.minOutputUp, Constants.Climber.maxOutputUp, Constants.Climber.upPidID);
        pidController.setOutputRange(Constants.Climber.minOutputDown, Constants.Climber.maxOutputDown, Constants.Climber.downPidID);

        // PIVOT
        pivotR.configNominalOutputForward(Constants.Climber.maxPivotOutputForward);
        pivotR.configNominalOutputReverse(Constants.Climber.maxPivotOutputReverse);
        pivotL.configNominalOutputForward(Constants.Climber.maxPivotOutputForward);
        pivotL.configNominalOutputReverse(Constants.Climber.maxPivotOutputReverse);

        pivotR.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder,
                Constants.Climber.pivotpidID,
                Constants.Climber.pivotTimeoutMs);
        pivotR.setSensorPhase(Constants.Climber.pivotSensorPhase);
        pivotR.setInverted(Constants.Climber.pivotMotorInvert);

        pivotR.config_kF(Constants.Climber.pivotpidID, Constants.Climber.pivotF, Constants.Climber.pivotTimeoutMs);
        pivotR.config_kP(Constants.Climber.pivotpidID, Constants.Climber.pivotP, Constants.Climber.pivotTimeoutMs);
        pivotR.config_kI(Constants.Climber.pivotpidID, Constants.Climber.pivotI, Constants.Climber.pivotTimeoutMs);
        pivotR.config_kD(Constants.Climber.pivotpidID, Constants.Climber.pivotD, Constants.Climber.pivotTimeoutMs);

        absolutePosition = pivotR.getSensorCollection().getPulseWidthPosition();

        pivotR.set(ControlMode.PercentOutput, 0);
        pivotR.set(ControlMode.Position, 0);

        pivotL.follow(pivotR);

    }

    @Override
    public void setPivotPos(double pos) {

    }

    @Override
    public void setWinchPos(double pos) {
        if (pos < Constants.Climber.releasePos || pos > Constants.Climber.winchUpPos * 1.05)
            return;
        //System.out.println(winchL.getEncoder().getPosition());
        //System.out.println(winchR.getEncoder().getPosition());

        if (pos < getWinchPos())
            pidController.setReference(pos, CANSparkMax.ControlType.kPosition, Constants.Climber.downPidID);
        else
            pidController.setReference(pos, CANSparkMax.ControlType.kPosition, Constants.Climber.upPidID);
    }

    @Override
    public double getWinchPos() {
        return encoder.getPosition();
    }

    @Override
    public double getPivotPos() {
        return pivotL.getSelectedSensorPosition();
    }

    @Override
    public void halt() {
        pidController.setReference(Math.round(getWinchPos()), CANSparkMax.ControlType.kPosition);
    }




}
