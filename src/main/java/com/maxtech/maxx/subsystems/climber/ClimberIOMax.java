package com.maxtech.maxx.subsystems.climber;

import com.kauailabs.navx.frc.AHRS;
import static com.maxtech.maxx.Constants.Climber.*;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;

public class ClimberIOMax implements ClimberIO{
    public CANSparkMax winchR = new CANSparkMax(rightWinchID, MotorType.kBrushless);
    public CANSparkMax winchL = new CANSparkMax(leftWinchID,  MotorType.kBrushless);
    private SparkMaxPIDController pidController;
    private RelativeEncoder encoder;
    private final AHRS gyro = new AHRS();

    public ClimberIOMax() {
        // Factory defaults
        winchL.restoreFactoryDefaults();
        winchR.restoreFactoryDefaults();

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

        gyro.reset();
    }

    @Override
    public void setPivotPos(double pos) {
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
        return 0;
    }

    @Override
    public void halt() {
        pidController.setReference(Math.round(getWinchPos()), CANSparkMax.ControlType.kPosition);
    }
}
