package com.maxtech.maxx.subsystems;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class IntakeSubsystem extends SubsystemBase {


    MotorController left1 = new CANSparkMax(Constants.leftIntakeMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorController right1 = new CANSparkMax(Constants.rightIntakeMotorID, CANSparkMaxLowLevel.MotorType.kBrushless);
    MotorControllerGroup intake = new MotorControllerGroup(left1, right1);

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
