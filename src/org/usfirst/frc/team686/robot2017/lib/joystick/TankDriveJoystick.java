package org.usfirst.frc.team686.robot2017.lib.joystick;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;

public class TankDriveJoystick extends JoystickControlsBase{
	
	private static JoystickControlsBase mInstance = new TankDriveJoystick();
	
	public static JoystickControlsBase getInstance(){
		
		return mInstance;
		
	}
	
	public DriveCommand getDriveCommand()
    {
    	double lMotorSpeed = -mStick.getRawAxis(Constants.kXboxLStickYAxis);
        double rMotorSpeed = -mStick.getRawAxis(Constants.kXboxRStickYAxis);
	    
	    DriveCommand signal = new DriveCommand(lMotorSpeed, rMotorSpeed);
	   	    
	    return signal;        
    }

}
