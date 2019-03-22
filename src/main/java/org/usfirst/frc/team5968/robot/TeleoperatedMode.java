package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

public class TeleoperatedMode implements IRobotMode {

    private XboxController xboxController;
    private IDrive drive;
    private IGyroscopeSensor gyroscope;
    private IHook hook;
    private ILauncher launcher;
    private ICargoGuide cargoGuide;

    private boolean headingIsMaintained = true;

    private static final double LEFT_STICK_TOLERANCE = 0.1;
    private static final double ROTATION_SPEED_THRESHOLD = 0.3;

    private static final double LEFT_STICK_EXPONENT = 1.0;
    private static final double RIGHT_STICK_EXPONENT = 3.0;

    private static final double LINE_ALIGNMENT_SPEED = 0.5;

    private boolean lastUpdateWasPanic = false;

    public TeleoperatedMode(IDrive drive, IHook hook, ILauncher launcher, ICargoGuide cargoGuide, IGyroscopeSensor gyroscope) {

        xboxController = new XboxController(PortMap.USB.XBOXCONTROLLER);

        this.drive = drive;
        this.hook = hook;
        this.launcher = launcher;
        this.cargoGuide = cargoGuide;
        this.gyroscope = gyroscope;
    }

    @Override
    public void init() {
        drive.init();
    }

    @Override
    public void periodic() {
        // Force reset robot orientation when driver holds both center buttons
        boolean playerPanic = xboxController.getBackButton() && xboxController.getStartButton();

        if (playerPanic) {
            if(!lastUpdateWasPanic) {
                Debug.log("Panic-resetting orientation!");
                gyroscope.resetYaw();
            }
            lastUpdateWasPanic = true;

            // Makes sure robot doesn't use old drive state
            drive.stop();

            // Skips controls processing
            return;
        }
        lastUpdateWasPanic = false;

        // Process Linear Motion Controls
        double leftX = xboxController.getX(Hand.kLeft);
        double leftY = -xboxController.getY(Hand.kLeft);
        double leftMagnitude = Math.sqrt(Math.pow(leftX, 2.0) + Math.pow(leftY, 2.0));

        if (leftMagnitude < LEFT_STICK_TOLERANCE) {
            leftX = 0.0;
            leftY = 0.0;

            // Unless the left stick is pushed far enough, no driver controls are processed when the robot is in autonomous mode
            if (drive.getCurrentDriveMode() != DriveMode.DRIVERCONTROL) {
                return;
            }
        }
        else {
            leftX = Math.pow(leftX, LEFT_STICK_EXPONENT);
            leftY = Math.pow(leftY, LEFT_STICK_EXPONENT);
        }

        drive.driveManual(leftX, leftY);

        // Process Angular Motion Controls
        double rightX = xboxController.getX(Hand.kRight);
        double rightY = xboxController.getY(Hand.kRight);
        double angle = (Math.atan2(rightY, rightX) + (Math.PI / 2));
        double rotationSpeed = Math.sqrt(Math.pow(rightX, 2.0) + Math.pow(rightY, 2.0));

        if (rotationSpeed < ROTATION_SPEED_THRESHOLD) {
            if (!headingIsMaintained) {
                drive.maintainHeading();
                headingIsMaintained = true;
            }
        } else {
            rotationSpeed = Math.pow(rotationSpeed, RIGHT_STICK_EXPONENT);
            drive.lookAt(angle, rotationSpeed);
            headingIsMaintained = false;
        }

        // Process Peripheral Controls
        if (xboxController.getBumper(Hand.kRight)) {
            launcher.start();
        } else {
            launcher.stop();
        }

        if (xboxController.getYButton()) {
            hook.grabPanel();
        }

        if (xboxController.getXButton()) {
            hook.releasePanel();
        }

        if (xboxController.getBButton()) {
            cargoGuide.engageGuide();
        }

        if(xboxController.getAButton()) {
            cargoGuide.disengageGuide();
        }

        // Process Line Alignment
        final int RIGHT = 90;
        final int LEFT = 270;

        if (xboxController.getPOV() == LEFT) {
            drive.driveToLine(LINE_ALIGNMENT_SPEED);
        } else if (xboxController.getPOV() == RIGHT) {
            drive.driveToLine(-LINE_ALIGNMENT_SPEED);
        }
    }
}
