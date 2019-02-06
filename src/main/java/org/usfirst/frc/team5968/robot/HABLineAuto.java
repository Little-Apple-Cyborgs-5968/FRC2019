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
        
    }

   /** 
   * Since all we really need to do is go straight cross the HAB line,
   * there isn't any need for a sensor. Simply driving straight 48 inches
   * will cross the HAB line with extra distance to spare.
   */
    public void driveStraight() {
        drive.driveDistance(0, robotSpeed, 48.0, completionRoutine); // Add actual completion routine later
    }

}
