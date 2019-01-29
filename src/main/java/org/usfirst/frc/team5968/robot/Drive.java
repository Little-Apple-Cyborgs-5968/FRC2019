package org.usfirst.frc.team5968.robot; 
import java.lang.Object;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.kauailabs.navx.frc.AHRS;

public class Drive implements IDrive {

    private GenericHID xbox;
    private NavXMXP navX;

    private TalonSRX leftMotorControllerLead;
    private TalonSRX leftMotorControllerFollow;
    private TalonSRX rightMotorControllerLead;
    private TalonSRX rightMotorControllerFollow;
    private TalonSRX middleMotorControllerLead;
    private TalonSRX middleMotorControllerFollow;

    private double xleftJoystick;
    private double yleftJoystick;
    private double robotAngle;
    private double joyStickAngle;
    private double robotDriveAngle;
    private double robotXDriveDirection;
    private double robotYDriveDirection;
    private double robotSpeed;

    public Drive(){

        xbox = new XboxController(XBOXPORT);

        leftMotorControllerLead = new TalonSRX(LEFT_MOTOR_CONTROLLER_LEAD_PORT);
        leftMotorControllerFollow = new TalonSRX(LEFT_MOTOR_CONTROLLER_FOLLOW_PORT);
        rightMotorControllerLead = new TalonSRX(RIGHT_MOTOR_CONTROLLER_LEAD_PORT);
        rightMotorControllerFollow = new TalonSRX(RIGHT_MOTOR_CONTROLLER_FOLLOW_PORT);
        middleMotorControllerLead = new TalonSRX(MIDDLE_MOTOR_CONTROLLER_LEAD_PORT);
        middleMotorControllerFollow = new TalonSRX(MIDDLE_MOTOR_CONTROLLER_FOLLOW_PORT);

        leftMotorControllerFollow.follow(leftMotorControllerLead);
        rightMotorControllerFollow.follow(rightMotorControllerLead);
        middleMotorControllerFollow.follow(middleMotorControllerLead);
    
    }
    
    private void getRobotSpeed(){

        robotSpeed = Math.sqrt(Math.pow(xleftJoystick, 2) + Math.pow(yleftJoystick, 2));

    }

    private void getRobotAngle(){

        robotAngle = navX.getYaw();

    }

    private void setRobotDriveAngle(){

        xleftJoystick = xbox.getX(kLeft);
        yleftJoystick = xbox.getY(kRight);
        joyStickAngle = Math.atan(xleftJoystick / yleftJoystick);
        robotDriveAngle = joyStickAngle + robotAngle;    //angle relative to robot
        robotXDriveDirection = 1 / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));
        robotYDriveDirection = Math.tan(robotDriveAngle) / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));

    }
    
    private void setRobotSpeed(){

        leftMotorControllerLead.set(robotSpeed * robotYDriveDirection);
        rightMotorControllerLead.set(robotSpeed * robotYDriveDirection);
        middleMotorControllerLead.set(robotSpeed * robotXDriveDirection);

    }

    @Override
    public void driveDistance(double distanceInches, double xDirectionSpeed, double yDirectionSpeed) {
    }

    @Override
    public void rotateDegrees(double angle, double angularSpeed) {
    }
    
    @Override
    public void driveDistance(double xDirectionSpeed, double yDirectionSpeed, double distanceInches, Runnable completionRoutine) {
    }
    
    @Override
    public void rotateDegrees(double relativeAngle, double angularSpeed, Runnable completionRoutine) {
    }
    
    @Override
    public void driveManual(double xDirectionSpeed, double yDirectionSpeed) {
    }
    
    @Override
    public void init() {
    }
    
    @Override 
    public void periodic(){
        getRobotAngle();
        getRobotSpeed(); 
        setRobotDriveAngle();
        setRobotSpeed(); 
    }
         
   
