package org.usfirst.frc.team686.robot2017.subsystems;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.lib.util.Util;

import com.ctre.CANTalon;

public class Climber extends Subsystem {
	
	private static Climber instance = new Climber();
	public static Climber getInstance(){
		return instance;
	}
	
	private CANTalon climbMotor;
	
	public Climber(){
		super();
		
		climbMotor = new CANTalon(Constants.kClimbMotorTalonId);  
		climbMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

	} 
	
	
	public void climb(double speed) {
		//double rotateValue = Util.limit(speed, 1.0);
		climbMotor.set(speed);
	}
	
	
	public void log(){
		
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
