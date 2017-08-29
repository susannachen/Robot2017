package org.usfirst.frc.team686.bot.auto.actions;

import org.usfirst.frc.team686.bot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenGearPickup extends Command{
	public OpenGearPickup() {
		requires(Robot.gearpickup);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.gearpickup.open(Robot.oi.getJoystick());
	}
	
	@Override
	protected boolean isFinished() {
		return false; // Runs until interrupted
	}

	@Override
	protected void end() {
		Robot.gearpickup.stop();
	}
}
