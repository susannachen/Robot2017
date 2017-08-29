/*package org.usfirst.frc.team686.bot.auto.actions;

import org.usfirst.frc.team686.bot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveStraight extends Command{
	public DriveStraight() {
		requires(Robot.drivetrain);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.drivetrain.lMotorMaster.set();
	}
	
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	@Override
	protected void end() {
		Robot.drivetrain.lMotorMaster.set(0.0);
		Robot.drivetrain.rMotorMaster.set(0.0);
	}
}
*/