package org.usfirst.frc.team686.bot.subsystems;

import org.usfirst.frc.team686.bot.Constants;
import org.usfirst.frc.team686.bot.auto.actions.Hoist;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Climb extends Subsystem {
	private CANTalon climbMotor;
	
	public Climb(){
		super();
		
		climbMotor = new CANTalon(Constants.kClimbMotorTalonId);  
		climbMotor.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

	} 
	
	@Override
	public void initDefaultCommand() {
		
		setDefaultCommand(new Hoist());
	}
	
	public void go(Joystick joy) {
		if(joy.getRawButton(Constants.kXboxButtonA)){ climbMotor.set(1); }
	}
	
	public void release() {
		climbMotor.set(-1);
	}
	
	public void stop(){
		climbMotor.set(0.0);
	}
	
	public void log(){
		
	}
	
}
