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
    private ICargoGuide cargoGuide;
    private IGyroscopeSensor gyroscope;

    public Robot() {
        gyroscope = new NavXMXP();
        drive = new Drive(gyroscope);
        hook = new Hook();
        launcher = new Launcher();
        cargoGuide = new CargoGuide();

        disabledMode = new DisabledMode(hook, launcher);
        autonomousMode = new AutonomousMode(drive, hook);
        teleoperatedMode = new TeleoperatedMode(drive, hook, launcher, cargoGuide);
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
        cargoGuide.init();
        drive.init();
        hook.init();
        launcher.init();
    }

    private void doPeripheralPeriodicProcessing() {
        drive.periodic();
        launcher.periodic();

        Debug.periodic();
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
