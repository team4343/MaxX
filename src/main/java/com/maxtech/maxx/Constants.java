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
    // TODO: Refactor these constants into @{link Drive}.
    public static final int left1ID = 2;
    public static final int left2ID = 3;
    public static final int right1ID = 4;
    public static final int right2ID = 5;
    public static final int Shooter_Motor1ID = 6;

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

        public static final DCMotor gearbox = DCMotor.getNeo550(4);
        public static final double gearing = 10.71 / 1;

        public static final double kpDriveVelocity = 38.96;

        public static final double maxSpeedMetersPerSecond = 3;
        public static final double maxAccelerationMetersPerSecondSquared = 3;
        public static final double maxVoltage = 12;

        // Reasonable baseline values for a RAMSETE follower in units of meters and seconds
        public static final double ramseteB = 2;
        public static final double ramseteZeta = 0.7;
    }
}