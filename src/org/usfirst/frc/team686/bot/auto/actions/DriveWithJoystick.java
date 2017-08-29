package org.usfirst.frc.team686.bot.auto.actions;

import org.usfirst.frc.team686.bot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveWithJoystick extends Command{
	
	public DriveWithJoystick() {
		requires(Robot.drivetrain);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.drivetrain.drive(Robot.oi.getJoystick());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.drivetrain.drive(0, 0);
	}
}
