package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.loop.DriveLoop;

import edu.wpi.first.wpilibj.DoubleSolenoid;



public class GearShift extends Subsystem {
	
	private static GearShift instance = new GearShift();
	public static GearShift getInstance() { return instance; }
	
	private final DoubleSolenoid gearSolenoid;
	DriveLoop driveLoop = DriveLoop.getInstance();
	
	
	public GearShift(){
		gearSolenoid = new DoubleSolenoid(0, Constants.kGearShiftSolenoidForwardChannel, Constants.kGearShiftSolenoidReverseChannel);
	}

	public void setHighGear() {
		gearSolenoid.set(DoubleSolenoid.Value.kForward);
		// TODO: set isHighGear in DriveCmd, set currentLimit in DriveLoop 
		driveLoop.enableCurrentLimit(true);	// temporary fix for brownouts
	}
	
	public void setLowGear() {
		gearSolenoid.set(DoubleSolenoid.Value.kReverse);
		driveLoop.enableCurrentLimit(false);	// temporary fix for brownouts
	}
	
	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
