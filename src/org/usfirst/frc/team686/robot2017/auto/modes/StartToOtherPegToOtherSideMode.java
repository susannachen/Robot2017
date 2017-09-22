package org.usfirst.frc.team686.robot2017.auto.modes;

import org.usfirst.frc.team686.robot2017.lib.util.Path;
import org.usfirst.frc.team686.robot2017.lib.util.PathSegment;
import org.usfirst.frc.team686.robot2017.lib.util.Pose;
import org.usfirst.frc.team686.robot2017.lib.util.Util;
import org.usfirst.frc.team686.robot2017.lib.util.Path.Waypoint;

import org.usfirst.frc.team686.robot2017.lib.util.Vector2d;

import java.util.Optional;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.auto.AutoModeBase;
import org.usfirst.frc.team686.robot2017.auto.AutoModeEndedException;
import org.usfirst.frc.team686.robot2017.auto.actions.*;



public class StartToOtherPegToOtherSideMode extends AutoModeBase 
{
	// initialPose in inherited from AutoModeBase
	boolean isBlue;
	FieldDimensions fieldDimensions;
	Path pathToPeg;
	Path pathBackupFromPeg;
	Path pathToFarSide;
	
	
    public StartToOtherPegToOtherSideMode(boolean _isBlue, FieldDimensions _fieldDimensions) 
    {
    	isBlue = _isBlue;
    	fieldDimensions = _fieldDimensions;
		initialPose = fieldDimensions.getOtherStartPose();
    }
    
    private void init()
    {
    	PathSegment.Options pathOptions   = new PathSegment.Options(Constants.kPathFollowingMaxVel, Constants.kPathFollowingMaxAccel, Constants.kPathFollowingLookahead, false);
    	PathSegment.Options visionOptions = new PathSegment.Options(Constants.kVisionMaxVel,        Constants.kVisionMaxAccel,        Constants.kPathFollowingLookahead, true);

		// get positions, based on red/blue alliance
		Pose initialPose = fieldDimensions.getOtherStartPose();
		Vector2d initialPosition = initialPose.getPosition();
		double initialHeading = initialPose.getHeading();
		        
		Pose pegPose = fieldDimensions.getOtherPegBasePose();
		Vector2d pegPosition = pegPose.getPosition();
		double pegHeading = pegPose.getHeading();
		Pose farSidePose = fieldDimensions.getFarSideOfFieldPose();
		Vector2d farSidePosition = farSidePose.getPosition();
		double farSideHeading = farSidePose.getHeading();
		
		
		// where to stop to score gear
		Vector2d v = Vector2d.magnitudeAngle(FieldDimensions.getDistanceToStopFromPeg(), pegHeading);
		Vector2d pegStopPosition = pegPosition.add(v);

		// where to turn towards peg
		Optional<Vector2d> intersection = Util.getLineIntersection(initialPose, pegPose);
		Vector2d pegTurnPosition;
		if (intersection.isPresent())
			pegTurnPosition = intersection.get();
		else
			pegTurnPosition = pegStopPosition;
		
		// where to backup to after scoring gear
		double distanceToTurnFromPeg = 60;
		v = Vector2d.magnitudeAngle(distanceToTurnFromPeg, pegHeading);
		Vector2d backupTurn = pegPosition.add(v);
		double distanceToBackUpFromTurn = 24;
    	double backupDirection = +Math.PI/2;		// Red: turn right while backing up
    	if (isBlue) {
    		backupDirection = -Math.PI/2;			// Blue: turn left while backing up
    	}
    	v = Vector2d.magnitudeAngle(distanceToBackUpFromTurn, pegHeading + backupDirection);
		Vector2d backupPosition = backupTurn.add(v);
				
		
		
		// define path to peg
		pathToPeg = new Path();
		pathToPeg.add(new Waypoint(initialPosition, 	pathOptions));
		pathToPeg.add(new Waypoint(pegTurnPosition,     visionOptions));	// enable vision
		pathToPeg.add(new Waypoint(pegStopPosition, 	visionOptions));	// enable vision
		
		
		// backup away from peg, turn front towards boiler
		pathBackupFromPeg = new Path();
		pathBackupFromPeg.add(new Waypoint(pegStopPosition, pathOptions));
		pathBackupFromPeg.add(new Waypoint(backupTurn, 		pathOptions));
		pathBackupFromPeg.add(new Waypoint(backupPosition, 	pathOptions));
		pathBackupFromPeg.setReverseDirection();									// drive in reverse to backup
		
		// define path to far side 
		pathToFarSide = new Path();
		pathToFarSide.add(new Waypoint(backupPosition, 	pathOptions));
		pathToFarSide.add(new Waypoint(farSidePosition, 	pathOptions));

	}

    // called by AutoModeExecuter.start() --> AutoModeBase.run()
    @Override
    protected void routine() throws AutoModeEndedException 
    {
    	System.out.println("Starting StartToOtherPegToOtherSideMode");

    	 
    	init();																// generate paths
    	
   		runAction( new PathFollowerWithVisionAction( pathToPeg ) );			// drive to peg   
    	runAction( new ScoreGearAction() );	    							// score gear
    	
   		runAction( new PathFollowerWithVisionAction( pathBackupFromPeg ) );	// backup from peg
    	runAction( new PathFollowerWithVisionAction( pathToFarSide ) ); 	// drive to far side
   	
    }
    
}
