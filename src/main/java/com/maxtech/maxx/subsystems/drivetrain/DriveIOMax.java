package com.maxtech.maxx.subsystems.drivetrain;

import com.kauailabs.navx.frc.AHRS;
import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveIOMax implements DriveIO {
    private final CANSparkMax left1 = new CANSparkMax(Constants.left1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax left2 = new CANSparkMax(Constants.left2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final MotorControllerGroup left = new MotorControllerGroup(left1, left2);

    private final CANSparkMax right1 = new CANSparkMax(Constants.right1ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final CANSparkMax right2 = new CANSparkMax(Constants.right2ID, CANSparkMaxLowLevel.MotorType.kBrushless);
    private final MotorControllerGroup right = new MotorControllerGroup(right1, right2);

    private final REVPhysicsSim drivetrainSimulator = REVPhysicsSim.getInstance();

    private final DifferentialDrive drivetrain;
    DifferentialDrivetrainSim drivetrainSim = new DifferentialDrivetrainSim (
            Constants.Drive.gearbox,
            Constants.Drive.gearing,
            7.5,
            60.0,
            Constants.Drive.wheelDiameter / 2,
            Constants.Drive.trackWidth,
            null);

    private final AHRS gyro = new AHRS(SPI.Port.kMXP);
    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyro.getRotation2d());
    private final Field2d field = new Field2d();

    public DriveIOMax() {
        // Defaults
        left1.restoreFactoryDefaults();
        left2.restoreFactoryDefaults();
        right1.restoreFactoryDefaults();
        right2.restoreFactoryDefaults();

        // Coast Mode
        left1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        left2.setIdleMode(CANSparkMax.IdleMode.kCoast);
        right1.setIdleMode(CANSparkMax.IdleMode.kCoast);
        right2.setIdleMode(CANSparkMax.IdleMode.kCoast);

        // Ramp rate
        left1.setOpenLoopRampRate(Constants.Drive.rampRate);
        left2.setOpenLoopRampRate(Constants.Drive.rampRate);
        right1.setOpenLoopRampRate(Constants.Drive.rampRate);
        right2.setOpenLoopRampRate(Constants.Drive.rampRate);

        left1.setInverted(true);
        left2.setInverted(true);
        right1.setInverted(false);
        right2.setInverted(false);

        left1.getEncoder().setPosition(0);
        left2.getEncoder().setPosition(0);
        right1.getEncoder().setPosition(0);
        right2.getEncoder().setPosition(0);

        gyro.reset();
        gyro.resetDisplacement();

        drivetrain = new DifferentialDrive(left, right);

        drivetrainSimulator.addSparkMax(left1, DCMotor.getNEO(1));
        drivetrainSimulator.addSparkMax(left2, DCMotor.getNEO(1));
        drivetrainSimulator.addSparkMax(right1, DCMotor.getNEO(1));
        drivetrainSimulator.addSparkMax(right2, DCMotor.getNEO(1));

        SmartDashboard.putData("Drivetrain field", field);
        var tab = Shuffleboard.getTab("Drive");
        tab.addNumber("rotation", gyro::getAngle);
        tab.addNumber("distance travelled left", () -> this.getDistanceTravelled(left1));
        tab.addNumber("distance travelled right", () -> this.getDistanceTravelled(right1));
        tab.addNumber("wheel speeds left", () -> getWheelSpeeds().leftMetersPerSecond);
        tab.addNumber("wheel speeds right", () -> getWheelSpeeds().rightMetersPerSecond);
        tab.addNumber("pose x", () -> getPose().getX());
        tab.addNumber("pose y", () -> getPose().getY());
        tab.addNumber("left1 voltage", left1::getAppliedOutput);
        tab.addNumber("left2 voltage", left2::getAppliedOutput);
        tab.addNumber("right1 voltage", right1::getAppliedOutput);
        tab.addNumber("right2 voltage", right2::getAppliedOutput);
    }

    @Override
    public void periodic() {
        odometry.update(gyro.getRotation2d(), getDistanceTravelled(left1), getDistanceTravelled(right1));
        field.setRobotPose(odometry.getPoseMeters());
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

    public void tankDriveVolts(double lv, double rv) {
        left.setVoltage(rv);
        right.setVoltage(lv);
        drivetrain.feed();
    }

    /**
     * Resets the odometry to a given pose.
     *
     * @param pose the pose to which to set the odometry.
     * @see DifferentialDriveOdometry
     * */
    public void resetOdometry(Pose2d pose) {
        gyro.reset();
        odometry.resetPosition(pose, gyro.getRotation2d());
        field.setRobotPose(pose);

        left1.getEncoder().setPosition(0);
        left2.getEncoder().setPosition(0);
        right1.getEncoder().setPosition(0);
        right2.getEncoder().setPosition(0);
    }

    @Override
    public void setStartingPosition(Pose2d pose) {
        resetOdometry(pose);
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
     * Get the current field.
     *
     * @return the field
     */
    @Override
    public Field2d getField() {
        return field;
    }

    @java.lang.Override
    public double getDistanceTravelled(com.maxtech.lib.wrappers.rev.CANSparkMax controller, double gearing, double wheelDiameter) {
        var motorRotations = controller.getEncoder().getPosition();
        var wheelRotations = motorRotations / gearing;
        double distanceTravelled = wheelRotations * Math.PI * wheelDiameter;

        return distanceTravelled;
    }

    /** Get the distance travelled of one Spark Max motor controller. */
    public double getDistanceTravelled(CANSparkMax controller, double gearing, double wheelDiameter) {
        var motorRotations = controller.getEncoder().getPosition();
        var wheelRotations = motorRotations / gearing;
        double distanceTravelled = wheelRotations * Math.PI * wheelDiameter;

        return distanceTravelled;
    }

    public double getDistanceTravelled(CANSparkMax controller) {
        // 12.98
        return getDistanceTravelled(controller, Constants.Drive.gearing, Constants.Drive.wheelDiameter / 2);
    }

    /**
     * Get the total wheel speeds.
     *
     * @return the wheel speeds
     * @see DifferentialDriveWheelSpeeds
     * */
    public DifferentialDriveWheelSpeeds getWheelSpeeds() {
        var left = RPMToMetersPerSecond((left1.getEncoder().getVelocity() + left2.getEncoder().getVelocity()) / 2);
        var right = -RPMToMetersPerSecond((right1.getEncoder().getVelocity() + right2.getEncoder().getVelocity()) / 2);
        return new DifferentialDriveWheelSpeeds(left / 10, right / 10);
    }

    private double RPMToMetersPerSecond(double rpm, double r) {
        return 0.1047 * r * rpm;
    }

    /**
     * Convert RPM to m/s based on a default wheel radius of 6 inches (.1524 meters).
     */
    private double RPMToMetersPerSecond(double rpm) {
        return RPMToMetersPerSecond(rpm, 0.0762);
    }
}
