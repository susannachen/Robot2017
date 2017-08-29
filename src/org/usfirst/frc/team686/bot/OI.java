package org.usfirst.frc.team686.bot;


import org.usfirst.frc.team686.bot.auto.actions.CloseGearPickup;
import org.usfirst.frc.team686.bot.auto.actions.OpenGearPickup;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class OI {
	
	private Joystick joy = new Joystick(Constants.kJoystickPort); //TODO: put in constructor
	
	public OI() {
		SmartDashboard.putData("Open Gear Pickup", new OpenGearPickup());
		SmartDashboard.putData("Open Gear Pickup", new CloseGearPickup());
		// Create some buttons
		JoystickButton left_trigger = new JoystickButton(joy, Constants.kXboxLTriggerAxis);
		JoystickButton right_trigger = new JoystickButton(joy, Constants.kXboxRTriggerAxis);

		// Connect the buttons to commands
		left_trigger.whenPressed(new OpenGearPickup());
		right_trigger.whenPressed(new CloseGearPickup());
	}

	public Joystick getJoystick() {
		return joy;
	}
	
}
