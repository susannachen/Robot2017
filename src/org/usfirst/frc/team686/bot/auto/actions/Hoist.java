package org.usfirst.frc.team686.bot.auto.actions;

import org.usfirst.frc.team686.bot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class Hoist extends Command{
	
	public Hoist() {
		requires(Robot.climber);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		Robot.climber.go(Robot.oi.getJoystick());
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.climber.stop();
	}
}
