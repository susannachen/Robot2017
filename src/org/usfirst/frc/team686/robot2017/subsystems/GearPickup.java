package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class GearPickup extends Subsystem {
	private static GearPickup instance = new GearPickup();
	public static GearPickup getInstance() { return instance; }
	
	private GearCommand gearCmd;
	

	
	public GearPickup(){
		
		//gearCmd = gearCmd.INITIALIZE();
		
	}

	public void down() {
		gearCmd.set(DoubleSolenoid.Value.kForward);
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
