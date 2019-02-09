/*package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class CargoGuide implements ICargoGuide {

    private DoubleSolenoid guidePiston;
    private PistonState pistonState; 

    public CargoGuide() {

        guidePiston = new DoubleSolenoid(2, 3); // add real channels later
        pistonState = PistonState.OPEN;

    }

    @Override
    public void engageGuide() {
        grabberPiston.set(DoubleSolenoid.Value.kForward);
    }

    @Override
    public void disengageGuide() {
        grabberPiston.set(DoubleSolenoid.Value.kReverse);
    }

}
*/ 