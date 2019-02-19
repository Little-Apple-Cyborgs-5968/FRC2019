package org.usfirst.frc.team5968.robot;

public interface IFieldInformation {

    public FieldPosition getStartingPosition();

    public boolean getIsDataValid();

    public void refresh();

}
