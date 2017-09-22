package org.usfirst.frc.team686.robot2017.auto.modes;

import org.usfirst.frc.team686.robot2017.lib.util.Path;
import org.usfirst.frc.team686.robot2017.lib.util.PathSegment;
import org.usfirst.frc.team686.robot2017.lib.util.Pose;
import org.usfirst.frc.team686.robot2017.lib.util.Util;
import org.usfirst.frc.team686.robot2017.lib.util.Path.Waypoint;

import org.usfirst.frc.team686.robot2017.lib.util.Vector2d;

import java.util.Arrays;
import java.util.Optional;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.auto.AutoModeBase;
import org.usfirst.frc.team686.robot2017.auto.AutoModeEndedException;
import org.usfirst.frc.team686.robot2017.auto.actions.*;



public class StartToHopperToBoilerMode extends AutoModeBase 
{
	// initialPose in inherited from AutoModeBase
	boolean isBlue;
	FieldDimensions fieldDimensions;
	Path pathToHopper;
	Path pathToOpenTray;
	Path pathToBoiler;
	
	
    public StartToHopperToBoilerMode(boolean _isBlue, FieldDimensions _fieldDimensions) 
    {
    	isBlue = _isBlue;
    	fieldDimensions = _fieldDimensions;
    }
    
    private void init()
    {
    	PathSegment.Options pathOptions   = new PathSegment.Options(Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel, Constants.kPathFollowingLookahead, false);
    	PathSegment.Options tightTurnOptions   = new PathSegment.Options(Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel, 12, false);	// turn radius of 12" instead of normal 24"


		// get positions, based on red/blue alliance
		Pose initialPose = fieldDimensions.getBoilerStartPose();
		Vector2d initialPosition = initialPose.getPosition();
		double initialHeading = initialPose.getHeading();
		        
		Pose hopperPose = fieldDimensions.getBoilerHopperPose();
		Vector2d hopperPosition = hopperPose.getPosition();
		double hopperHeading = hopperPose.getHeading();
		Pose boilerPose = fieldDimensions.getBoilerPose();
		Vector2d boilerPosition = boilerPose.getPosition();
		double boilerHeading = boilerPose.getHeading();
		
		
		// where to turn towards hopper
		Vector2d hopperTurnPosition1 = new Vector2d(100,  -70);
		Vector2d hopperTurnPosition2 = new Vector2d(150, -100);
		if (isBlue)
		{
			// negate y coordinate
			hopperTurnPosition1 = hopperTurnPosition1.conj();
			hopperTurnPosition2 = hopperTurnPosition2.conj();
		}
		
		// hopper contact point
		Vector2d hopperContactPosition = new Vector2d(Constants.kCenterToFrontBumper, +Constants.kCenterToSideBumper);
		if (isBlue) {
			hopperContactPosition = hopperContactPosition.conj();
		}
		hopperContactPosition = hopperContactPosition.rotate(hopperHeading);
		hopperContactPosition = hopperContactPosition.add(hopperPosition);

		double y = -120;
		if (isBlue)
			y = -y;
		Optional<Vector2d> intersection = Util.getLineIntersection(new Pose(0,y,0), new Pose(hopperContactPosition, hopperHeading));
		Vector2d hopperTurnPosition3 = intersection.get();
		
		// location to stop and collect balls
		double distanceContactToCollect = 30;
		Vector2d v = Vector2d.magnitudeAngle(distanceContactToCollect, Math.PI);
		Vector2d hopperCollectPosition = hopperContactPosition.add(v);

		// where to turn towards boiler
		double distanceToTurnFromBoiler = 50;
		v = Vector2d.magnitudeAngle(distanceToTurnFromBoiler, boilerHeading);
		Vector2d boilerTurnPosition = boilerPosition.add(v);

		// where to open ball tray
		double distanceToOpenTrayFromBoiler = 4 + Constants.kCenterToFrontBumper;
		v = Vector2d.magnitudeAngle(distanceToOpenTrayFromBoiler, boilerHeading);
		Vector2d boilerOpenPosition = boilerPosition.add(v);
		
		// where to stop in front of boiler
		double distanceToStopFromBoiler = 2 + Constants.kCenterToFrontBumper;
		v = Vector2d.magnitudeAngle(distanceToStopFromBoiler, boilerHeading);
		Vector2d boilerStopPosition = boilerPosition.add(v);
		
		
		
		// define path to peg
		Path pathToHopper = new Path();
		pathToHopper.add(new Waypoint(initialPosition, 		 pathOptions));
		pathToHopper.add(new Waypoint(hopperTurnPosition1,   pathOptions));
		pathToHopper.add(new Waypoint(hopperTurnPosition2,   pathOptions));
		pathToHopper.add(new Waypoint(hopperTurnPosition3,   pathOptions));
		pathToHopper.add(new Waypoint(hopperContactPosition, pathOptions));
		pathToHopper.add(new Waypoint(hopperCollectPosition, pathOptions));
		
		
		// define path to boiler (just before, where we open the tray)
		pathToOpenTray = new Path();
		pathToOpenTray.add(new Waypoint(hopperCollectPosition, 	tightTurnOptions));
		pathToOpenTray.add(new Waypoint(boilerTurnPosition, 	tightTurnOptions));
		pathToOpenTray.add(new Waypoint(boilerOpenPosition, 	tightTurnOptions));
		
		
		// define path to boiler 
		pathToBoiler = new Path();
		pathToBoiler.add(new Waypoint(boilerOpenPosition, 	pathOptions));
		pathToBoiler.add(new Waypoint(boilerStopPosition, 	pathOptions));
	}

    // called by AutoModeExecuter.start() --> AutoModeBase.run()
    @Override
    protected void routine() throws AutoModeEndedException 
    {
    	System.out.println("Starting StartToHopperToBoilerMode");

   	 
    	init();																// generate paths
    	
   		runAction( new PathFollowerWithVisionAction( pathToHopper ) );		// drive to hopper   
    	runAction( new WaitAction(3.0) );	    							// wait to collect balls
    	
   		runAction( new PathFollowerWithVisionAction( pathToOpenTray ) );    // drive to boiler

        runAction(new ParallelAction(Arrays.asList(
        		new OpenBallTrayAction(),									// open ball tray, while 
        		new PathFollowerWithVisionAction(pathToBoiler) )));			// finishing drive to boiler
   		
    	// finish here, with ball tray left open in front of boiler
    	// ball tray will close when teleop starts (if driver isn't pushing button)
    }
    
}
