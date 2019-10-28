package org.usfirst.frc.team5968.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Launcher implements ILauncher
{

    private TalonSRX launcherMotor;
    private DigitalInput bottomSwitch;
    private DigitalInput topSwitch;

    private double motorSpeed;
    private static final double HIGH = 0.9;
    private static final double LOW = 0.0;
    private boolean isAuto;

    public Launcher() {
        launcherMotor = new TalonSRX(PortMap.CAN.CONVEYER_MOTOR_CONTROLLER);
        launcherMotor.setInverted(true);
        bottomSwitch = new DigitalInput(9);
        topSwitch = new DigitalInput(8);

        stop();
    }

    @Override
    public void stop() {
        motorSpeed = LOW;
        isAuto = false;
    }

    @Override
    public void start() {
        motorSpeed = HIGH;
    }

    @Override
    public void init() {
        stop();
    }
    
    @Override
    public void periodic() {
        launcherMotor.set(ControlMode.PercentOutput, motorSpeed);
        
        if(bottomSwitch.get() && !isAuto) {
            start();
            isAuto = true;
        } else if(topSwitch.get()) {
            stop();
        }
    }
}
