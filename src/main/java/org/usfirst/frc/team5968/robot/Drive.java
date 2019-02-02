package org.usfirst.frc.team5968.robot; 

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.SerialPort;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import org.usfirst.frc.team5968.robot.PortMap.CAN;
import org.usfirst.frc.team5968.robot.PortMap.USB;

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

    private double xleftJoystick;
    private double yleftJoystick;
    private double robotAngle;
    private double fieldDriveAngle;
    private double robotDriveAngle;
    private double robotXDriveDirection;
    private double robotYDriveDirection;
    private double robotSpeed;

    public Drive(IGyroscopeSensor gyroscope){

        this.gyroscope = gyroscope;  

        leftMotorControllerLead = new TalonSRX(PortMap.portOf(CAN.LEFT_MOTOR_CONTROLLER_LEAD));
        leftMotorControllerFollow = new TalonSRX(PortMap.portOf(CAN.LEFT_MOTOR_CONTROLLER_FOLLOW));
        rightMotorControllerLead = new TalonSRX(PortMap.portOf(CAN.RIGHT_MOTOR_CONTROLLER_LEAD));
        rightMotorControllerFollow = new TalonSRX(PortMap.portOf(CAN.RIGHT_MOTOR_CONTROLLER_FOLLOW));
        middleMotorControllerLead = new TalonSRX(PortMap.portOf(CAN.MIDDLE_MOTOR_CONTROLLER_LEAD));
        middleMotorControllerFollow = new TalonSRX(PortMap.portOf(CAN.MIDDLE_MOTOR_CONTROLLER_FOLLOW));

        leftMotorControllerFollow.follow(leftMotorControllerLead);
        rightMotorControllerFollow.follow(rightMotorControllerLead);
        middleMotorControllerFollow.follow(middleMotorControllerLead);
    
    }

    @Override
    public DriveMode getCurrentDriveMode(){
        return driveMode;
    }
    
    private void getRobotSpeed(){

        robotSpeed = Math.sqrt(Math.pow(xleftJoystick, 2) + Math.pow(yleftJoystick, 2));

    }

    private double getRobotAngle(){
        
        return gyroscope.getYaw();
    }

    
    private void setRobotDriveAngle(xleftJoystick, yleftJoystick){

        xleftJoystick = xboxController.getY(Hand.kLeft);
        yleftJoystick = xboxController.getY(Hand.kLeft);
        fieldDriveAngle = Math.atan(xleftJoystick / yleftJoystick);
        robotDriveAngle = fieldDriveAngle + robotAngle;    //angle relative to robot
        //robotXDriveDirection = 1 / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));
        //robotYDriveDirection = Math.tan(robotDriveAngle) / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));

    } 
    
    private void setRobotSpeed(double robotXDriveDirection, double robotYDriveDirection){

        leftMotorControllerLead.set(ControlMode.PercentOutput, robotSpeed * robotYDriveDirection);
        rightMotorControllerLead.set(ControlMode.PercentOutput, robotSpeed * robotYDriveDirection);
        middleMotorControllerLead.set(ControlMode.PercentOutput, robotSpeed * robotXDriveDirection);

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
       
        driveMode = DriveMode.DRIVERCONTROL;
        robotXDriveDirection = xDirectionSpeed;
        robotYDriveDirection = yDirectionSpeed;

    }

    private void stop() {

        driveManualImplementation(0.0, 0.0);

    }
    
    @Override 
    public void lookAt(double angle) {
        
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
    public void periodic(){

        if (driveMode == DriveMode.DRIVERCONTROL) {
            setRobotSpeed(0, 1); 
        }
    
         xleftJoystick = 1 / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));
         yleftJoystick = Math.tan(robotDriveAngle) / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));

    }
        
}
