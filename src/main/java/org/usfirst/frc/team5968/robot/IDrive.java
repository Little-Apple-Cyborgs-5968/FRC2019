package org.usfirst.frc.team5968.robot; 

public interface IDrive {

    public DriveMode getCurrentDriveMode();
    
    /*
     * Drive straight forward a specified distance at a specified speed
     */
    public void driveDistance(double distanceInches, double xDirectionSpeed, double yDirectionSpeed);

    /*
     * Turn in place a specified angle at a specified speed
     */
    public void rotateDegrees(double angle, double angularSpeed);

    /*
     * completionRoutine is called when the current action has been completed
     */
    public void driveDistance(double xDirectionSpeed, double yDirectionSpeed, double distanceInches, Runnable completionRoutine);

    public void rotateDegrees(double relativeAngle, double angularSpeed, Runnable completionRoutine);

    /*
     * This is the method used to drive manually during teleoperated mode
     */
    public void driveManual(double xDirectionSpeed, double yDirectionSpeed);

    public void lookAt(double angle, double speed);

    public void init();
    
    /*
     * Called periodically to actually execute the driving and rotating set by
     * the driveDistance() and rotateDegrees() methods
     */
    public void periodic();

}
