package com.maxtech.maxx;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.XboxController;

import static com.maxtech.maxx.RobotContainer.decide;

/**
 * A convenient place to keep constant values, that will certainly never change throughout robot execution.
 */
public final class Constants {
    public static int left1ID = 11;
    public static int left2ID = 12;
    public static int right1ID = 13;
    public static int right2ID = 14;

    public static int a1 = 4;
    public static int a2 = 3;

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
         *
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
        public static final double rampRate = 0.5; // Seconds to reach full output drive motors
    }

    public static class Flywheel {
        public static final int id = 5;
        public static final double P = 0.2;
        public static final double I = 0;
        public static final double D = 0;
        public static final double F = 0;
        public static final int pidID = 0;
        public static final int TimeoutMs = 30;
        public static final boolean SensorPhase = false;
        public static final boolean MotorInvert = false;
        public static final int topBinRPM = 15000;
        public static final int bottomBinRPM = 8000;
        public static final double rpmThreshold = 0.7;
        public static final int talonFXResolution = 2048;
    }

    public static class Indexer {
        public static final double maxOutput = 0.5;
        public static final int bottomID = 8;
        public static final int topID = 9;
        public static final int topSensorDIO = 0;
        public static final int bottomSensorDIO = 1;
    }

    public static class Intake {
        public static final int pivotID = 6;
        public static final int wheelsID = 7;
        public static final int upPos = 0;
        public static final int downPos = decide(-1100, -1200);
        public static final double P = 0.5;
        public static final double I = 0;
        public static final double D = 0;
        public static final double F = 0;
        public static final int pidID = 0;
        public static final int TimeoutMs = 30;
        public static final boolean SensorPhase = false;
        public static final boolean MotorInvert = false;
        public static final double wheelsInPercentOut = -0.6;
    }

    public static class Buttons {
        public static final int ToggleDriveDirection = XboxController.Button.kRightBumper.value;
        public static final int Intake = XboxController.Button.kX.value;
        public static final int ShootHigh = XboxController.Button.kA.value;
        public static final int ShootLow = XboxController.Button.kB.value;
        public static final int Climb = XboxController.Button.kY.value;
        public static final int DumpPOV = 90;
        public static final int ExtendClimbPOV = 0;
        public static final int ReleaseClimbPOV = -1;
        public static final int HangClimbPOV = 180;


        // POV/DPAD is by -1, 0 - 360
    }

    public static class Climber {
        public static boolean pinned = true;
        public static final int leftWinchID = 19;
        public static final int rightWinchID = 17;
        public static final int leftPivotID = 21; // TODO FILL IN IDs
        public static final int rightPivotID = 22;

        // WINCH
        public static final double down_P = 0.2;
        public static final double down_I = 0.0;
        public static final double down_Iz = 0;
        public static final double down_D = 0;
        public static final double down_F = 0;
        public static final double up_P = 0.4;
        public static final double up_I = 0;
        public static final double up_Iz = 0;
        public static final double up_D = 0;
        public static final double up_F = 0;
        public static final int upPidID = 0;
        public static final int downPidID = 1;
        public static final double maxOutputUp = 0.8;
        public static final double minOutputUp = -0.8;
        public static final double maxOutputDown = 0.8;
        public static final double minOutputDown = -0.8;
        public static final float winchForwardSoftLimit = 70;
        public static final float winchReverseSoftLimit = 0;

        // PIVOT
        public static final int pivotpidID = 0;
        public static final int pivotTimeoutMs = 0;
        public static final boolean pivotSensorPhase = false;
        public static final boolean pivotMotorInvert = false;
        public static final double pivotP = 0;// TODO tune
        public static final double pivotI = 0;// TODO tune
        public static final double pivotD = 0;// TODO tune
        public static final double pivotF = 0;// TODO tune
        public static final double maxPivotOutputForward = 0;// TODO tune
        public static final double maxPivotOutputReverse = 0;// TODO tune

        public static final double winchUpPos = 70;
        public static final double winchUpThreshold = 0; // TODO tune
        public static final double winchDownPos = 1;
        public static final double winchDownThreshold = 0; // TODO tune
        public static final double winchHangingPos = 25; // TODO tune
        public static final double winchHangingThreshold = 0; // TODO tune
        public static final double winchReleasePos = 0.99;
        public static final int pivotHangingPos = 0;// TODO tune
        public static final int pivotHandoffPos = 0;// TODO tune
        public static final int pivotHandoffThreshold = 0;// TODO tune
        public static final int pivotClearBarPos = 0;// TODO tune
        public static final int pivotClearBarThreshold = 0;// TODO tune
        public static final int pivotContactHighBarPos = 0;// TODO tune
        public static final int pivotContactHighBarThreshold = 0;// TODO tune
        public static final int pivotHangingThreshold = 0;// TODO tune
    }
}
