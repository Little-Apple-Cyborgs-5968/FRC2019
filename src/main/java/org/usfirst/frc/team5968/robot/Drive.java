import java.lang.Object;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;

public class Drive implements IDrive{
    
    private GenericHID xbox;
    private SendableBase encoder;

    private TalonSRX leftMotorControllerLead;
    private TalonSRX leftMotorControllerFollow;
    private TalonSRX rightMotorControllerLead;
    private TalonSRX rightMotorControllerFollow;
    private TalonSRX middleMotorControllerLead;
    private TalonSRX middleMotorControllerFollow;


    @Override
    public void driveManual(){

        double xbox = new XboxController(XBOXPORT);
        double navX = new AHRS(AHRSPORT);

        double leftMotorControllerLead = new TalonSRX(LEFT_MOTOR_CONTROLLER_LEAD_PORT);
        double leftMotorControllerFollow = new TalonSRX(LEFT_MOTOR_CONTROLLER_FOLLOW_PORT);
        double rightMotorControllerLead = new TalonSRX(RIGHT_MOTOR_CONTROLLER_LEAD_PORT);
        double rightMotorControllerFollow = new TalonSRX(RIGHT_MOTOR_CONTROLLER_FOLLOW_PORT);
        double middleMotorControllerLead = new TalonSRX(MIDDLE_MOTOR_CONTROLLER_LEAD_PORT);
        double middleMotorControllerFollow = new TalonSRX(MIDDLE_MOTOR_CONTROLLER_FOLLOW_PORT);

        leftMotorControllerFollow.follow(leftMotorControllerLead);
        rightMotorControllerFollow.follow(rightMotorControllerLead);
        middleMotorControllerFollow.follow(middleMotorControllerLead);

        double xleftJoystick = xbox.getX(kLeft);
        double yleftJoystick = xbox.getY(kLeft);
        double robotSpeed = Math.sqrt(Math.pow(xleftJoystick, 2) + Math.pow(yleftJoystick, 2));

        double robotAngle = navX.getYaw();
        double joyStickAngle = Math.atan(xleftJoystick / yleftJoystick);
        double robotDriveAngle = joyStickAngle + robotAngle;    //angle relative to robot

        double robotXDriveDirection = 1 / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));
        double robotYDriveDirection = Math.tan(robotDriveAngle) / Math.sqrt(1 + Math.pow(Math.tan(robotDriveAngle), 2));

        leftMotorControllerLead.set(robotSpeed * robotYDriveDirection);
        rightMotorControllerLead.set(robotSpeed * robotYDriveDirection);
        middleMotorControllerLead.set(robotSpeed * robotXDriveDirection);
    
    }

}