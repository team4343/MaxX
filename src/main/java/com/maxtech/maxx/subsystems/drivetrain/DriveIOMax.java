package com.maxtech.maxx.subsystems.drivetrain;

import com.kauailabs.navx.frc.AHRS;
import com.maxtech.maxx.Constants;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.REVPhysicsSim;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DifferentialDrivetrainSim;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;

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

    private final AHRS gyro = new AHRS();
    private final DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(gyro.getRotation2d(), new Pose2d(0.0, 0, new Rotation2d()));
    private final Field2d field = new Field2d();

    public DriveIOMax() {
        right.setInverted(true);
        gyro.reset();

        drivetrain = new DifferentialDrive(left, right);

        drivetrainSimulator.addSparkMax(left1, DCMotor.getNEO(1));
        drivetrainSimulator.addSparkMax(left2, DCMotor.getNEO(1));
        drivetrainSimulator.addSparkMax(right1, DCMotor.getNEO(1));
        drivetrainSimulator.addSparkMax(right2, DCMotor.getNEO(1));
    }

    @Override
    public void periodic() {
    }

    @Override
    public void simulationPeriodic() {
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
        return 0;
    }

    /** Get the distance travelled of one Spark Max motor controller. */
    public double getDistanceTravelled(CANSparkMax controller, double gearing, double wheelDiameter) {
        var motorRotations = controller.getEncoder().getPosition();
        var wheelRotations = motorRotations / gearing;
        double distanceTravelled = wheelRotations * Math.PI * wheelDiameter;

        return distanceTravelled;
    }

    public double getDistanceTravelled(CANSparkMax controller) {
        return getDistanceTravelled(controller, 12.98, Constants.Drive.gearing);
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
