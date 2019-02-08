package org.usfirst.frc.team5968.robot; 

public class HABLineAuto implements IRobotMode {

    private IDrive drive; 

    private double robotSpeed;
    private static final double HIGH = 0.5;
    private static final double LOW = 0.0;

    public HABLineAuto(IDrive drive) {
        this.drive = drive; 

    }

@Override
public void init() {
    robotSpeed = LOW;
    driveStraight(); 
    
}

@Override
public void periodic() {
    // Visual sensor checks whether or not robot has crossed HAB line
    if(crossedLine()) {
        // if true: go forward a bit more, then stop (to ensure robot has crossed line)
        //drive.driveDistance(0, robotSpeed, 12.0, /* Runnable? */);
        robotSpeed = LOW;
    }  
        // if false: keep going forward  
}

//crosses HAB line
public void driveStraight() {
    // Just needs to set motors going forward
    drive.driveManual(0, robotSpeed);
}

private boolean crossedLine() {
    // checks whether robot has crossed line.
        // returns false if visual sensor detects carpet
        // returns true if visual sensor detects HAB line
        return false; 
}

}
