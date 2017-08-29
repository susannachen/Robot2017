package org.usfirst.frc.team686.bot;

public class Constants {

	//climb motor
	public static int kClimbMotorTalonId = 4;
	
	//motor control
	public static double kDeadzone = 0.1;
	
	
	//Solenoid ports
	public static int kSolenoidForwardChannel = 0;
	public static int kSolenoidReverseChannel = 1;
	
	//Talon motor ports
	public static int kLeftMotorMasterTalonId = 1;
	public static int kLeftMotorSlave1TalonId = 2;
	public static int kLeftMotorSlave2TalonId = 3;
	public static int kRightMotorMasterTalonId = 6;
	public static int kRightMotorSlave1TalonId = 7;
	public static int kRightMotorSlave2TalonId = 8;
	
	//Encoder
	public static double kDistancePerPulse = 0.042;
	public static int    kQuadEncoderCodesPerRev = 256;
    public static int    kQuadEncoderPulsesPerRev = 4*kQuadEncoderCodesPerRev;
    public static double kQuadEncoderStatusFramePeriod = 0.100;	// 100ms
    
    // CONTROL LOOP GAINS
    public static double kFullThrottleRPM = 520;	// measured max RPM using NI web interface
    public static double kFullThrottleEncoderPulsePer100ms = kFullThrottleRPM / 60.0 * kQuadEncoderStatusFramePeriod * kQuadEncoderPulsesPerRev; 
    
    
    // PID gains for drive velocity loop (sent to Talon)
    // Units: error is 4*256 counts/rev. Max output is +/- 1023 units.
    public static double kDriveVelocityKp = 1.0;
    public static double kDriveVelocityKi = 0.0;
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
	
	//Xbox controller
	public static int kJoystickPort = 0;
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
	
	
	//speed multiplier
	public final static double kSpeedMultiplier = 0.625;
	
}
