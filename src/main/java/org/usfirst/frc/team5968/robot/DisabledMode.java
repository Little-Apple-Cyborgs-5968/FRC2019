package org.usfirst.frc.team5968.robot;

public class DisabledMode implements IRobotMode {

    private IHook hook;
    private ILauncher launcher;
    private ILineDetector lineDetector;

    public DisabledMode(IHook hook, ILauncher launcher, ILineDetector lineDetector) {
        this.hook = hook;
        this.launcher = launcher;
        this.lineDetector = lineDetector;
    }

    @Override
    public void init() {

    }

    @Override
    public void periodic() {
        Debug.logPeriodic("Line Detector Raw Value: " + lineDetector.getRawValue());
        Debug.logPeriodic("Is Robot on Line? " + lineDetector.isOnLine());
    }

}