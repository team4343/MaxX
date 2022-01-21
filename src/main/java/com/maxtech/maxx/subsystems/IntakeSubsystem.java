package com.maxtech.maxx.subsystems;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {

    CANSparkMax leftIntakeMotor = new CANSparkMax(Constants.leftIntakeMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
    CANSparkMax rightIntakeMotor = new CANSparkMax(Constants.rightIntakeMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup intake = new MotorControllerGroup(leftIntakeMotor, rightIntakeMotor);

    public IntakeSubsystem() {

    }

    @Override
    public void periodic() {
       intake.set(0);
    }

    @Override
    public void simulationPeriodic() {
        // This method will be called once per scheduler run during simulation
        intake.set(0);
    }
}
