package org.usfirst.frc.team5968.robot; 

import org.usfirst.frc.team5968.robot.PortMap.USB;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TeleoperatedMode implements IRobotMode {

    //private double xleftJoystick;
    //private double yleftJoystick;

    private IDrive drive; 
    private IHook hook;
    private ILauncher launcher;

    public TeleoperatedMode(IDrive drive, IHook hook, ILauncher launcher) {

        GenericHID xbox = new XboxController(PortMap.portOf(USB.XBOXCONTROLLER));

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

    }
}
