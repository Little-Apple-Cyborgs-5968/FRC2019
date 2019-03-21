package org.usfirst.frc.team5968.robot;

public class HABLineAuto implements IRobotMode {

    private IDrive drive;

    private static final double DRIVE_DISTANCE = 64.0; // inches
    private static final double DRIVE_SPEED = 0.5;

    public HABLineAuto(IDrive drive) {
        this.drive = drive;
    }

    @Override
    public void init() {
        drive.driveDistance(DRIVE_DISTANCE, DRIVE_SPEED);
    }

    @Override
    public void periodic() {
    // Nothing to do
    }
}
