package org.usfirst.frc.team5968.robot;

public class AutonomousMode implements IRobotMode{

    private IDrive drive;
    private IHook hook;
    private ILauncher launcher;

    public AutonomousMode(IDrive drive, IHook hook, ILauncher launcher) {
        this.drive = drive;
        this.hook = hook;
        this.launcher = launcher;
    }

    public void init(){

    }

    public void periodic(){

    }
}