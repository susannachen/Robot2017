package org.usfirst.frc.team686.robot2017.lib.joystick;

import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand.GearMode;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;

/**
 * An abstract class that forms the base of joystick controls.
 */
public abstract class JoystickControlsBase 
{
    protected final Joystick mStick;

    protected JoystickControlsBase() 
    {
        mStick = new Joystick(0);
    }

    public boolean getButton(int _num) { return mStick.getRawButton(_num); }
    public double getAxis(int _num) { return mStick.getRawAxis(_num); }
    
    

    // DRIVER CONTROLS
    public abstract DriveCommand getDriveCommand();	// mapping from joystick controls to DriveSignal
    
    public abstract GearCommand getGearCommand(GearMode newMode);
    
    
}
