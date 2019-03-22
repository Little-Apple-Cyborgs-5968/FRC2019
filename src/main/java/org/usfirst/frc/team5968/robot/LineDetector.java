package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class LineDetector implements ILineDetector {

    private AnalogInput analogInput;

    private static final double LINE_THRESHOLD = 3.5;

    public LineDetector() {
        analogInput = new AnalogInput(0);
    }

    @Override
    public boolean isOnLine() {

        if (getRawValue() > LINE_THRESHOLD) {
            return true;
        } else {
            return false;
        }

    }

    @Override
    public double getRawValue() {
        return analogInput.getVoltage();
    }

}
