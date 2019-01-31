package org.usfirst.frc.team5968.robot; 

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends RobotBase {
    
    private IRobotMode disabledMode;
    private IRobotMode autonomousMode;
    private IRobotMode teleoperatedMode;
    
    private IDrive drive;
    private IHook hook;
    private ILauncher launcher;
    
    public Robot() {
        drive = new Drive();
        hook = new Hook();
        launcher = new Launcher();

        disabledMode = new DisabledMode(hook, launcher);
        autonomousMode = new AutonomousMode(drive, hook, launcher);
        teleoperatedMode = new TeleoperatedMode(drive, hook, launcher);
    }
    
    @Override
    public void startCompetition() {
        HAL.observeUserProgramStarting();
        
        IRobotMode currentMode = null;
        IRobotMode desiredMode = null;
        
        while (true) {
            desiredMode = getDesiredMode();
        
            if (desiredMode != currentMode) {
                LiveWindow.setEnabled(isTest());
                doPeripheralReinitialization();
            	desiredMode.init();
            	currentMode = desiredMode;
            }
            currentMode.periodic();
            doPeripheralPeriodicProcessing();
            SmartDashboard.updateValues();
            LiveWindow.updateValues();
        }
    }
    
    private void doPeripheralReinitialization() {
        drive.init();
   
    }
    
    private void doPeripheralPeriodicProcessing() {
      
        drive.periodic();

    }
    
    private IRobotMode getDesiredMode() {
        if (isDisabled()) {
            HAL.observeUserProgramDisabled();
            return disabledMode;
        } else if (isAutonomous()) {
            HAL.observeUserProgramAutonomous();
        return autonomousMode;
        } else if (isOperatorControl()) {
            HAL.observeUserProgramTeleop();
            return teleoperatedMode;
        } else if (isTest()) {
            HAL.observeUserProgramTest();
            return teleoperatedMode;
        } else {
            throw new IllegalStateException("Robot is in an invalid mode");
        }
    }

}
