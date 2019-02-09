package org.usfirst.frc.team5968.robot; 

public class AutonomousMode implements IRobotMode {

    private IDrive drive; 
    private IHook hook;

    public AutonomousMode(IDrive drive, IHook hook) {
        this.drive = drive;
        this.hook = hook; 

    }

    @Override
    public void init() {

    }

    @Override
    public void periodic() {

    }
}
