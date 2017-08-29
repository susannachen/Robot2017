package org.usfirst.frc.team686.bot.subsystems;

import org.usfirst.frc.team686.bot.Constants;
import org.usfirst.frc.team686.bot.Robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team686.bot.auto.actions.OpenGearPickup;
import org.usfirst.frc.team686.bot.auto.actions.ReleaseGear;

public class GearPickup extends Subsystem {
	private DoubleSolenoid gearRelease;
	
	public GearPickup(){
		super();
		
		gearRelease = new DoubleSolenoid(Constants.kSolenoidForwardChannel, Constants.kSolenoidReverseChannel);
		
		LiveWindow.addActuator("GearPickup", "gearRelease", (DoubleSolenoid)gearRelease);
	}
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new OpenGearPickup());
	}
	
	public void open(Joystick joy) {
		if(joy.getRawButton(Constants.kXboxButtonB)){gearRelease.set(DoubleSolenoid.Value.kForward);}
	}
	
	public void close() {
		gearRelease.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stop(){
		gearRelease.set(DoubleSolenoid.Value.kOff);
	}
	
	public void log(){
		SmartDashboard.putString("gearRelease", gearRelease.getSmartDashboardType());
	}
	
}
