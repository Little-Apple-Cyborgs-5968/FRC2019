package org.usfirst.frc.team5968.robot; 

import org.usfirst.frc.team5968.robot.PortMap.USB;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TeleoperatedMode implements IRobotMode {

    private XboxController xboxController; 
    private IDrive drive; 
    private IHook hook;
    private ILauncher launcher;

    private final double TOLERANCE = 0.1; 

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

        drive.driveManual(0, getLeftStickY());

    }

    private double getLeftStickY() {
            
        double leftY = xboxController.getY(Hand.kLeft); 
        return (Math.abs(leftY) < TOLERANCE) ? 0 : -Math.pow(leftY, 3); 

    }

}
