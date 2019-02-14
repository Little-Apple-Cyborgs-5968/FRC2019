import edu.wpi.first.wpilibj.DoubleSolenoid;

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
    public void init() {
        
    }
}
