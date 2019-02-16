package org.usfirst.frc.team5968.robot;

public class HABLineAuto implements IRobotMode {

    private IDrive drive;

    private double robotSpeed;
    private static final double HIGH = 0.5;
    private static final double LOW = 0.0;

    private double input;

    public HABLineAuto(IDrive drive) {
        this.drive = drive;
        robotSpeed = LOW;
    }

    @Override
    public void init() {
        robotSpeed = LOW;
        driveStraight();
    }

    @Override
    public void periodic() {
        if(crossedLine()) {
            //drive.driveDistance(0, robotSpeed, 6.0, /* Runnable? */);
            robotSpeed = LOW;
        }
    }

    public void driveStraight() {
        robotSpeed = HIGH;
        drive.driveManual(0, robotSpeed);
    }

    private boolean crossedLine() {
        if(input < 64) {
            return true;
        } else {
            return false;
        }
    }

}
