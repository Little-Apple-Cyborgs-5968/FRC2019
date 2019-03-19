package org.usfirst.frc.team5968.robot;

import edu.wpi.first.wpilibj.AnalogInput;

public class LineDetector implements ILineDetector {

    private ILineDetector lineDetector;
    private AnalogInput analogInput;

    private static final double LINE_THRESHOLD = 1.45;

    public LineDetector() {
        analogInput = new AnalogInput(0);
        lineDetector = new LineDetector();
    }

    @Override
    public boolean isOnLine() {

        if (lineDetector.getRawValue() > LINE_THRESHOLD) {
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
