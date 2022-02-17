package com.maxtech.lib.managers;

import com.maxtech.lib.command.Subsystem;
import edu.wpi.first.wpilibj.Notifier;

import java.util.ArrayList;
import java.util.List;

public class DashboardValuesManager {
    private static DashboardValuesManager instance;

    public static DashboardValuesManager getInstance() {
        if (instance == null) {
            instance = new DashboardValuesManager();
        }

        return instance;
    }

    private DashboardValuesManager() {

    }

    private List<Subsystem> subsystems = new ArrayList<>();
    private Notifier notifier = new Notifier(this::updateTelemetry);
    private double period;

    /** Start the periodic telemetry sending with a default period of 20 milliseconds. */
    public void start() {
        start(.20);
    }

    public void start(double period) {
        this.period = period;
        notifier.startPeriodic(period);
    }

    /** Add a subsystem to the subsystems list. */
    public void addSubsystem(Subsystem subsystem) {
        subsystems.add(subsystem);
    }

    /** Add multiple subsystems to the subsystems list. */
    public void addSubsystems(Subsystem... subsystems) {
        for (Subsystem s : subsystems) {
            addSubsystem(s);
        }
    }

    /** Add multiple subsystems to the subsystems list, as a List<>. */
    public void addSubsystems(List<Subsystem> subsystems) {
        for (Subsystem s : subsystems) {
            addSubsystem(s);
        }
    }

    /** Update the telemetry for each subsystem. */
    public void updateTelemetry() {
        subsystems.forEach((subsystem -> subsystem.sendTelemetry(subsystem.getName() + "/")));
    }
}
