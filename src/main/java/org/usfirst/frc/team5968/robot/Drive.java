package org.usfirst.frc.team5968.robot; 

import com.ctre.phoenix.motorcontrol.ControlMode;
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

    private static final double ROTATION_SPEED_THRESHOLD = 0.00000001; 
    private static final double DELTA_ANGLE_SPEED_POWER = 3; 

    public Drive(IGyroscopeSensor gyroscope){

        this.gyroscope = gyroscope;  

        leftMotorControllerLead = new TalonSRX(PortMap.portOf(CAN.LEFT_MOTOR_CONTROLLER_LEAD));
        leftMotorControllerFollow = new TalonSRX(PortMap.portOf(CAN.LEFT_MOTOR_CONTROLLER_FOLLOW));
        rightMotorControllerLead = new TalonSRX(PortMap.portOf(CAN.RIGHT_MOTOR_CONTROLLER_LEAD));
        rightMotorControllerFollow = new TalonSRX(PortMap.portOf(CAN.RIGHT_MOTOR_CONTROLLER_FOLLOW));
        middleMotorControllerLead = new TalonSRX(PortMap.portOf(CAN.MIDDLE_MOTOR_CONTROLLER_LEAD));
        middleMotorControllerFollow = new TalonSRX(PortMap.portOf(CAN.MIDDLE_MOTOR_CONTROLLER_FOLLOW));

        leftMotorControllerFollow.setInverted(true);
        leftMotorControllerLead.setInverted(true);
        middleMotorControllerFollow.setInverted(true);
        middleMotorControllerLead.setInverted(true);

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

    }
    
    @Override 
    public void periodic() {
        double leftSpeed;
        double rightSpeed;
        double middleSpeed;

        // Linear Motion
        double fieldAngle = Math.atan2(yDirectionSpeed, xDirectionSpeed); 
        double robotDriveAngle = fieldAngle + gyroscope.getYaw(); 

        double speedMagnitude = Math.sqrt(Math.pow(xDirectionSpeed, 2) + Math.pow(yDirectionSpeed, 2)); 
        leftSpeed = Math.cos(robotDriveAngle) * speedMagnitude;
        rightSpeed = leftSpeed; 
        middleSpeed = Math.sin(robotDriveAngle) * speedMagnitude; 

        // Angular Motion

        if (rotationSpeed > ROTATION_SPEED_THRESHOLD) {
            double deltaAngle = gyroscope.getYaw() - desiredAngle; 

            if (Math.abs(deltaAngle) > Math.PI) {
                deltaAngle -= (Math.PI * 2) * Math.signum(deltaAngle); 
            }

            double actualSpeed = rotationSpeed * Math.pow(Math.abs(deltaAngle) / Math.PI, DELTA_ANGLE_SPEED_POWER); 
            leftSpeed *= 1 - actualSpeed; 
            rightSpeed *= 1 - actualSpeed; 

            if (deltaAngle < 0) {
                leftSpeed += rotationSpeed;
                rightSpeed -= rotationSpeed;
            }
            else {
                leftSpeed -= rotationSpeed;
                rightSpeed += rotationSpeed;
            }

            Debug.periodic(); 
            //Debug.logPeriodic(gyroscope.getYaw());  
            //Debug.logPeriodic(robotDriveAngle);  
            //Debug.logPeriodic(actualSpeed);  

        }

        // Set Motor Speeds
        leftMotorControllerLead.set(ControlMode.PercentOutput, leftSpeed);
        rightMotorControllerFollow.set(ControlMode.PercentOutput, rightSpeed);
        middleMotorControllerFollow.set(ControlMode.PercentOutput, middleSpeed);
    }
}
