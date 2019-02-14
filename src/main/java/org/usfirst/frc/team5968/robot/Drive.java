package org.usfirst.frc.team5968.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5968.robot.PortMap.CAN;

public class Drive implements IDrive {

    private IGyroscopeSensor gyroscope;
    private DriveMode driveMode;

    private TalonSRX leftMotorControllerLead;
    private TalonSRX leftMotorControllerFollow;
    private TalonSRX rightMotorControllerLead;
    private TalonSRX rightMotorControllerFollow;
    private TalonSRX middleMotorControllerLead;
    private TalonSRX middleMotorControllerFollow;

    private Runnable currentCompletionRoutine;

    private double xDirectionSpeed;
    private double yDirectionSpeed;
    private double desiredAngle;
    private double rotationSpeed;

    private static final double DELTA_ANGLE_SPEED_POWER = 1;
    private static final double MAINTAINING_HEADING_SPEED = 0.5;

    public Drive(IGyroscopeSensor gyroscope){

        this.gyroscope = gyroscope;

        leftMotorControllerLead = new TalonSRX(PortMap.CAN.LEFT_MOTOR_CONTROLLER_LEAD);
        leftMotorControllerFollow = new TalonSRX(PortMap.CAN.LEFT_MOTOR_CONTROLLER_FOLLOW);
        rightMotorControllerLead = new TalonSRX(PortMap.CAN.RIGHT_MOTOR_CONTROLLER_LEAD);
        rightMotorControllerFollow = new TalonSRX(PortMap.CAN.RIGHT_MOTOR_CONTROLLER_FOLLOW);
        middleMotorControllerLead = new TalonSRX(PortMap.CAN.MIDDLE_MOTOR_CONTROLLER_LEAD);
        middleMotorControllerFollow = new TalonSRX(PortMap.CAN.MIDDLE_MOTOR_CONTROLLER_FOLLOW);

        leftMotorControllerLead.setNeutralMode(NeutralMode.Brake);
        leftMotorControllerFollow.setNeutralMode(NeutralMode.Brake);
        middleMotorControllerLead.setNeutralMode(NeutralMode.Brake);
        middleMotorControllerFollow.setNeutralMode(NeutralMode.Brake);
        rightMotorControllerLead.setNeutralMode(NeutralMode.Brake);
        rightMotorControllerFollow.setNeutralMode(NeutralMode.Brake);

        leftMotorControllerFollow.setInverted(false);
        leftMotorControllerLead.setInverted(false);
        middleMotorControllerFollow.setInverted(true);
        middleMotorControllerLead.setInverted(true);
        rightMotorControllerLead.setInverted(true);
        rightMotorControllerFollow.setInverted(true);

        leftMotorControllerFollow.follow(leftMotorControllerLead);
        rightMotorControllerFollow.follow(rightMotorControllerLead);
        middleMotorControllerFollow.follow(middleMotorControllerLead);

    }

    @Override
    public DriveMode getCurrentDriveMode(){
        return driveMode;
    }

    @Override
    public void driveDistance(double distanceInches, double xDirectionSpeed, double yDirectionSpeed) {

    }

    @Override
    public void rotateDegrees(double angle, double angularSpeed) {

    }

    @Override
    public void driveDistance(double xDirectionSpeed, double yDirectionSpeed, double distanceInches, Runnable completionRoutine) {
        setCompletionRoutine(completionRoutine);

    }

    @Override
    public void rotateDegrees(double relativeAngle, double angularSpeed, Runnable completionRoutine) {

    }

    @Override
    public void driveManual(double xDirectionSpeed, double yDirectionSpeed) {
        setCompletionRoutine(null);
        driveManualImplementation(xDirectionSpeed, yDirectionSpeed);

    }

    private void driveManualImplementation(double xDirectionSpeed, double yDirectionSpeed) {
        this.xDirectionSpeed = xDirectionSpeed;
        this.yDirectionSpeed = yDirectionSpeed;
        driveMode = DriveMode.DRIVERCONTROL;

    }

    private void stop() {
        driveManualImplementation(0.0, 0.0);

    }

    @Override
    public void lookAt(double angle, double speed) {
        setCompletionRoutine(null);

        desiredAngle = MathUtilities.normalizeAngle(angle);
        rotationSpeed = speed;


    }

    @Override
    public void maintainHeading() {
        setCompletionRoutine(null);
        desiredAngle = gyroscope.getYaw();
        rotationSpeed = 1.0;
    }

    private void setCompletionRoutine(Runnable completionRountime) {
        if (currentCompletionRoutine != null) {
            throw new IllegalStateException("Tried to perform a lift action while one was already in progress!");
        }

        currentCompletionRoutine = completionRountime;

    }

    @Override
    public void init() {
        currentCompletionRoutine = null;
        stop();
        gyroscope.resetYaw();

    }

    @Override
    public void periodic() {
        double leftSpeed;
        double rightSpeed;
        double middleSpeed;

        // Linear Motion
        double fieldAngle = Math.atan2(yDirectionSpeed, xDirectionSpeed) - (Math.PI / 2);
        double robotDriveAngle = fieldAngle + gyroscope.getYaw();

        double speedMagnitude = Math.sqrt(Math.pow(xDirectionSpeed, 2) + Math.pow(yDirectionSpeed, 2));
        leftSpeed = Math.cos(robotDriveAngle) * speedMagnitude * 0.5;
        rightSpeed = leftSpeed;
        middleSpeed = Math.sin(robotDriveAngle) * speedMagnitude;

        Debug.logPeriodic("---------------------------------------------");
        //Debug.logPeriodic("controllerAngle: " + MathUtilities.normalizeAngle(fieldAngle));
        Debug.logPeriodic("      gyroAngle: " + gyroscope.getYaw());
        //Debug.logPeriodic("robotDriveAngle:" + MathUtilities.normalizeAngle(robotDriveAngle));

        // Angular Motion
        if (true) {
            double deltaAngle = gyroscope.getYaw() - desiredAngle;

            //Debug.logPeriodic(" desiredAngle: " + desiredAngle);
            Debug.logPeriodic(" deltaAngle1: " + deltaAngle);

            if (Math.abs(deltaAngle) > Math.PI) {
                deltaAngle -= (Math.PI * 2) * Math.signum(deltaAngle);
            }

            Debug.logPeriodic(" deltaAngle2: " + deltaAngle);

            double actualSpeed = rotationSpeed * Math.pow(Math.abs(deltaAngle) / Math.PI, DELTA_ANGLE_SPEED_POWER);
            leftSpeed *= 1 - actualSpeed;
            rightSpeed *= 1 - actualSpeed;

            if (deltaAngle < 0) {
                leftSpeed += actualSpeed;
                rightSpeed -= actualSpeed;
            }
            else {
                leftSpeed -= actualSpeed;
                rightSpeed += actualSpeed;
            }

            Debug.logPeriodic(" actualSpeed: " + actualSpeed);

        }

        // Set Motor Speeds
        leftMotorControllerLead.set(ControlMode.PercentOutput, leftSpeed);
        rightMotorControllerLead.set(ControlMode.PercentOutput, rightSpeed);
        middleMotorControllerLead.set(ControlMode.PercentOutput, middleSpeed);
    }
}
