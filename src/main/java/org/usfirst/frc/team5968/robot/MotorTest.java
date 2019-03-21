package org.usfirst.frc.team5968.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Joystick;

public class MotorTest implements IRobotMode
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
                10, 3, 6, 4, 5, 7
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
    }
