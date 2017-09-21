package org.usfirst.frc.team686.robot2017.loop;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand.GearMode;
import org.usfirst.frc.team686.robot2017.subsystems.Drive;
import org.usfirst.frc.team686.robot2017.subsystems.GearPickup;

import com.ctre.MotorControl.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;

public class GearLoop implements Loop {
	
	private static GearLoop instance = new GearLoop();
	public static GearLoop getInstance() {return instance;}
	
	private static GearPickup gearPickup;
	private static Drive drive;
	
	public final CANTalon intakeMotor;
	public final DoubleSolenoid gearRelease;
	
	private GearLoop()
	{
		gearPickup = GearPickup.getInstance();
		drive = Drive.getInstance();
		
		intakeMotor = new CANTalon(Constants.kIntakeMotorTalonId);
		intakeMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		gearRelease = new DoubleSolenoid(Constants.kGearShiftSolenoidReverseChannel, Constants.kGearShiftSolenoidForwardChannel);
		
		GearCommand defaultCmd = GearCommand.DEFAULT();
		setControlMode(defaultCmd);
		
		intakeMotor.set(0);
	}
	
	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLoop() {
		sendCommands();
		
	}

	@Override
	public void onStop() {
		stopGearPickup();
		
	}
	
	private void stopGearPickup(){
		gearPickup.setCommand(GearCommand.DEFAULT());
		sendCommands();
	}
	
	private void sendCommands(){
		
		GearCommand newCmd = gearPickup.getCommand();
		
		double currentTime = Timer.getFPGATimestamp();
		if(currentTime - newCmd.getCommandTime() > Constants.kGearOuttakeTimerThreshold){
			stopGearPickup();
			return;
		}
		synchronized(newCmd){
			setControlMode(newCmd);
		}
	}
	
	private void setControlMode(GearCommand newCmd)
	{
		GearMode newMode = newCmd.getGearMode();
		
		switch(newMode)
		{
		case INITIALIZE:
			//forward = down
			gearRelease.set(DoubleSolenoid.Value.kForward);
			intakeMotor.set(0);
			break;
		case DEFAULT:
			gearRelease.set(DoubleSolenoid.Value.kReverse);
			intakeMotor.set(0);
			
		case INTAKE:
			gearRelease.set(DoubleSolenoid.Value.kForward);
			intakeMotor.set(-1);
			break;
		case OUTTAKE:
			gearRelease.set(DoubleSolenoid.Value.kForward);
			intakeMotor.set(1);
			drive.setOpenLoop(new DriveCommand(-0.5, -0.5));
			
			break;
			
		}
	}

	
}
