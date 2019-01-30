package org.usfirst.frc.team5968.robot;

import org.usfirst.frc.team5968.robot.PortMap.CAN;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Launcher implements ILauncher
{

    private TalonSRX launcherMotor;

    private double motorSpeed;
    private static final double HIGH = 0.9;
    private static final double LOW = 0.0;

    public Launcher() {
        launcherMotor = new TalonSRX(PortMap.portOf(CAN.CONVEYER_MOTOR_CONTROLLER));

        stop();
    }

    public void stop() {
        motorSpeed = LOW;
    }

    @Override
    public void pullInCargo() {
        // There may need to be a medium speed depending on how powerful the launcher is.
        motorSpeed = HIGH; 
    }

    @Override
    public void releaseCargo() {
        motorSpeed = HIGH;
    }

    @Override
    public void periodic() {
        launcherMotor.set(ControlMode.PercentOutput, motorSpeed);
    }
}
