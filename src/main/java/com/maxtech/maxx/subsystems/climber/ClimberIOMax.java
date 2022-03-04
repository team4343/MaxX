package com.maxtech.maxx.subsystems.climber;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

public class ClimberIOMax implements ClimberIO{
    private CANSparkMax winchR = new CANSparkMax(Constants.Climber.rightID, MotorType.kBrushless);
    private CANSparkMax winchL = new CANSparkMax(Constants.Climber.leftID,  MotorType.kBrushless);
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;

    public ClimberIOMax() {
        winchL.restoreFactoryDefaults();
        winchR.restoreFactoryDefaults();

        winchL.setInverted(true);
        winchR.setInverted(false);
        winchR.setIdleMode(CANSparkMax.IdleMode.kBrake);
        winchL.setIdleMode(CANSparkMax.IdleMode.kBrake);

        winchR.follow(CANSparkMax.ExternalFollower.kFollowerDisabled, 0);
        winchL.follow(winchR, true);

        pidController = winchR.getPIDController();
        encoder = winchR.getEncoder();

        winchL.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        winchL.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);
        winchR.enableSoftLimit(CANSparkMax.SoftLimitDirection.kForward, true);
        winchR.enableSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, true);

        winchL.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 5);
        winchR.setSoftLimit(CANSparkMax.SoftLimitDirection.kForward, 5);
        winchL.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -5);
        winchR.setSoftLimit(CANSparkMax.SoftLimitDirection.kReverse, -5);

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

    }

    @Override
    public void setPos(double pos) {
        if (pos < 0 || pos > Constants.Climber.upPos * 1.05)
            return;

        if (pos < getPos())
            pidController.setReference(pos, CANSparkMax.ControlType.kPosition, Constants.Climber.downPidID);
        else
            pidController.setReference(pos, CANSparkMax.ControlType.kPosition, Constants.Climber.upPidID);
    }

    @Override
    public double getPos() {
        return encoder.getPosition();
    }

    @Override
    public void halt() {
        pidController.setReference(getPos(), CANSparkMax.ControlType.kPosition);
    }


}
