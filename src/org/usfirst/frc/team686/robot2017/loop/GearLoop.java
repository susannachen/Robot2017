package org.usfirst.frc.team686.robot2017.loop;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;
import org.usfirst.frc.team686.robot2017.command_status.DriveStatus;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand;
import org.usfirst.frc.team686.robot2017.lib.sensors.BNO055;
import org.usfirst.frc.team686.robot2017.subsystems.Drive;
import org.usfirst.frc.team686.robot2017.subsystems.GearPickup;

import com.ctre.CANTalon;
import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;


/*
 * GearLoop is the interface between Drive.java and the actual hardware.
 * It runs periodically, taking the commands sent by Drive and sending them to the hardware.
 * In this way, Drive.java does not access the hardware directly.  The benefits of this partition are: 
 * 1) Changes to drive hardware only requires changes to GearLoop, not Drive
 * 2) GearLoop can be easily replaced for simulation purposes.
 */

public class GearLoop implements Loop 
{
	private static GearLoop instance = new GearLoop();
	public static GearLoop getInstance() { return instance; }
	
    private static GearPickup gearpickup;
    
    private DoubleSolenoid gearRelease;
    
    private final CANTalon intakeMotor;
    
    
    
	private GearLoop() 
	{
		gearpickup = GearPickup.getInstance();
	
		gearRelease = new DoubleSolenoid(Constants.kGearPickupSolenoidReverseChannel, Constants.kGearPickupSolenoidForwardChannel);
		
		intakeMotor = new CANTalon(Constants.kIntakeMotorTalonId);
		intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

		// Set initial settings
		GearCommand defaultCmd = GearCommand.DEFAULT();
	
	}



	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onLoop() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		
	}
	
	



};
