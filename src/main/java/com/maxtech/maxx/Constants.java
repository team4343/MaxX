package com.maxtech.maxx;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;

/**
 * A convenient place to keep constant values, that will certainly never change throughout robot execution.
 */
public final class Constants {
    public static final int left1ID = 2;
    public static final int left2ID = 3;
    public static final int right1ID = 4;
    public static final int right2ID = 5;

    public static final class Simulation {
        public static final double trackWidthMeters = 0.69;
        public static final DifferentialDriveKinematics driveKinematics =
                new DifferentialDriveKinematics(trackWidthMeters);

        public static final int encoderCPR = 1024;
        public static final double wheelDiameterMeters = 0.15;
        public static final double encoderDistancePerPulse =
                // Assumes the encoders are directly mounted on the wheel shafts
                (wheelDiameterMeters * Math.PI) / (double) encoderCPR;

        public static final double sVolts = 0.22;
        public static final double vVoltSecondsPerMeter = 1.98;
        public static final double aVoltSecondsSquaredPerMeter = 0.2;

        public static final double vVoltSecondsPerRadian = 1.5;
        public static final double aVoltSecondsSquaredPerRadian = 0.3;

        public static final LinearSystem<N2, N2, N2> drivetrainPlant =
                LinearSystemId.identifyDrivetrainSystem(
                        vVoltSecondsPerMeter,
                        aVoltSecondsSquaredPerMeter,
                        vVoltSecondsPerRadian,
                        aVoltSecondsSquaredPerRadian);

        public static final DCMotor driveGearbox = DCMotor.getCIM(2);
        public static final double driveGearing = 8;

        public static final double pDriveVel = 8.5;
    }
}