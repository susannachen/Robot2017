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
public class StartToCenterGearToBoilerMode extends AutoModeBase 
{
	// initialPose in inherited from AutoModeBase
	boolean isRed;
	List<Path> pathListToPeg;
	List<Path> pathListToOpenTray;
	List<Path> pathListToBoiler;
	
	
    public StartToCenterGearToBoilerMode(boolean _isRed) 
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

    	// where to backup to after scoring gear
    	double distanceToBackUpFromPeg = 50;
    	v = Vector2d.magnitudeAngle(distanceToBackUpFromPeg, pegPose.getHeading());
    	Pose backupPosition = pegPose.add(v);
    	
    	// where to turn towards boiler
    	double distanceToTurnFromBoiler = 30;
    	v = Vector2d.magnitudeAngle(distanceToTurnFromBoiler, boilerPose.getHeading());
    	Pose boilerTurnPosition = boilerPose.add(v);

    	// where to open ball tray
    	double distanceToOpenTrayFromBoiler = 4 + Constants.kCenterToFrontBumper;
    	v = Vector2d.magnitudeAngle(distanceToOpenTrayFromBoiler, boilerPose.getHeading());
    	Pose boilerOpenPosition = boilerPose.add(v);

    	// where to stop in front of boiler
    	double distanceToStopFromBoiler = 2 + Constants.kCenterToFrontBumper;
    	v = Vector2d.magnitudeAngle(distanceToStopFromBoiler, boilerPose.getHeading());
    	Pose boilerStopPosition = boilerPose.add(v);


    	
		pathListToPeg = new ArrayList<Path>();
		pathListToOpenTray = new ArrayList<Path>();
		pathListToBoiler = new ArrayList<Path>();
    	
    	// define path to peg
    	Path path = new Path();
    	path.add(new Waypoint(initialPose.getPosition(), 		pathOptions));
    	path.add(new Waypoint(pegStopPosition.getPosition(), 	visionOptions));	// enable vision
		pathListToPeg.add(path);
		
				
    	// define path to boiler (just before, where we open the tray)
		path = new Path();
	   	path.add(new Waypoint(pegStopPosition.getPosition(), 	pathOptions));
	   	path.add(new Waypoint(backupPosition.getPosition(), 	pathOptions));
		path.setReverseDirection();
		pathListToOpenTray.add(path);

		path = new Path();
	   	path.add(new Waypoint(backupPosition.getPosition(), 	pathOptions));
	   	path.add(new Waypoint(boilerTurnPosition.getPosition(), pathOptions));
	   	path.add(new Waypoint(boilerOpenPosition.getPosition(), pathOptions));
		pathListToOpenTray.add(path);

		
    	// define path to boiler 
		path = new Path();
	   	path.add(new Waypoint(boilerOpenPosition.getPosition(), 	pathOptions));
	   	path.add(new Waypoint(boilerStopPosition.getPosition(), 	pathOptions));
	   	pathListToBoiler.add(path);
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
    		
    	// score gear
    	runAction( new ScoreGearAction() );   
    		
    	// backup from peg, drive to boiler
    	for (Path path : pathListToOpenTray)
    		runAction( new PathFollowerWithVisionAction( path ) );   
    		
    	// open ball tray
    	runAction( new OpenBallTrayAction() );   
    	
    	// finish drive to boiler
    	for (Path path : pathListToBoiler)
    		runAction( new PathFollowerWithVisionAction( path ) );   

    	// finish here, with ball tray left open in front of boiler
    	// ball tray will close when teleop starts (if driver isn't pushing button)
    	
    }
    
}
