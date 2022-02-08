package com.maxtech.maxx.subsystems;

import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

/**
 * A drivetrain subsystem for Max X.
 */
public class DriveSubsystem extends SubsystemBase {
    private final CANSparkMax left1 = new CANSparkMax(Constants.left1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax left2 = new CANSparkMax(Constants.left2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    private final CANSparkMax right1 = new CANSparkMax(Constants.right1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax right2 = new CANSparkMax(Constants.right2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    private final DifferentialDrive drivetrain = new DifferentialDrive(left, right);

    private final ADXRS450_Gyro gyro = new ADXRS450_Gyro();
    private final DifferentialDriveOdometry odometry;

    public DriveSubsystem() {
        // Invert the necessary motors.
        right.setInverted(true);

        // Create the odometry.
        odometry = new DifferentialDriveOdometry(gyro.getRotation2d());
    }

    @Override
    public void periodic() {
        // Update the odometry
        // TODO: we need to getDistance(), which it seems SparkMax encoders don't provide.
        odometry.update(gyro.getRotation2d(), 0, 0);

        SmartDashboard.putNumber("Left1/Drive Voltage", left1.getBusVoltage());
        SmartDashboard.putNumber("Left2/Drive Voltage", left2.getBusVoltage());
        SmartDashboard.putNumber("Right1/Drive Voltage", right1.getBusVoltage());
        SmartDashboard.putNumber("Right2/Drive Voltage", right2.getBusVoltage());

        SmartDashboard.putNumber("Left1/Drive Temperature", left1.getMotorTemperature());
        SmartDashboard.putNumber("Left2/Drive Temperature", left2.getMotorTemperature());
        SmartDashboard.putNumber("Right1/Drive Temperature", right1.getMotorTemperature());
        SmartDashboard.putNumber("Right2/Drive Temperature", right2.getMotorTemperature());
    }

    /**
     * Drive by arcade-style parameters.
     *
     * @param s speed to drive at
     * @param r rotation to drive at
     * @see DifferentialDrive
     */
    public void arcade(double s, double r) {
        drivetrain.arcadeDrive(s, r);
    }

    /**
     * Drive by tank-style parameters.
     *
     * @param ls left side speed
     * @param rs right side speed
     * @see DifferentialDrive
     */
    public void tank(double ls, double rs) {
        drivetrain.tankDrive(ls, rs);
    }

    /**
     * Resets the odometry to a given pose.
     *
     * @param pose the pose to which to set the odometry.
     * @see DifferentialDriveOdometry
     * */
    public void resetOdometry(Pose2d pose) {
        // TODO: we may need to reset encoders here.
        odometry.resetPosition(pose, gyro.getRotation2d());
    }

    /**
     * Get the current pose, according to odometry.
     *
     * @return the pose
     * */
    public Pose2d getPose() {
        return odometry.getPoseMeters();
    }

    /**
     * Get the total wheel speeds.
     *
     * @return the wheel speeds
     * @see DifferentialDriveWheelSpeeds
     * */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        return new DifferentialDriveWheelSpeeds(left1.getEncoder().getVelocity(), right1.getEncoder().getVelocity());
    }
}
