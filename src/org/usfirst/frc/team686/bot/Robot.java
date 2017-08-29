package org.usfirst.frc.team686.bot;


import org.usfirst.frc.team686.bot.subsystems.*;

import org.usfirst.frc.team686.bot.subsystems.GearPickup;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends IterativeRobot {
    
    public static DriveTrain drivetrain;
    public static GearPickup gearpickup;
    public static Climb climber;
    public static OI oi;
    
    @Override
    public void robotInit() {
        drivetrain = new DriveTrain();
        gearpickup = new GearPickup();
        climber = new Climb();
        oi = new OI();

		// Show what command your subsystem is running on the SmartDashboard
		SmartDashboard.putData(drivetrain);
		SmartDashboard.putData(gearpickup);
    
    }
	@Override
	public void autonomousInit() {
		//wait until teleop is enabled
		while(isAutonomous()){
			try {
			    Thread.sleep(10);                 
			} catch(InterruptedException ex) {
			    Thread.currentThread().interrupt();
			}
		}
	}

	
	@Override
	public void autonomousPeriodic() {
		//Scheduler.getInstance().run();
		//log();
	}
    
    
	@Override
    public void teleopInit() 
    { 
        //autonomousCommand.cancel();
    }
	
	@Override
    public void teleopPeriodic() 
    { 
		Scheduler.getInstance().run();
		log();
    }
	
	private void log() {
		drivetrain.log();
		gearpickup.log();
	}

    @Override
    public void testPeriodic() 
    {
    	LiveWindow.run();
    }
    
}
