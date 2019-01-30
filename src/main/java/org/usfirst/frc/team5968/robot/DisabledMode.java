package org.usfirst.frc.team5968.robot; 

public class DisabledMode implements IRobotMode {

    private IHook hook;
    private ILauncher launcher;

    public DisabledMode(IHook hook, ILauncher launcher) {

        this.hook = hook; 
        this.launcher = launcher; 

    } 

    @Override
    public void init() {

    }

    @Override
    public void periodic() {

    }

}