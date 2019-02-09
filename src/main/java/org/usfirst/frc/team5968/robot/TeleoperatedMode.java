package org.usfirst.frc.team5968.robot; 

import org.usfirst.frc.team5968.robot.PortMap.USB;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TeleoperatedMode implements IRobotMode {

    private XboxController xboxController; 
    private IDrive drive; 
    private IHook hook;
    private ILauncher launcher;

    private final double TOLERANCE = 0.1 * 5.0;

    public TeleoperatedMode(IDrive drive, IHook hook, ILauncher launcher) {

        xboxController = new XboxController(PortMap.portOf(USB.XBOXCONTROLLER));

        this.drive = drive; 
        this.hook = hook; 
        this.launcher = launcher; 

    }
   
    @Override
    public void init() {
        drive.init(); 
    }

    @Override
    public void periodic() {

        drive.driveManual(getLeftStickX(), getLeftStickY());
       
        double rightX = xboxController.getX(Hand.kRight); 
        double rightY = xboxController.getX(Hand.kRight);
        double angle = -(Math.atan2(rightY, rightX) - (Math.PI / 2)); 
        double rotationSpeed = Math.sqrt(Math.pow(rightX, 2) + Math.pow(rightY, 2)); 
        if (rotationSpeed < TOLERANCE) {

            rotationSpeed = 0; 
        }
        else {
            rotationSpeed = Math.pow(rotationSpeed, 3); 
        }

        drive.lookAt(angle, rotationSpeed);
    }

    private double getLeftStickY() {
            
        double leftY = xboxController.getY(Hand.kLeft); 
        return (Math.abs(leftY) < TOLERANCE) ? 0 : -Math.pow(leftY, 3); 

    }

    private double getLeftStickX() {
            
        double leftX = xboxController.getX(Hand.kLeft); 
        return (Math.abs(leftX) < TOLERANCE) ? 0 : Math.pow(leftX, 3); 

    }

}
