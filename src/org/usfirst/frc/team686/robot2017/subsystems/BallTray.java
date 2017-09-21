package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.Constants;

import edu.wpi.first.wpilibj.DoubleSolenoid;

public class BallTray extends Subsystem {
	
	private static BallTray instance = new BallTray();
	public static BallTray getInstance(){
		return instance;
	}
	
	private DoubleSolenoid ballTray;
	
	public BallTray(){
		super();
		
		ballTray = new DoubleSolenoid(Constants.kBallTraySolenoidForwardChannel, Constants.kBallTraySolenoidReverseChannel);

	} 
	
	
	public void down() {
		ballTray.set(DoubleSolenoid.Value.kForward);
	}
	
	public void up() {
		ballTray.set(DoubleSolenoid.Value.kReverse);
	}
	
	public void stop(){
		ballTray.set(DoubleSolenoid.Value.kOff);
	}
	
	
	public void log(){
		
	}

	@Override
	public void zeroSensors() {
		// TODO Auto-generated method stub
		
	}
	
}
