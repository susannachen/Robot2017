package org.usfirst.frc.team686.robot2017.auto.actions;

import org.usfirst.frc.team686.robot2017.lib.util.DataLogger;
import org.usfirst.frc.team686.robot2017.lib.util.Path;
<<<<<<< Upstream, based on origin/auto_paths
import org.usfirst.frc.team686.robot2017.lib.util.PathFollowerWithVisionDriveController;
import org.usfirst.frc.team686.robot2017.lib.util.PathFollowerWithVisionDriveController.PathVisionState;
=======
import org.usfirst.frc.team686.robot2017.util.PathFollowerWithVisionDriveController;
import org.usfirst.frc.team686.robot2017.util.PathFollowerWithVisionDriveController.PathVisionState;
>>>>>>> 12a512e commented out LedRelay

/**
 * Action for following a path defined by a Path object.
 * 
 * Serially configures a PathFollower object to follow each path 
 */
public class PathFollowerWithVisionAction implements Action 
{
	PathFollowerWithVisionDriveController driveCtrl;

    public PathFollowerWithVisionAction(Path _path) 
    {
    	driveCtrl = new PathFollowerWithVisionDriveController(_path, PathVisionState.PATH_FOLLOWING);
    }

    public PathFollowerWithVisionDriveController getDriveController() { return driveCtrl; }

    @Override
    public void start() 
    {
		System.out.println("Starting PathFollowerWithVisionAction");
		driveCtrl.start();
    }


    @Override
    public void update() 
    {
    	driveCtrl.update();
	}	
	
	
    @Override
    public boolean isFinished() 
    {
    	return driveCtrl.isFinished();
    }

    @Override
    public void done() 
    {
		System.out.println("Finished PathFollowerWithVisionAction");
		// cleanup code, if any
		driveCtrl.done();
    }

 
    
    
    public DataLogger getLogger() { return driveCtrl.getLogger(); }
}
