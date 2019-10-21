package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Compressor;

public class Hook implements IHook {

    private DoubleSolenoid piston;

    public Hook (){
        piston = new DoubleSolenoid(3, 2);
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
