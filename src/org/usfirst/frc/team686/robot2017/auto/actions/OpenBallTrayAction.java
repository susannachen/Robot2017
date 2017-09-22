package org.usfirst.frc.team686.robot2017.auto.actions;


import org.usfirst.frc.team686.robot2017.lib.util.DataLogger;
import org.usfirst.frc.team686.robot2017.subsystems.BallTray;

import edu.wpi.first.wpilibj.Timer;

/**
 * Action to open ball tray at low boiler 
 * To use this Action, call runAction(new OpenBallTrayAction())
 */
public class OpenBallTrayAction implements Action {

	BallTray ballTray = BallTray.getInstance();
    private double startTime, elapsedTime;
    private final double durationDownStart = 0.5;
    private final double durationUpPinball = 0.5;
    private final double durationDownFinish = 5.0;
    boolean finished = false;
    
    enum BallTrayState { UP, START, PINBALL, DOWN; }
    BallTrayState state = BallTrayState.UP;
    
    
    public OpenBallTrayAction() {}

    @Override
    public void start() {
    	startTime = Timer.getFPGATimestamp();
    	elapsedTime = 0;
    	state = BallTrayState.START;		// move from the default UP state to START upon start of this action
    	finished = false;
    }
    
    @Override
    public void update() {
    	elapsedTime = Timer.getFPGATimestamp() - startTime;
    	
    	switch (state)
    	{
    	case START:
    		ballTray.down();
    		if (elapsedTime > durationDownStart)
    			state = BallTrayState.PINBALL;
    		break;
    		
    	case PINBALL:
    		ballTray.up();
    		if (elapsedTime > durationDownStart + durationUpPinball)
    			state = BallTrayState.DOWN;
    		break;
    		
    	case DOWN:
    		ballTray.down();
    		if (elapsedTime > durationDownStart + durationUpPinball + durationDownFinish)
    			state = BallTrayState.UP;
    		break;
    		
    	case UP:
    	default:
    		ballTray.up();
    		finished = true;
    		// stay here indefinitely
    		break;
    	}
     }

    @Override
    public boolean isFinished() {
        return finished;
    }

    @Override
    public void done() {
		ballTray.up();
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
