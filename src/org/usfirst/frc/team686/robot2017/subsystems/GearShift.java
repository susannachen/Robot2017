package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;



public class GearShift extends Subsystem {
	
	private static GearShift instance = new GearShift();
	public static GearShift getInstance() { return instance; }
	
	private final DoubleSolenoid gearSolenoid;
	
	public GearShift(){
		gearSolenoid = new DoubleSolenoid(0, Constants.kGearShiftSolenoidForwardChannel, Constants.kGearShiftSolenoidReverseChannel);
		setLowGear();
	}

	public void setHighGear() {
		gearSolenoid.set(DoubleSolenoid.Value.kForward);
	}
	
	public void setLowGear() {
		gearSolenoid.set(DoubleSolenoid.Value.kReverse);
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
