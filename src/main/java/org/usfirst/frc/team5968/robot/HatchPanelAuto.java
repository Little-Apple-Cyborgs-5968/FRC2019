package org.usfirst.frc.team5968.robot; 

public class HatchPanelAuto implements IRobotMode {

    /*private IDrive drive; 
    private IHook hook;  

    private final double ROBOT_SPEED = 0.5; //these values may change
    private final double ROTATION_SPEED = 0.3; 

    public HatchPanelAuto(IDrive drive, IHook hook) {
        this.drive = drive;
        this.hook = hook; 

    }
*/ 
@Override
public void init() {
    //goStraight();

}

@Override
public void periodic() {
//autonomous doesn't need periodic 
} 

/*Step 1: Go straight to Cargo Ship and put panel on
* drive straight, align with line, use hook to release panel 
*/ 
/* 
public void goStraight() {
    drive.driveDistance(ROBOT_SPEED, ROBOT_SPEED, 100, () -> alignToLine()); //add in values 
    hook.releasePanel();

} */ 

/*Step 2: Go and get panel from Loading Station 
* turn around approx. 135 degrees, drive straight, grab panel
*/ 
/* 
public void goDiagonalLeft() {
    drive.rotateDegrees(135, ROTATION_SPEED); 
    drive.driveDistance(ROBOT_SPEED, ROBOT_SPEED, 100, () -> alignToLine()); //add in values 
    hook.grabPanel(); 

} */ 

/*Step 3: Go to Cargo Ship and put panel on
*turn around approx. -135 degrees, drive straight, go farther, 
*find other line, align with line, use hook to release panel 
*/ 
/* 
public void goDiagonalRight() {
    drive.rotateDegrees(-135, ROTATION_SPEED);
    drive.driveDistance(ROBOT_SPEED, ROBOT_SPEED, 100, () -> alignToLine()); //add in values
    hook.releasePanel();
    
} */ 

}
