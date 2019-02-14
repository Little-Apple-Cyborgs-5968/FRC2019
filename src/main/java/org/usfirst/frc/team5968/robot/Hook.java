package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Hook implements IHook {

    private DoubleSolenoid piston;
    private PistonState pistonState;
    private Compressor compressor;


    public Hook (){
        compressor = new Compressor(PortMap.portOf(PortMap.CAN.PCM));
        compressor.setClosedLoopControl(true);
        piston = new DoubleSolenoid(2,3);
        pistonState = PistonState.OPEN;
    }

    @Override
    public void grabPanel() {
        pistonState = PistonState.CLOSED;
    }

    @Override
    public void releasePanel() {
        pistonState = PistonState.OPEN;
    }

    @Override
    public void toggleGrabbing() {
        if (pistonState == PistonState.CLOSED) {
            releasePanel();
        } else {
            grabPanel();
        }
    }

    @Override
    public void periodic() {
        if (pistonState == PistonState.CLOSED) {
            piston.set(DoubleSolenoid.Value.kReverse);
        } else {
            piston.set(DoubleSolenoid.Value.kForward);
        }
    }

    @Override
    public void init() {

    }
}
