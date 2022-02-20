package com.maxtech.lib.wrappers.rev;

import com.maxtech.lib.logging.RobotLogger;

public class CANSparkMax extends com.revrobotics.CANSparkMax {
    /**
     * Create a new object to control a SPARK MAX motor Controller
     *
     * @param deviceId The device ID.
     * @param type     The motor type connected to the controller. Brushless motor wires must be connected
     *                 to their matching colors and the hall sensor must be plugged in. Brushed motors must be
     */
    public CANSparkMax(int deviceId, MotorType type) {
        super(deviceId, type);
    }

    @Override
    public void set(double speed) {
        setVoltage(speed / 100);
    }
}
