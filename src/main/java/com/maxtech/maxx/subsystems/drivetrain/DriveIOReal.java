package com.maxtech.maxx.subsystems.drivetrain;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;

public class DriveIOReal implements DriveIO {
    CANSparkMax left1 = new CANSparkMax(Constants.left1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax left2 = new CANSparkMax(Constants.left2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    CANSparkMax right1 = new CANSparkMax(Constants.right1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax right2 = new CANSparkMax(Constants.right2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    DifferentialDrive drivetrain = new DifferentialDrive(left, right);

    public DriveIOReal() {
        right.setInverted(true);
    }

    public void updateInputs(DriveIOInputs inputs) {
        inputs.leftAppliedVolts = 1;
        inputs.rightAppliedVolts = 1;
    }

    public void drive(double ls, double rs) {
        drivetrain.tankDrive(ls, rs);
    }
}