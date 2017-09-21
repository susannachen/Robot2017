package org.usfirst.frc.team686.robot2017;


/**
 * Attribution: adapted from FRC Team 254
 */

import org.usfirst.frc.team686.robot2017.lib.util.ConstantsBase;

import edu.wpi.first.wpilibj.I2C;

/**
 * A list of constants used by the rest of the robot code. This include physics
 * constants as well as constants determined through calibrations.
 */
public class Constants extends ConstantsBase
{
    public static double kLoopDt = 0.01;
    public static double kDriveWatchdogTimerThreshold = 0.500;    
    
    // Bumpers
    public static double kCenterToFrontBumper = 19.0;	// position of front bumper with respect to robot center of rotation
    public static double kCenterToRearBumper = 19.5;	// position of rear bumper with respect to robot center of rotation
    public static double kCenterToSideBumper = 17.5;	// position of side bumper with respect to robot center of rotation
    // Front Bumper
    public static double kFrontBumperX = 21;	// position of front bumper with respect to robot center of rotation
    
    // Wheels
    public static double kDriveWheelCircumInches = 13.229;//13.250;
    public static double kDriveWheelDiameterInches = kDriveWheelCircumInches / Math.PI;
    public static double kTrackLengthInches = 25.000;
    public static double kTrackWidthInches = 23.000;
    public static double kTrackEffectiveDiameter = (kTrackWidthInches * kTrackWidthInches + kTrackLengthInches * kTrackLengthInches) / kTrackWidthInches;
    public static double kTrackScrubFactor = 0.5;

    // Wheel Encoder
    public static double kQuadEncoderGain = ( 30.0 / 54.0 ) * ( 12.0 / 36.0 );	// number of drive shaft rotations per encoder shaft rotation
																				// 54:30 drive shaft --> 3rd stage, 36:12 3rd stage --> encoder shaft 
    
    public static int    kQuadEncoderCodesPerRev = 64;
    public static int    kQuadEncoderPulsesPerRev = (int)(4*kQuadEncoderCodesPerRev / kQuadEncoderGain);
    public static double kQuadEncoderStatusFramePeriod = 0.100;	// 100ms
    
    // CONTROL LOOP GAINS
    public static double kFullThrottleRPM = 4500 * kQuadEncoderGain;	// high gear: measured max RPM using NI web interface
    public static double kFullThrottleEncoderPulsePer100ms = kFullThrottleRPM / 60.0 * kQuadEncoderStatusFramePeriod * kQuadEncoderPulsesPerRev; 
    
    
    // PID gains for drive velocity loop (sent to Talon)
    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    public static double kDriveVelocityKp = 1.0;
    public static double kDriveVelocityKi = 0.001;
    public static double kDriveVelocityKd = 6.0;
    public static double kDriveVelocityKf = 1023.0 / kFullThrottleEncoderPulsePer100ms;
    public static int    kDriveVelocityIZone = 0;
    public static double kDriveVelocityRampRate = 0.0;
    public static int    kDriveVelocityAllowableError = 0;

    // PID gains for drive base lock loop
    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    public static double kDriveBaseLockKp = 0.5;
    public static double kDriveBaseLockKi = 0;
    public static double kDriveBaseLockKd = 0;
    public static double kDriveBaseLockKf = 0;
    public static int    kDriveBaseLockIZone = 0;
    public static double kDriveBaseLockRampRate = 0;
    public static int    kDriveBaseLockAllowableError = 10;

    // PID gains for constant heading velocity control
    // Units: Error is degrees. Output is inches/second difference to
    // left/right.
    public static double kDriveHeadingVelocityKp = 4.0;
    public static double kDriveHeadingVelocityKi = 0.0;
    public static double kDriveHeadingVelocityKd = 50.0;
    
    // Point Turn constants
    public static double kPointTurnMaxVel    = 80.0; // inches/sec  		
    public static double kPointTurnMaxAccel  = 200.0; // inches/sec^2	
    public static double kPointTurnMinSpeed  = 20.0; // inches/sec 
    public static double kPointTurnCompletionTolerance = 1.0 * (Math.PI/180.0); 
    
    // Path following constants
    public static double kPathFollowingMaxVel    = 60.0; // inches/sec  		
    public static double kPathFollowingMaxAccel  = 48.0; // inches/sec^2	
    public static double kPathFollowingLookahead = 24.0; // inches
    public static double kPathFollowingCompletionTolerance = 1.0; 
    
    // Vision constants
    public static double kCameraPoseX     = +7.25;	// camera location with respect to robot center of rotation, +X axis is in direction of travel
    public static double kCameraPoseY     =     0;	// camera location with respect to robot center of rotation, +Y axis is positive to the left
    public static double kCameraPoseTheta =     0;	// camera angle with respect to robot heading
    
    public static double kVisionMaxVel    = 60.0; // inches/sec  		
    public static double kVisionMaxAccel  = 48.0; // inches/sec^2		
    public static double kTargetWidthInches = 10.25;
    public static double kPegTargetDistanceThresholdFromBumperInches = 18;		// inches to stop from target, measured from front bumper
    public static double kPegTargetDistanceThresholdFromCameraInches = kCenterToFrontBumper - kCameraPoseX + kPegTargetDistanceThresholdFromBumperInches;
    public static double kVisionCompletionTolerance = 1.0; 
    public static double kVisionMaxDistanceInches = 240;		// ignore targets greater than this distance
    public static double kVisionLookaheadDist = 24.0;	// inches
    public static double kCameraFOVDegrees = 42.5;			// Camera Field of View (degrees)
    public static double kCameraHalfFOVRadians = kCameraFOVDegrees/2.0 * Math.PI/180.0;			// Half of Camera Field of View (radians)
    public static double kTangentCameraHalfFOV = Math.tan(kCameraHalfFOVRadians);
    public static double kCameraLatencySeconds = 0.240;			// Camera image capturing latency
    public static double kTargetLocationFilterConstant = (30.0 * kLoopDt);		// 30 time constants in 1 second
    
    
    
    
    // Do not change anything after this line!
    
    // Motor Controllers
    // (Note that if multiple Talons are dedicated to a mechanism, any sensors are attached to the master)
    public static int kLeftMotorMasterTalonId = 1;
	public static int kLeftMotorSlave1TalonId = 2;
	public static int kLeftMotorSlave2TalonId = 3;
	public static int kRightMotorMasterTalonId = 5;
	public static int kRightMotorSlave1TalonId = 6;
	public static int kRightMotorSlave2TalonId = 7;

    // WPILib doesn't handle drive motor reversal correctly, so we'll do it with these flags
	// +1 if not reversed, -1 if reversed
	public static final int lMotorPolarity = -1;
	public static final int rMotorPolarity = 1;
	
	public static final int kTalonCurrentLimit = 25;
	
	//Climb motor
	public static int kClimbMotorTalonId = 4;
	
	// Gear intake motor
	public static int kIntakeMotorTalonId = 8;

	
	//Solenoid ports for pneumatics
	public static int kGearShiftSolenoidForwardChannel = 1;
	public static int kGearShiftSolenoidReverseChannel = 0;	
	public static int kGearPickupSolenoidForwardChannel = 2;
	public static int kGearPickupSolenoidReverseChannel = 3;
	public static int kBallTraySolenoidForwardChannel = 4;
	public static int kBallTraySolenoidReverseChannel = 5;
    

    // Joystick Controls
    public static int kXboxButtonA  = 1;
    public static int kXboxButtonB  = 2;
    public static int kXboxButtonX  = 3;
    public static int kXboxButtonY  = 4;
    public static int kXboxButtonLB = 5;
    public static int kXboxButtonRB = 6;
    
    public static int kXboxLStickXAxis  = 0;
    public static int kXboxLStickYAxis  = 1;
    public static int kXboxLTriggerAxis = 2;
    public static int kXboxRTriggerAxis = 3;
    public static int kXboxRStickXAxis  = 4;
    public static int kXboxRStickYAxis  = 5;

    // Joystick Mappings
    public static int kHighGearButton1 		= Constants.kXboxButtonLB;
    public static int kHighGearButton2 		= Constants.kXboxButtonRB;
    public static int kGearIntakeButton 	= Constants.kXboxButtonA;
    public static int kGearScoreButton 		= Constants.kXboxButtonX;
    public static int kBallTrayButton 		= Constants.kXboxButtonY;
    public static int kSwitchCameraButton	= Constants.kXboxButtonB;
    //public static int kQuickTurnButton 		= Constants.kXboxButtonA;
    
    public static int kClimbAxis			= Constants.kXboxRTriggerAxis; 
        
    //Robot stops when joystick axis < 0.1 and >-0.1
    public static double kDriveDeadzone = 0.2;



    // Relay Ports
    public static int kLedRelayPort = 0;
    
    // Gyro
	// The I2C port the BNO055 is connected to
    public static final I2C.Port BNO055_PORT = I2C.Port.kOnboard;
	public static final double kGearOuttakeTimerThreshold = 0.500;
    
    // BNO055 accelerometer calibration constants
    // ( -7, -34,  33, -24) - taken 10/14/2016
    // (-13, -53,  18, -24) - taken 10/14/2016
    // (  0, -59,  25, -24) - taken 10/14/2016
    // using average of the above
    public static short kAccelOffsetX =  -7;
    public static short kAccelOffsetY = -53;
    public static short kAccelOffsetZ =  25;
    public static short kAccelRadius  = -24;
    
    
}
