package org.usfirst.frc.team686.robot2017.auto.modes;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.auto.AutoModeBase;
import org.usfirst.frc.team686.robot2017.auto.AutoModeEndedException;
import org.usfirst.frc.team686.robot2017.auto.actions.PathFollowerWithVisionAction;
import org.usfirst.frc.team686.robot2017.lib.util.Path;
import org.usfirst.frc.team686.robot2017.lib.util.Path.Waypoint;
import org.usfirst.frc.team686.robot2017.lib.util.PathSegment;
import org.usfirst.frc.team686.robot2017.lib.util.Vector2d;

/**
 * Go over the defenses in the starting configuration, then launch one ball (in
 * the robot at start)
 */
public class SquarePatternMode extends AutoModeBase {

    public SquarePatternMode(int lane, boolean shouldDriveBack) 
    {
    }

    @Override
    protected void routine() throws AutoModeEndedException 
    {
    	System.out.println("Starting Auto Mode: Square Pattern");


    	PathSegment.Options options = new PathSegment.Options(Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel, Constants.kPathFollowingLookahead, false);
    	
        Path path = new Path();
        path.add(new Waypoint(new Vector2d( 0, 0), options));
        path.add(new Waypoint(new Vector2d( 240.0, 0), options));
        //path.add(new Waypoint(new Vector2d( 96.0, 72.0), options));
        //path.add(new Waypoint(new Vector2d( 0, 72.0), options));
        //path.add(new Waypoint(new Vector2d( 0, 0), options));
  
        //Path revPath = new Path(path);
        //revPath.setReverseOrder();
        //revPath.setReverseDirection();
        
        runAction(new PathFollowerWithVisionAction(path));			// drive forward
        //runAction(new PathFollowerWithVisionAction(revPath));    	// drive reversed 
    }
}
