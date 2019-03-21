package org.usfirst.frc.team5968.robot;

public class HatchPanelAuto implements IRobotMode {

    private IDrive drive;
    private IHook hook;

    // correct these values as necessary
    private final double APPROACH_DISTANCE = 93.0; // inches
    private final double DOCK_DISTANCE = 13.0; // inches
    private final double BACK_OFF_DISTANCE = 25.0; // inches

    // Assumes robot is right of the line and needs to go left to get onto the line
    private final double ALIGNMENT_SPEED_AND_DIRECTION = -1.0;

    private final double APPROACH_SPEED = 0.75;
    private final double DOCK_SPEED = 0.25;
    private final double BACK_OFF_SPEED = 0.2;

    public HatchPanelAuto(IDrive drive, IHook hook) {
        this.drive = drive;
        this.hook = hook;
    }

@Override
public void init() {
    drive.driveDistance(APPROACH_DISTANCE, APPROACH_SPEED, () -> andThenAlign());
}

@Override
public void periodic() {
//autonomous doesn't need periodic
}

public void andThenAlign() {
    drive.driveToLine(ALIGNMENT_SPEED_AND_DIRECTION, () -> andThenDock());
}

public void andThenDock() {
    drive.driveDistance(DOCK_DISTANCE, DOCK_SPEED , () -> andThenReleaseAndBackOff());
}

public void andThenReleaseAndBackOff() {
    hook.releasePanel();
    drive.driveDistance(BACK_OFF_DISTANCE, BACK_OFF_SPEED);
}

}
