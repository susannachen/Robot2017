package org.usfirst.frc.team686.robot2017.auto.modes;

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
public class StartToBoilerPegToBoilerMode extends AutoModeBase 
{
	// initialPose in inherited from AutoModeBase
	FieldDimensions fieldDimensions;
	Path pathToPeg;
	Path pathBackupFromPeg;
	Path pathToOpenTray;
	Path pathToBoiler;
	
	
    public StartToBoilerPegToBoilerMode(FieldDimensions _fieldDimensions) 
    {
    	
    	fieldDimensions = _fieldDimensions;
		initialPose = fieldDimensions.getCenterStartPose();
    }
    
    private void init()
    {
    	PathSegment.Options pathOptions   = new PathSegment.Options(Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel, Constants.kPathFollowingLookahead, false);
    	PathSegment.Options visionOptions = new PathSegment.Options(Constants.kVisionMaxVel,        Constants.kVisionMaxAccel,        Constants.kPathFollowingLookahead, true);


// TODO: UPDATE FOR BOILER SIDE.  THIS IS THE CENTER PEG ROUTINE    	
    	
    		// get position of peg and boiler, based on red/blue alliance
    		Pose pegPose, boilerPose;
    		pegPose = fieldDimensions.getCenterPegBasePose();
    		boilerPose = fieldDimensions.getBoilerPose();
       		
        	// where to stop to score gear
        	Vector2d v = Vector2d.magnitudeAngle(FieldDimensions.getDistanceToStopFromPeg(), pegPose.getHeading());
        	Pose pegStopPosition = pegPose.add(v);

        	// where to backup to after scoring gear
        	double distanceToTurnFromPeg = 50;
        	v = Vector2d.magnitudeAngle(distanceToTurnFromPeg, pegPose.getHeading());
        	Pose backupTurn = pegPose.add(v);
        	double distanceToBackUpFromTurn = 24;
        	v = Vector2d.magnitudeAngle(distanceToBackUpFromTurn, pegPose.getHeading() - Math.PI/2);	// turn left while backing up
        	Pose backupPosition = backupTurn.add(v);
        	
        	// where to turn towards boiler
        	double distanceToTurnFromBoiler = 30;
        	v = Vector2d.magnitudeAngle(distanceToTurnFromBoiler, boilerPose.getHeading());
        	Pose boilerTurnPosition = boilerPose.add(v);

        	// where to open ball tray
        	double distanceToOpenTrayFromBoiler = 4;
        	v = Vector2d.magnitudeAngle(distanceToOpenTrayFromBoiler, boilerPose.getHeading());
        	Pose boilerOpenPosition = boilerPose.add(v);

        	// where to stop in front of boiler
        	double distanceToStopFromBoiler = 2;
        	v = Vector2d.magnitudeAngle(distanceToStopFromBoiler, boilerPose.getHeading());
        	Pose boilerStopPosition = boilerPose.add(v);


        	
        	// define path to peg
        	pathToPeg = new Path();
        	pathToPeg.add(new Waypoint(initialPose.getPosition(), 		pathOptions));
        	pathToPeg.add(new Waypoint(pegStopPosition.getPosition(), 	visionOptions));	// enable vision
    		
    				
        	// backup away from peg, turn front towards boiler
        	pathBackupFromPeg = new Path();
        	pathBackupFromPeg.add(new Waypoint(pegStopPosition.getPosition(), 	pathOptions));
        	pathBackupFromPeg.add(new Waypoint(backupTurn.getPosition(), 		pathOptions));
        	pathBackupFromPeg.add(new Waypoint(backupPosition.getPosition(), 	pathOptions));
        	pathBackupFromPeg.setReverseDirection();													// drive in reverse to backup

        	// define path to boiler (just before, where we open the tray)
        	pathToOpenTray = new Path();
        	pathToOpenTray.add(new Waypoint(backupPosition.getPosition(), 	pathOptions));
    	   	pathToOpenTray.add(new Waypoint(boilerTurnPosition.getPosition(), pathOptions));
    	   	pathToOpenTray.add(new Waypoint(boilerOpenPosition.getPosition(), pathOptions));

    		
        	// define path to boiler 
    	   	pathToBoiler = new Path();
    	   	pathToBoiler.add(new Waypoint(boilerOpenPosition.getPosition(), 	pathOptions));
    	   	pathToBoiler.add(new Waypoint(boilerStopPosition.getPosition(), 	pathOptions));
    	}

        // called by AutoModeExecuter.start() --> AutoModeBase.run()
        @Override
        protected void routine() throws AutoModeEndedException 
        {
        	System.out.println("Starting StartToCenterGear");

        	 
        	init();																// generate paths
        	
       		runAction( new PathFollowerWithVisionAction( pathToPeg ) );			// drive to peg   
        	runAction( new ScoreGearAction() );	    							// score gear
        	
       		runAction( new PathFollowerWithVisionAction( pathBackupFromPeg ) );	// backup from peg
       		runAction( new PathFollowerWithVisionAction( pathToOpenTray ) );    // drive to boiler
        	runAction( new OpenBallTrayAction() );    							// open ball tray
       		runAction( new PathFollowerWithVisionAction( pathToBoiler ) );    	// finish drive to boiler

        	// finish here, with ball tray left open in front of boiler
        	// ball tray will close when teleop starts (if driver isn't pushing button)
        	
        }
        
    }
