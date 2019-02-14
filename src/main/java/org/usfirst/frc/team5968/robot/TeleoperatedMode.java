package org.usfirst.frc.team5968.robot; 

import org.usfirst.frc.team5968.robot.PortMap.USB;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TeleoperatedMode implements IRobotMode {

    private XboxController xboxController; 
    private IDrive drive; 
    private IHook hook;
    private ILauncher launcher;
    private ICargoGuide cargoGuide;

    private boolean headingIsMaintained = true;
    
    private static final double TOLERANCE = 0.1 * 5.0;
    private static final double ROTATION_SPEED_THRESHOLD = 0.3;
    private static final double CONTROL_EXPONENT = 5.0;

    public TeleoperatedMode(IDrive drive, IHook hook, ILauncher launcher, ICargoGuide cargoGuide) {

        xboxController = new XboxController(PortMap.USB.XBOXCONTROLLER);

        this.drive = drive; 
        this.hook = hook; 
        this.launcher = launcher;
        this.cargoGuide = cargoGuide;

    }
   
    @Override
    public void init() {
        drive.init(); 
    }

    @Override
    public void periodic() {

        drive.driveManual(getLeftStickX(), getLeftStickY());
       
        double rightX = xboxController.getX(Hand.kRight); 
        double rightY = xboxController.getY(Hand.kRight);
        double angle = (Math.atan2(rightY, rightX) + (Math.PI / 2)); 
        double rotationSpeed = Math.sqrt(Math.pow(rightX, 2) + Math.pow(rightY, 2)); 
        if (rotationSpeed < TOLERANCE) {

            rotationSpeed = 0; 
        }
        else {
            rotationSpeed = Math.pow(rotationSpeed, 3); 
        }

        if(rotationSpeed < ROTATION_SPEED_THRESHOLD) {
            if(!headingIsMaintained) {
                drive.maintainHeading();
                headingIsMaintained = true;
            }
        } else {
            drive.lookAt(angle, rotationSpeed);
            headingIsMaintained = false;
        }

        if (xboxController.getBumper(Hand.kRight)) {
            launcher.start();
        } else {
            launcher.stop();
        }
    
        if (xboxController.getYButton()) {
            hook.grabPanel();
    
        }
    
        if (xboxController.getXButton()) {
            hook.releasePanel();
    
        }

        if (xboxController.getBButton()) {
            cargoGuide.engageGuide();

        }

        if(xboxController.getAButton()) {
            cargoGuide.disengageGuide();

        }
    
    }

    private double getLeftStickY() {
            
        double leftY = xboxController.getY(Hand.kLeft); 
        return (Math.abs(leftY) < TOLERANCE) ? 0 : -Math.pow(leftY, CONTROL_EXPONENT); 

    }

    private double getLeftStickX() {
            
        double leftX = xboxController.getX(Hand.kLeft); 
        return (Math.abs(leftX) < TOLERANCE) ? 0 : Math.pow(leftX, CONTROL_EXPONENT); 

    }
}
