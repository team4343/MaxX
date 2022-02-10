package com.maxtech.maxx.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    private final CANSparkMax Shooter_Motor1 = new CANSparkMax(Constants.Shooter_Motor1ID, CANSparkMaxLowLevel.MotorType.kBrushless);

    public void start() {
        Shooter_Motor1.set(1);
    }
    public void stop() {
        Shooter_Motor1.set(0);
    }

    public void Motor_Speed(double s) {
        Shooter_Motor1.set(s);
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Shooter Motor Voltage", Shooter_Motor1.getBusVoltage());

        SmartDashboard.putNumber("Shooter Motor Temperature", Shooter_Motor1.getMotorTemperature());
    }
}
