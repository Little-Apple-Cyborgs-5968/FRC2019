import edu.wpi.first.wpilibj.DoubleSolenoid;

public class Hook implements IHook {
    
    private DoubleSolenoid piston;
    private PistonState pistonState; 

    
    public Hook (){
        piston = new DoubleSolenoid(0, 1); // change to real ports later
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
    public void periodic() {
        if (pistonState == PistonState.CLOSED) {
            piston.set(DoubleSolenoid.Value.kReverse);
        } else {
            piston.set(DoubleSolenoid.Value.kForward);
        }
    }
}

