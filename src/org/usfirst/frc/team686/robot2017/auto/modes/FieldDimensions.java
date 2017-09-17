package org.usfirst.frc.team686.robot2017.auto.modes;

import org.usfirst.frc.team686.robot2017.Constants;
import org.usfirst.frc.team686.robot2017.lib.util.Pose;

public class FieldDimensions
{
	// origin is along driver station wall in line with center peg

	public static double distanceToStopFromPeg = Constants.kCenterToFrontBumper + 10.0;		// stop 10" from airship wall (5" from base of peg) 
	public static double distXFarSideOfField  = 400;
	public static double kNonCenterPegOffsetX = 17.563;
	public static double kNonCenterPegOffsetY = 30.518;

	
	// RED SIDE LOCATIONS
	
	// Field Measurements
	public static double distXWallToAirshipRed 		= 114.5;		// driver station wall to wall behind center peg 
	public static double distXWallToBoilerHopperRed	= 119.875;		// driver station wall to center of hopper on boiler side
	public static double distXWallToOtherHopperRed	= 207.125;		// driver station wall to center of hopper on boiler side
	
	public static double distYOriginToEdgeofDSWallOnBoilerSideRed 	= 124.875;	// origin to edge of driver station wall closest to boiler 
	public static double distYOriginToEdgeofDSWallOnOtherSideRed 	= 124.875;	// origin to edge of driver station wall opposite the boiler 
	public static double distYOriginToBoilerFieldRed 				= 160.8;	// center of field to wall on boiler side
	public static double distYOriginToOtherFieldWallRed 			= 158.5;	// center of field to wall on other side

	public static double distXOriginToCenterOfBoilerRed	= 18;			// origin to center of boiler 
	public static double distYOriginToCenterOfBoilerRed	= 143.2;		// origin to center of boiler

	
	
	
	// Robot Starting Positions
    public static Pose kCenterStartPositionRed = new Pose(Constants.kCenterToRearBumper, 0, 0);
    public static Pose kBoilerStartPositionRed = new Pose(Constants.kCenterToRearBumper, -distYOriginToEdgeofDSWallOnBoilerSideRed, 0);
    public static Pose kOtherStartPositionRed  = new Pose(Constants.kCenterToRearBumper, +distYOriginToEdgeofDSWallOnOtherSideRed, 0);

    // Peg Locations
	public static double kCenterPegHeadingRed =  180 * Math.PI/180;
	public static double kBoilerPegHeadingRed = -120 * Math.PI/180;
	public static double kOtherPegHeadingRed  = +120 * Math.PI/180;
    public static Pose kCenterPegBaseRed = new Pose(distXWallToAirshipRed,                                            0, kCenterPegHeadingRed);
	public static Pose kBoilerPegBaseRed = new Pose(distXWallToAirshipRed + kNonCenterPegOffsetX, -kNonCenterPegOffsetY, kBoilerPegHeadingRed);
	public static Pose kOtherPegBaseRed  = new Pose(distXWallToAirshipRed + kNonCenterPegOffsetX, +kNonCenterPegOffsetY, kOtherPegHeadingRed);

	// Boiler Location
	public static double kBoilerHeadingRed =  45 * Math.PI/180;
	public static Pose kBoilerPoseRed = new Pose(distXOriginToCenterOfBoilerRed, -distYOriginToCenterOfBoilerRed,    kBoilerHeadingRed);	
	
	// Hopper Locations
	public static double kBoilerHopperHeadingRed =  90 * Math.PI/180;
	public static double kOtherHopperHeadingRed  = -90 * Math.PI/180;
	public static Pose kBoilerHopperPoseRed = new Pose(distXWallToBoilerHopperRed, -distYOriginToBoilerFieldRed,    kBoilerHopperHeadingRed);
	public static Pose kOtherHopperPoseRed  = new Pose(distXWallToOtherHopperRed,  +distYOriginToOtherFieldWallRed, kOtherHopperHeadingRed);

	// Ending point of run to other side of field
	public static Pose kFarSideOfFieldPoseRed = new Pose(distXFarSideOfField, +distYOriginToEdgeofDSWallOnOtherSideRed, 0);

	
	
	
	// BLUE SIDE LOCATIONS
	
	// Field Measurements
	public static double distXWallToAirshipBlue 		= distXWallToAirshipRed;		// driver station wall to wall behind center peg 
	public static double distXWallToBoilerHopperBlue	= distXWallToBoilerHopperRed;		// driver station wall to center of hopper on boiler side
	public static double distXWallToOtherHopperBlue	= distXWallToOtherHopperRed;		// driver station wall to center of hopper on boiler side
	
	public static double distYOriginToEdgeofDSWallOnBoilerSideBlue 	= distYOriginToEdgeofDSWallOnBoilerSideRed;	// origin to edge of driver station wall closest to boiler 
	public static double distYOriginToEdgeofDSWallOnOtherSideBlue 	= distYOriginToEdgeofDSWallOnOtherSideRed;	// origin to edge of driver station wall opposite the boiler 
	public static double distYOriginToBoilerFieldBlue 				= distYOriginToBoilerFieldRed;	// center of field to wall on boiler side
	public static double distYOriginToOtherFieldWallBlue 			= distYOriginToOtherFieldWallRed;	// center of field to wall on other side

	public static double distXOriginToCenterOfBoilerBlue	= distXOriginToCenterOfBoilerRed;			// origin to center of boiler 
	public static double distYOriginToCenterOfBoilerBlue	= distYOriginToCenterOfBoilerRed;		// origin to center of boiler

	
	
	
	// Robot Starting Positions
    public static Pose kCenterStartPositionBlue = new Pose(Constants.kCenterToRearBumper, 0, 0);
    public static Pose kBoilerStartPositionBlue = new Pose(Constants.kCenterToRearBumper, +distYOriginToEdgeofDSWallOnBoilerSideBlue, 0);	// same as red, but negate Y
    public static Pose kOtherStartPositionBlue  = new Pose(Constants.kCenterToRearBumper, -distYOriginToEdgeofDSWallOnOtherSideBlue, 0);	// same as red, but negate Y

    // Peg Locations
	public static double kCenterPegHeadingBlue =  180 * Math.PI/180;
	public static double kBoilerPegHeadingBlue = +120 * Math.PI/180;	// negative of red
	public static double kOtherPegHeadingBlue  = -120 * Math.PI/180;	// negative of red
    public static Pose kCenterPegBaseBlue = new Pose(distXWallToAirshipBlue,                                            0, kCenterPegHeadingBlue);
	public static Pose kBoilerPegBaseBlue = new Pose(distXWallToAirshipBlue + kNonCenterPegOffsetX, +kNonCenterPegOffsetY, kBoilerPegHeadingBlue);	// same as red, but negate Y
	public static Pose kOtherPegBaseBlue  = new Pose(distXWallToAirshipBlue + kNonCenterPegOffsetX, -kNonCenterPegOffsetY, kOtherPegHeadingBlue);	// same as red, but negate Y

	// Boiler Location
	public static double kBoilerHeadingBlue =  45 * Math.PI/180;
	public static Pose kBoilerPoseBlue = new Pose(distXOriginToCenterOfBoilerBlue, +distYOriginToCenterOfBoilerBlue,    kBoilerHeadingBlue);		// same as red, but negate Y
	
	// Hopper Locations
	public static double kBoilerHopperHeadingBlue = -90 * Math.PI/180;	// negative of red
	public static double kOtherHopperHeadingBlue  = +90 * Math.PI/180;	// negative of red
	public static Pose kBoilerHopperPoseBlue = new Pose(distXWallToBoilerHopperBlue, +distYOriginToBoilerFieldBlue,    kBoilerHopperHeadingBlue);	// same as red, but negate Y
	public static Pose kOtherHopperPoseBlue  = new Pose(distXWallToOtherHopperBlue,  -distYOriginToOtherFieldWallBlue, kOtherHopperHeadingBlue);	// same as red, but negate Y

	// Ending point of run to other side of field
	public static Pose kFarSideOfFieldPoseBlue = new Pose(distXFarSideOfField, -distYOriginToEdgeofDSWallOnOtherSideBlue, 0);
	
}
