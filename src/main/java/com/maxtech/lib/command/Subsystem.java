package com.maxtech.lib.command;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public abstract class Subsystem extends SubsystemBase {
    public abstract void sendTelemetry(String prefix);
}
