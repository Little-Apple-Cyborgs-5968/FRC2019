package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class CargoGuide implements ICargoGuide {

    private DoubleSolenoid guidePiston;

    public CargoGuide() {
        guidePiston = new DoubleSolenoid(0, 1);
    }

    @Override
    public void init() {
        disengageGuide();
    }

    @Override
    public void engageGuide() {
        guidePiston.set(DoubleSolenoid.Value.kForward);
    }

    @Override
    public void disengageGuide() {
        guidePiston.set(DoubleSolenoid.Value.kReverse);
    }

}
