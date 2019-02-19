package org.usfirst.frc.team5968.robot;

// import edu.wpi.first.wpilibj.DriverStation;

public class FieldInformation implements IFieldInformation {
    private FieldPosition startingPosition;
    private boolean isDataValid;

    public FieldInformation() {
        refresh();

    }

    @Override
    public FieldPosition getStartingPosition() {
        return startingPosition;

    }

    @Override
    public boolean getIsDataValid() {
        return isDataValid;

    }

    @Override
    public void refresh() {
        isDataValid = false;
        startingPosition = FieldPosition.INVALID;

        // int position = DriverStation.getInstance().getLocation();

    }

    // this is probably wrong
    /* switch (position) {

        case 1: startingPosition = FieldPosition.LEFT; // may be opposite
            break;
        case 2: startingPosition = FieldPosition.CENTER;
            break;
        case 3: startingPosition = FieldPosition.RIGHT; // may be opposite
            break;
        default: startingPosition = FieldPosition.INVALID;
            break;

    } */

}
