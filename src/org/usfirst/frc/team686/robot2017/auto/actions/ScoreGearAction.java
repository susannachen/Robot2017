package org.usfirst.frc.team686.robot2017.auto.actions;


import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;
import org.usfirst.frc.team686.robot2017.lib.util.DataLogger;
import org.usfirst.frc.team686.robot2017.subsystems.Drive;
import org.usfirst.frc.team686.robot2017.subsystems.GearPickup;

import edu.wpi.first.wpilibj.Timer;

/**
 * Action to score a gear on a peg 
 * To use this Action, call runAction(new ScoreGearAction())
 */
public class ScoreGearAction implements Action {

    private double mTimeToBackup = 0.5;
    private double mStartTime;

	Drive drive = Drive.getInstance();			
	GearPickup gearPickup = GearPickup.getInstance();
    
    public ScoreGearAction() {}

    @Override
    public boolean isFinished() {
        return (Timer.getFPGATimestamp() - mStartTime) >= mTimeToBackup;
    }

    @Override
    public void update() {
    	// nothing to do but wait for backup timer to expire
    }

    @Override
    public void done() {

    }

    @Override
    public void start() {
		gearPickup.down();
		gearPickup.outtake();
		drive.setOpenLoop(new DriveCommand(-0.5, -0.5)); //not sure why not negative
    }

    
	private final DataLogger logger = new DataLogger()
    {
        @Override
        public void log()
        {
	    }
    };
	
    public DataLogger getLogger() { return logger; }
    
}
