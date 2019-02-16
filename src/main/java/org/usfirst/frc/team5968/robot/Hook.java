package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Hook implements IHook {

    private DoubleSolenoid piston;
    private Compressor compressor;


    public Hook (){
        compressor = new Compressor(PortMap.CAN.PCM);
        compressor.setClosedLoopControl(true);
        piston = new DoubleSolenoid(2,3);
        releasePanel();
    }

    @Override
    public void grabPanel() {
        piston.set(DoubleSolenoid.Value.kForward);
    }

    @Override
    public void releasePanel() {
        piston.set(DoubleSolenoid.Value.kReverse);
    }

    @Override
    public void init() {
        releasePanel();
    }
}
