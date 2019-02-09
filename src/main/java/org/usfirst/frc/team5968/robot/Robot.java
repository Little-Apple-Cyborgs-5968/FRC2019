package org.usfirst.frc.team5968.robot; 

import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.hal.HAL;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends RobotBase {
    
    private IRobotMode disabledMode;
    private IRobotMode autonomousMode;
    private IRobotMode teleoperatedMode;
    
    private IDrive drive;
    private IHook hook;
    private ILauncher launcher;
    private IGyroscopeSensor gyroscope;

    /*private class MotorTest implements IRobotMode
    {
        private Boolean lastButton0 = false;
        private Boolean lastButton1 = false;
        private TalonSRX[] motors;
        private int currentMotor = 0;
        private Joystick joystick;

        private Boolean lastDoubleButton = false;
        private Boolean doubleMode = false;

        public MotorTest() {
            int[] canPorts = {
                2, 3, 4, 5, 6, 7
            };

            motors = new TalonSRX[canPorts.length];

            for (int i = 0; i < canPorts.length; i++) {
                motors[i] = new TalonSRX(canPorts[i]);
            }

            joystick = new Joystick(0);
        } 

        @Override
        public void init()
        {
            currentMotor = 0;
            lastButton0 = false;
            lastButton1 = false;
            doubleMode = false;
            lastDoubleButton = false;
        }
        
        @Override
        public void periodic()
        {
            Boolean button0 = joystick.getRawButton(11);
            Boolean button1 = joystick.getRawButton(12);
            Boolean doubleButton = joystick.getRawButton(7);

            if (button0 && !lastButton0) {
                currentMotor--;

                if (currentMotor < 0) {
                    currentMotor = motors.length - 1;
                }
            } else if (button1 && !lastButton1) {
                currentMotor++;

                if (currentMotor >= motors.length) {
                    currentMotor = 0;
                }
            }

            if (doubleButton && !lastDoubleButton) {
                doubleMode = !doubleMode;
            }

            lastButton0 = button0;
            lastButton1 = button1;
            lastDoubleButton = doubleButton;
            double speed = joystick.getY();
            motors[currentMotor].set(ControlMode.PercentOutput, speed);
            int nextMotorIndex = -1;

            if (doubleMode) {
                nextMotorIndex = currentMotor + 1;

                if (nextMotorIndex >= motors.length) {
                    nextMotorIndex = 0;
                }

                motors[nextMotorIndex].set(ControlMode.PercentOutput, speed);
            }

            for (int i = 0; i < motors.length; i++) {
                if (i == currentMotor || i == nextMotorIndex) {
                    continue;
                }

                motors[i].set(ControlMode.PercentOutput, 0.0);
            }
        }
    } */ 
    
    public Robot() {
        gyroscope = new NavXMXP();
        //drive = new Drive(gyroscope);
        drive = new NullDrive(); 
        hook = new Hook();
        launcher = new Launcher();

        disabledMode = new DisabledMode(hook, launcher);
        autonomousMode = new AutonomousMode(drive, hook);
        teleoperatedMode = new TeleoperatedMode(drive, hook, launcher);
        //teleoperatedMode = new MotorTest();
    
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
        Debug.periodic(); 
        Debug.logPeriodic("Before");
        Debug.logPeriodic(Double.toString(gyroscope.getYaw()));
        Debug.logPeriodic("After");
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
