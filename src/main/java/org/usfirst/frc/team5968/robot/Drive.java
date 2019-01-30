package org.usfirst.frc.team5968.robot; 

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.GenericHID.Hand;

import org.usfirst.frc.team5968.robot.PortMap.CAN;
import org.usfirst.frc.team5968.robot.PortMap.USB;

public class Drive implements IDrive {

    private GenericHID xbox;
    private NavXMXP navX;
    private DriveMode driveMode;

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

        xbox = new XboxController(PortMap.portOf(USB.XBOXCONTROLLER));

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

        return navX.getYaw();

    }

    private void setRobotDriveAngle(){

        xleftJoystick = xbox.getX(Hand.kLeft);
        yleftJoystick = xbox.getY(Hand.kRight);
        joyStickAngle = Math.atan(xleftJoystick / yleftJoystick);
        robotDriveAngle = joyStickAngle + robotAngle;    //angle relative to robot
        robotXDriveDirection = 1 / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));
        robotYDriveDirection = Math.tan(robotDriveAngle) / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));

    }
    
    private void setRobotSpeed(){

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
         
   
}
