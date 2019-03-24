package org.usfirst.frc.team5968.robot;

public class Compressor implements ICompressor {
    private edu.wpi.first.wpilibj.Compressor compressor;
    
    public Compressor() {
        compressor = new edu.wpi.first.wpilibj.Compressor(PortMap.CAN.PCM);
    }

    public void enable() {
        compressor.start();
    }

    public void disable() {
        compressor.stop();
    }

    public void init() {
        enable();
    }
}