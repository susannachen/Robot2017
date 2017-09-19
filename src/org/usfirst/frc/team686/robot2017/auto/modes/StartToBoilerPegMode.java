package org.usfirst.frc.team686.robot2017.auto.modes;

import java.util.ArrayList;
import java.util.List;

import org.usfirst.frc.team686.robot2017.lib.util.Path;
import org.usfirst.frc.team686.robot2017.lib.util.PathSegment;
import org.usfirst.frc.team686.robot2017.lib.util.Pose;
import org.usfirst.frc.team686.robot2017.lib.util.Path.Waypoint;
import org.usfirst.frc.team686.robot2017.lib.util.Vector2d;
import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.auto.AutoModeBase;
import org.usfirst.frc.team686.robot2017.auto.AutoModeEndedException;
import org.usfirst.frc.team686.robot2017.auto.actions.*;



/**
 * Go over the defenses in the starting configuration, then launch one ball (in
 * the robot at start)
 */
public class StartToBoilerPegMode extends AutoModeBase 
{
	// initialPose in inherited from AutoModeBase
	boolean isRed;
	List<Path> pathListToPeg;
	
	
    public StartToBoilerPegMode(boolean _isRed) 
    {
    	isRed = _isRed;
    	
    	if (isRed)
    		initialPose = FieldDimensions.kCenterStartPositionRed;
    	else
    		initialPose = FieldDimensions.kCenterStartPositionBlue;
    }
    
    private void init()
    {
    	PathSegment.Options pathOptions   = new PathSegment.Options(Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel, Constants.kPathFollowingLookahead, false);
    	PathSegment.Options visionOptions = new PathSegment.Options(Constants.kVisionMaxVel,        Constants.kVisionMaxAccel,        Constants.kPathFollowingLookahead, true);


		// get position of peg and boiler, based on red/blue alliance
		Pose pegPose, boilerPose;
    	if (isRed)
    	{
    		pegPose = FieldDimensions.kCenterPegBaseRed;
    		boilerPose = FieldDimensions.kBoilerPoseRed;
    	}
    	else
    	{
    		pegPose = FieldDimensions.kCenterPegBaseBlue;
    		boilerPose = FieldDimensions.kBoilerPoseBlue;
    	}
   		
    	// where to stop to score gear
    	Vector2d v = Vector2d.magnitudeAngle(FieldDimensions.distanceToStopFromPeg, pegPose.getHeading());
    	Pose pegStopPosition = pegPose.add(v);


    	
		pathListToPeg = new ArrayList<Path>();
    	
    	// define path to peg
    	Path path = new Path();
    	//path.add(new Waypoint(initialPose.getPosition(), 		pathOptions));
    	//path.add(new Waypoint(pegStopPosition.getPosition(), 	visionOptions));	// enable vision
    	path.add(new Waypoint(new Vector2d(Constants.kCenterToRearBumper, 0.0), pathOptions));
    	path.add(new Waypoint(new Vector2d(120.0, 0.0), pathOptions));
    	double margin = (Constants.kCenterToFrontBumper)/Math.sqrt(2);
    	path.add(new Waypoint(new Vector2d(192.0-margin, 72.0-margin), pathOptions));
		pathListToPeg.add(path);
		
	}

    // called by AutoModeExecuter.start() --> AutoModeBase.run()
    @Override
    protected void routine() throws AutoModeEndedException 
    {
    	System.out.println("Starting StartToCenterGear");

    	// generate pathList 
    	init();

    	// drive to peg
    	for (Path path : pathListToPeg)
    		runAction( new PathFollowerWithVisionAction( path ) );   
    		
    	runAction( new WaitAction(0.5) );
    	// score gear
    	runAction( new ScoreGearAction() );   
    	
    	
    }
    
}
