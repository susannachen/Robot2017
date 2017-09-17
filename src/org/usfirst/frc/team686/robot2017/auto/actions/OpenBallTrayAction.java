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
public class OpenBallTrayAction implements Action {

	//BallTray ballTray = BallTray.getInstance();
	//(or just use a double solenoid) 
    
    public OpenBallTrayAction() {}

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void update() {
     }

    @Override
    public void done() {

    }

    @Override
    public void start() {
		//ballTray.down();
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
