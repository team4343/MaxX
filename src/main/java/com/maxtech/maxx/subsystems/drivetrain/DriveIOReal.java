package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.RelativeEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveIOReal implements DriveIO {
    private CANSparkMax left1 = new CANSparkMax(Constants.left1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax left2 = new CANSparkMax(Constants.left2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    private CANSparkMax right1 = new CANSparkMax(Constants.right1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private CANSparkMax right2 = new CANSparkMax(Constants.right2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    private DifferentialDrive drivetrain = new DifferentialDrive(left, right);

    public DriveIOReal() {
        right.setInverted(true);
    }

    public void updateInputs(DriveIOInputs inputs) {
        inputs.left1AppliedVolts = left1.getBusVoltage();
        inputs.left2AppliedVolts = left2.getBusVoltage();
        inputs.right1AppliedVolts = right1.getBusVoltage();
        inputs.right2AppliedVolts = right2.getBusVoltage();
    }

    public void drive(double ls, double rs) {
        drivetrain.tankDrive(ls, rs);
    }
}