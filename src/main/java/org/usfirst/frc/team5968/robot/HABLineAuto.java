package org.usfirst.frc.team5968.robot; 

import org.usfirst.frc.team5968.Drive;

public class HABLineAuto implements IRobotMode {

    private IDrive drive;

    private double robotSpeed;
    private static final double HIGH = 0.5;
    private static final double LOW = 0.0;

    public HABLineAuto(IDrive drive) {
        this.drive = drive; 

        robotSpeed = LOW;
    }

    @Override
    public void init() {
        robotSpeed = HIGH;
        driveStraight(); 
    }

    @Override
    public void periodic() {
        if(crossedLine()) {
            drive.driveDistance(0, robotSpeed, 12.0, completionRoutine); // add real completion routine later
            robotSpeed = LOW;
        }  
    }

    public void driveStraight() {
        drive.driveManual(0, robotSpeed);
    }

    private boolean crossedLine() {
        // checks whether robot has crossed line.
            // returns false if visual sensor detects carpet
            // returns true if visual sensor detects HAB line
    }

}
