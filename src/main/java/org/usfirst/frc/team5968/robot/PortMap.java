package org.usfirst.frc.team5968.robot;

public class PortMap {
    
    public enum USB {
        XBOXCONTROLLER
    }
    
    public enum CAN {
        LEFT_MOTOR_CONTROLLER_LEAD,
        LEFT_MOTOR_CONTROLLER_FOLLOW,
        RIGHT_MOTOR_CONTROLLER_LEAD,
        RIGHT_MOTOR_CONTROLLER_FOLLOW,
        MIDDLE_MOTOR_CONTROLLER_LEAD,
        MIDDLE_MOTOR_CONTROLLER_FOLLOW,
        CONVEYER_MOTOR_CONTROLLER,
        PCM
    }
    
    public static int portOf(USB usbDevice) {
        switch(usbDevice) {
        case XBOXCONTROLLER:
            return 0;
        default:
            return -1;
        }
    }
    
    public static int portOf(CAN canDevice) {
        switch(canDevice) {
        case LEFT_MOTOR_CONTROLLER_LEAD:
            return 10;
        case LEFT_MOTOR_CONTROLLER_FOLLOW:
            return 3;
        case RIGHT_MOTOR_CONTROLLER_LEAD:
            return 6;
        case RIGHT_MOTOR_CONTROLLER_FOLLOW:
            return 4;
        case CONVEYER_MOTOR_CONTROLLER: 
            return 2;
        case PCM:
            return 0; 
        case MIDDLE_MOTOR_CONTROLLER_LEAD:
            return 5;
        case MIDDLE_MOTOR_CONTROLLER_FOLLOW:
            return 7;
        default:
            return -1;
        }
    }
    
}
