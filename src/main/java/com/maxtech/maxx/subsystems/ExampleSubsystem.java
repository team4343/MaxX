package com.maxtech.maxx.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
    public ExampleSubsystem() {
    }

    @Override
    public void periodic() {
        System.out.println("Hello, world!");
    }

    @Override
    public void simulationPeriodic() {
    }
}
