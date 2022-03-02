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
    public static int left1ID = 1;
    public static int left2ID = 2;
    public static int right1ID = 3;
    public static int right2ID = 4;

    public static int a1 = 4;
    public static int a2 = 3;

    public static final int Beam1ID = 15;
    public static final int Beam2ID = 16;

    public static final int pivotID = 14;
    public static final int intakeID = 15;

    public static final class Drive {
        /**
         * Width in between the left and right wheels, in meters.
         */
        public static final double trackWidth = 0.53;
        public static final DifferentialDriveKinematics kinematics = new DifferentialDriveKinematics(trackWidth);

        /**
         * Encoder counts-per-revolution. Our count is 1, because the SparkMaxes account for the Neo's rotation.
         */
        public static final double encoderCPR = 1;

        /**
         * Diameter of one wheel, in centimeters.
         */
        public static final double wheelDiameter = 16;

        /**
         * Encoder distance per pulse.
         */
        public static final double encoderDistancePerPulse = (wheelDiameter * Math.PI) / encoderCPR;

        public static final double ksVolts = 1.6985;
        public static final double kvVolts = 30.64;
        public static final double kaVolts = 221.77;

        public static final double kvVoltSecondsPerRadian = 38.96;
        public static final double kaVoltSecondsSquaredPerRadian = 38.96;

        public static final LinearSystem<N2, N2, N2> plant = LinearSystemId.identifyDrivetrainSystem(kvVolts, kaVolts, kvVoltSecondsPerRadian, kaVoltSecondsSquaredPerRadian);

        public static final DCMotor gearbox = DCMotor.getNEO(4);
        public static final double gearing = 12.98;

        public static final double kpDriveVelocity = 38.96;

        public static final double maxSpeedMetersPerSecond = 0.25;
        public static final double maxAccelerationMetersPerSecondSquared = 0.1;
        public static final double maxVoltage = 9;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double ramseteB = 2;
        public static final double ramseteZeta = 0.7;
    }

    public static class Flywheel {
        public static final int id = 5;
        public static final int maxVoltage = 11;

        public static final double kV = 0.98229;
        public static final double kA = 0.047735;
    }

    public static class Indexer{
        public static final double topPercentOut = 1.0;
        public static final double bottomPercentOut = 1.0;
    }

    public static class Intake {
        public static final int upPos = 0;
        public static final int downPos = 0;
        public static final double P = 0.005;
        public static final double I = 0;
        public static final double D = 0;
        public static final double F = 0;
        public static final int pidID = 0;
        public static final int TimeoutMs = 30;
        public static final boolean SensorPhase = true;
        public static final boolean MotorInvert = false;
        public static final double wheelsIn = 1;
    }
}
