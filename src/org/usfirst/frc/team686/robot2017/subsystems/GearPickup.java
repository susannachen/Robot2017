package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.command_status.GearCommand;
import org.usfirst.frc.team686.robot2017.command_status.GearCommand.GearMode;

public class GearPickup extends Subsystem {

	private static GearPickup instance = new GearPickup();
	public static GearPickup getInstance() { return instance; }
	
	private GearCommand gearCmd;
	
	//private final CANTalon intakeMotor;
	
	public GearPickup(){
		
		gearCmd = GearCommand.DEFAULT();
	}
	
	public void setLoop (GearCommand cmd){
		gearCmd.setGearMode(GearMode.DEFAULT);
		gearCmd.setGearMode(cmd.getGearMode());
	}
	
	public void setCommand(GearCommand cmd) { gearCmd = cmd; }
	public GearCommand getCommand() {return gearCmd;}


	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub
		
	}
	
}
