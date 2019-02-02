package org.usfirst.frc.team5968.robot; 

public class HatchPanelAuto implements IRobotMode {

    private IDrive drive; 
    private IHook hook;  
    //private final double robotSpeed;

    public HatchPanelAuto(IDrive drive, IHook hook) {

        this.drive = drive;
        this.hook = hook; 

    }

@Override
public void init() {

    goStraight();

}

@Override
public void periodic() {

}

//Step 1: Go straight to Cargo Ship and put panel on
public void goStraight() {

    drive.driveDistance(xDirectionSpeed, yDirectionSpeed, distanceInches, completionRoutine); //add in values
    hook.releasePanel();

}

//Step 2: Get panel

}
