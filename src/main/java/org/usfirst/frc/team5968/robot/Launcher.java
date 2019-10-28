package org.usfirst.frc.team5968.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class Launcher implements ILauncher
{

    private TalonSRX launcherMotor;
    private DigitalInput bottomSwitch;
    private DigitalInput topSwitch;

    private double motorSpeed;
    public boolean isAuto;
    private static final double HIGH = 0.9;
    private static final double LOW = 0.0;

    public Launcher() {
        launcherMotor = new TalonSRX(PortMap.CAN.CONVEYER_MOTOR_CONTROLLER);
        launcherMotor.setInverted(true);

        bottomSwitch = new DigitalInput(9);
        topSwitch = new DigitalInput(8);

        stop();
        isAuto = false;
    }

    @Override
    public void stop() {
        motorSpeed = LOW;
    }

    @Override
    public void start() {
        motorSpeed = HIGH;
        isAuto = false;
    }

    @Override
    public void init() {
        stop();
    }

    @Override
    public void periodic() {
        if(bottomSwitch.get() && !isAuto) {
            isAuto = true;
        } else if(topSwitch.get()) {
            isAuto = false;
        }

        launcherMotor.set(ControlMode.PercentOutput, isAuto ? HIGH : motorSpeed);
    }
}
