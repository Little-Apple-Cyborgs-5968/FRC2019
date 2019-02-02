package org.usfirst.frc.team5968.robot; 

public class HABLineAuto implements IRobotMode {

    private IDrive drive; 

    public HABLineAuto(IDrive drive) {
        
        this.drive = drive; 

    }

@Override
public void init() {

    driveStraight(); 
    
}

@Override
public void periodic() {

}

//crosses HAB line
public void driveStraight() {

}

}
