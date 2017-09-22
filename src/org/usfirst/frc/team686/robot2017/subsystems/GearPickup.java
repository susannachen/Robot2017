package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.Constants;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;


public class GearPickup extends Subsystem {
	private DoubleSolenoid gearRelease;
	private static GearPickup instance = new GearPickup();
	public static GearPickup getInstance() { return instance; }
	
	private final CANTalon intakeMotor;
	
	public GearPickup(){
		gearRelease = new DoubleSolenoid(Constants.kGearPickupSolenoidForwardChannel, Constants.kGearPickupSolenoidReverseChannel);
		intakeMotor = new CANTalon(Constants.kIntakeMotorTalonId);
		intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	}

	public void down() {
		gearRelease.set(DoubleSolenoid.Value.kForward);
	}
	
	public void up() {
		gearRelease.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stop(){
		gearRelease.set(DoubleSolenoid.Value.kOff);
	}
	
	public void intake(){
		intakeMotor.set(-1);
		
	}
	
	public void outtake(){
		intakeMotor.set(1);
	}
	
	public void stopIntake(){
		intakeMotor.set(0);
	}
	
  
	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}
	
}
