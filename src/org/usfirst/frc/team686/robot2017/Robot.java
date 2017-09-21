package org.usfirst.frc.team686.robot2017; 

import java.util.TimeZone;

import org.usfirst.frc.team686.robot2017.auto.AutoModeExecuter;
import org.usfirst.frc.team686.robot2017.auto.modes.DriveStraightMode;
import org.usfirst.frc.team686.robot2017.command_status.DriveCommand;
import org.usfirst.frc.team686.robot2017.command_status.DriveStatus;
import org.usfirst.frc.team686.robot2017.command_status.RobotState;
import org.usfirst.frc.team686.robot2017.lib.joystick.ArcadeDriveJoystick;
import org.usfirst.frc.team686.robot2017.lib.joystick.JoystickControlsBase;
import org.usfirst.frc.team686.robot2017.lib.util.CrashTracker;
import org.usfirst.frc.team686.robot2017.lib.util.DataLogger;
import org.usfirst.frc.team686.robot2017.lib.util.Pose;
import org.usfirst.frc.team686.robot2017.loop.DriveLoop;
import org.usfirst.frc.team686.robot2017.loop.LoopController;
import org.usfirst.frc.team686.robot2017.loop.RobotStateLoop;
import org.usfirst.frc.team686.robot2017.subsystems.*;
import org.usfirst.frc.team686.robot2017.util.DataLogController;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSink;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;

public class Robot extends IterativeRobot {
    
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	JoystickControlsBase controls = ArcadeDriveJoystick.getInstance();
	RobotState robotState = RobotState.getInstance();	
	Drive drive = Drive.getInstance();			
	GearPickup gearPickup = GearPickup.getInstance();
	Climber climber = Climber.getInstance();
	GearShift gearShifter = GearShift.getInstance();
	BallTray ballTray = BallTray.getInstance();

	
	AutoModeExecuter autoModeExecuter = null;
	
    LoopController loopController;
    
    SmartDashboardInteractions smartDashboardInteractions;
    DataLogController robotLogger;
    
    VideoSource camera1;
    VideoSource camera2;
    CvSink cvsink1;
    CvSink cvsink2;
    VideoSink server;
    boolean cameraButtonPrev = false;

    private double startDropPeg;

    enum GearOption{
    	INITIALIZE, DEFAULT, INTAKE, OUTTAKE_START, OUTTAKE;
    }
    
    GearOption gearMode = GearOption.INITIALIZE;
    
    enum OperationalMode 
    {
    	DISABLED(0), AUTONOMOUS(1), TELEOP(2), TEST(3);
    	
    	private int val;
    	
    	private OperationalMode (int val) {this.val = val;}
    	public int getVal() {return val;}
    } 
    
    OperationalMode operationalMode = OperationalMode.DISABLED;
    
    public Robot() {
    	CrashTracker.logRobotConstruction();
    }
    
    @Override
    public void robotInit()
    {
    	try
    	{
    		CrashTracker.logRobotInit();
    		
    		loopController = new LoopController();
    		loopController.register(drive.getVelocityPIDLoop());
    		loopController.register(DriveLoop.getInstance());
    		loopController.register(RobotStateLoop.getInstance());
    		
    		smartDashboardInteractions = new SmartDashboardInteractions();
    		smartDashboardInteractions.initWithDefaults();
    		
    		// set datalogger and time info
    		TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
    		
    		robotLogger = DataLogController.getRobotLogController();
    		robotLogger.register(Drive.getInstance().getLogger());
    		robotLogger.register(drive.getCommand().getLogger());
    		robotLogger.register(DriveStatus.getInstance().getLogger());
    		robotLogger.register(RobotState.getInstance().getLogger());
    		
    		setInitialPose(new Pose());
    		
    		cameraButtonPrev = false;
    		UsbCamera camera1 = CameraServer.getInstance().startAutomaticCapture(0);
    		UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(1);
			server = CameraServer.getInstance().getServer();
		
			// dummy sinks to keep camera connections open
			cvsink1 = new CvSink("cam1cv");
			cvsink1.setSource(camera1);
			cvsink1.setEnabled(true);
			cvsink2 = new CvSink("cam2cv");
			cvsink2.setSource(camera2);
			cvsink2.setEnabled(true);
    		
//    		camera1.setBrightness(-255);
    		camera1.setResolution(640, 480);
//    		camera2.setBrightness(-255);
    		camera2.setResolution(640, 480);
    		
    	}
    	catch(Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
    }
    
    public void setInitialPose (Pose _initialPose){
    	robotState.reset(Timer.getFPGATimestamp(), DriveStatus.getInstance().getLeftDistanceInches(), DriveStatus.getInstance().getRightDistanceInches(), _initialPose);
    	System.out.println("InitialPose: " + _initialPose);
    }
    
    public void zeroAllSensors()
    {
    	drive.zeroSensors();
    }
    
    public void stopAll()
    {
    	drive.stop();
    }
    
    
    //DISABLED MODE
    
    @Override
    public void disabledInit()
    {
    	operationalMode = OperationalMode.DISABLED;
    	boolean logToFile = true;
    	boolean logToSmartDashboard = true;
    	robotLogger.setOutputMode(logToFile, logToSmartDashboard);
    	
    	try
    	{
    		CrashTracker.logDisabledInit();
    		if(autoModeExecuter != null){
    			autoModeExecuter.stop();
    		}
    		autoModeExecuter = null;
    		
    		stopAll(); //How does stopAll stop all??
    		loopController.start();
    	}
    	catch(Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
    }
    
    @Override
	public void disabledPeriodic() 
	{
		try 
		{
			stopAll(); 			// stop all actuators

			System.gc(); 		// runs garbage collector
			
			// CAMERA
			boolean cameraSwitchButton = controls.getButton(Constants.kSwitchCameraButton);

			if (cameraSwitchButton && !cameraButtonPrev)
			{
				server.setSource(camera2);
			}
			else if (!cameraSwitchButton && cameraButtonPrev)
			{
				server.setSource(camera1);
			}
			cameraButtonPrev = cameraSwitchButton;
			
		} 
		catch (Throwable t) 
		{
			CrashTracker.logThrowableCrash(t);
			throw t;
		}
	}
    
    
    //AUTONOMOUS MODE
    @Override
    public void autonomousInit()
    {
    	operationalMode = OperationalMode.AUTONOMOUS;
    	boolean logToFile = true;
    	boolean logToSmartDashboard = true;
    	robotLogger.setOutputMode(logToFile, logToSmartDashboard);
    	
    	try{
    		gearPickup.up();
    		ballTray.up();
			gearShifter.setLowGear();
    		
    		CrashTracker.logAutoInit();
    		if(autoModeExecuter != null){
    			autoModeExecuter.stop();
    		}
    		autoModeExecuter = null;
    		
    		autoModeExecuter = new AutoModeExecuter();
    		autoModeExecuter.setAutoMode(smartDashboardInteractions.getAutoModeSelection());
    		
    		setInitialPose(autoModeExecuter.getAutoMode().getInitialPose());
    		
    		autoModeExecuter.start();
    	}
    	catch(Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
    	
    }
    
    @Override
    public void autonomousPeriodic(){
    	try
    	{
    		
    	}
    	catch (Throwable t)
    	{
    		CrashTracker.logThrowableCrash(t);
    		throw t;
    	}
    }
    
	@Override
	public void teleopInit() 
	{
		operationalMode = OperationalMode.TELEOP;
		gearMode = GearOption.DEFAULT;
		boolean logToFile = true;
		boolean logToSmartDashboard = true;
		robotLogger.setOutputMode(logToFile, logToSmartDashboard);

		try 
		{
			CrashTracker.logTeleopInit();

			// Select joystick control method
			controls = smartDashboardInteractions.getJoystickControlsMode();
			

			// Configure looper
			loopController.start();

			gearShifter.setLowGear();
			drive.setOpenLoop(DriveCommand.NEUTRAL());

		} 
		catch (Throwable t) 
		{
			CrashTracker.logThrowableCrash(t);
			throw t;
		}

	}

	@Override
	public void teleopPeriodic() 
	{
		try 
		{
			boolean highGearButton   = controls.getButton(Constants.kHighGearButton1) || controls.getButton(Constants.kHighGearButton2);
			boolean gearScoreButton  = controls.getButton(Constants.kGearScoreButton);
			boolean gearIntakeButton = controls.getButton(Constants.kGearIntakeButton);
			double  climbStickValue  = controls.getAxis(Constants.kClimbAxis);
			boolean cameraSwitchButton = controls.getButton(Constants.kSwitchCameraButton);
			boolean ballTrayButton = controls.getButton(Constants.kBallTrayButton);
			
			// GEAR INTAKE
			switch(gearMode){
				case INITIALIZE:
					gearPickup.down();
					gearPickup.stopIntake();
					gearMode = GearOption.DEFAULT;
					break;
				case DEFAULT:
					gearPickup.up();
					gearPickup.stopIntake();
					
					if(gearIntakeButton){
						gearMode = GearOption.INTAKE;
					}else if(gearScoreButton){
						gearMode = GearOption.OUTTAKE_START;
					}
					
					break;
				case INTAKE:
					gearPickup.down();
					gearPickup.intake();
					if(!gearIntakeButton){
						gearMode = GearOption.DEFAULT;
					}
					break;
				case OUTTAKE_START:
					startDropPeg = Timer.getFPGATimestamp();
					gearMode = GearOption.OUTTAKE;
				case OUTTAKE:
					gearPickup.down();
					gearPickup.outtake();
					drive.setOpenLoop(new DriveCommand(-0.5, -0.5));
					
					double now = Timer.getFPGATimestamp();
					double timePassed = now-startDropPeg;
					
					if(timePassed>0.5){
						gearMode = GearOption.DEFAULT;
					}
					break;
					
			}
								
			// SHIFTER
			if (highGearButton)
			{
				gearShifter.setHighGear();
			} else {
				gearShifter.setLowGear();
			}
			

			// DRIVE
			if (gearMode != GearOption.OUTTAKE && gearMode != GearOption.OUTTAKE_START) {
				// ignore joystick drive controls while gear is being scored
				drive.setOpenLoop(controls.getDriveCommand());
			}

			
			// CLIMBER
			climber.climb( climbStickValue );

			
			
			// Ball Tray
			if (ballTrayButton){
				ballTray.down();
			} else { 
				ballTray.up();
			}
			
			
			
			// CAMERA
			if (cameraSwitchButton && !cameraButtonPrev)
			{
				server.setSource(camera2);
			}
			else if (!cameraSwitchButton && cameraButtonPrev)
			{
				server.setSource(camera1);
			}
			cameraButtonPrev = cameraSwitchButton;
		} 
		catch (Throwable t) 
		{
			CrashTracker.logThrowableCrash(t);
			throw t;
		}

	}
	
	@Override
	public void testInit() 
	{
		loopController.start();
	}

	@Override
	public void testPeriodic()
	{
		drive.testDriveSpeedControl();
	}
	
	
	// called after disabledPeriodic, autoPeriodic, and teleopPeriodic 
	@Override
	public void robotPeriodic()
	{
		robotLogger.log();
	}


	
	
	private final DataLogger logger = new DataLogger()
    {
        @Override
        public void log()
        {
			put("OperationalMode", operationalMode.getVal());
        }
    };
    
    public DataLogger getLogger() { return logger; }
	
	
}
