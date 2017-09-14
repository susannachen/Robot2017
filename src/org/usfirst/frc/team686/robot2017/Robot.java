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
import org.usfirst.frc.team686.robot2017.lib.util.Pose;
import org.usfirst.frc.team686.robot2017.loop.DriveLoop;
import org.usfirst.frc.team686.robot2017.loop.LoopController;
import org.usfirst.frc.team686.robot2017.loop.RobotStateLoop;
import org.usfirst.frc.team686.robot2017.subsystems.*;
import org.usfirst.frc.team686.robot2017.util.DataLogController;

import edu.wpi.cscore.UsbCamera;
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

	
	AutoModeExecuter autoModeExecuter = null;
	
    LoopController loopController;
    
    SmartDashboardInteractions smartDashboardInteractions;
    DataLogController robotLogger;
    
    CameraServer camera;

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
    		
    		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
    		camera.setBrightness(-255);
    		camera.setResolution(640, 480);
    		
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
    	gearMode = GearOption.DEFAULT;
    	boolean logToFile = true;
    	boolean logToSmartDashboard = true;
    	robotLogger.setOutputMode(logToFile, logToSmartDashboard);
    	
    	try{
    		CrashTracker.logAutoInit();
    		if(autoModeExecuter != null){
    			autoModeExecuter.stop();
    		}
    		autoModeExecuter = null;
    		
    		autoModeExecuter = new AutoModeExecuter();
    		autoModeExecuter.setAutoMode(new DriveStraightMode(0, false));//setAutoMode(smartDashboardInteractions.getAutoModeSelection());
    		
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
			boolean rStickYAxis = controls.getButton(Constants.kXboxRStickYAxis);
			boolean rButtonPressed = controls.getButton(Constants.kXboxButtonRB);
			boolean lButtonPressed = controls.getButton(Constants.kXboxButtonLB);
			boolean xButtonPressed = controls.getButton(Constants.kXboxButtonX);
			boolean aButtonPressed = controls.getButton(Constants.kXboxButtonA);
			
			if(gearMode != GearOption.OUTTAKE && gearMode != GearOption.OUTTAKE_START && !xButtonPressed){
				drive.setOpenLoop(controls.getDriveCommand());
			}
			
			//climber.climb(rStickYAxis);
			if(rButtonPressed || lButtonPressed){
				gearShifter.setLowGear();
			}
			if(!lButtonPressed && !rButtonPressed){
				gearShifter.setHighGear();
			}
			
			switch(gearMode){
				case INITIALIZE:
					gearPickup.down();
					gearPickup.stopIntake();
					gearMode = GearOption.DEFAULT;
					break;
				case DEFAULT:
					gearPickup.up();
					gearPickup.stopIntake();
					
					if(aButtonPressed){
						gearMode = GearOption.INTAKE;
					}else if(xButtonPressed){
						gearMode = GearOption.OUTTAKE_START;
					}
					
					break;
				case INTAKE:
					gearPickup.down();
					gearPickup.intake();
					if(!aButtonPressed){
						gearMode = GearOption.DEFAULT;
					}
					break;
				case OUTTAKE_START:
					startDropPeg = Timer.getFPGATimestamp();
					gearMode = GearOption.OUTTAKE;
				case OUTTAKE:
					gearPickup.down();
					gearPickup.outtake();
					drive.setOpenLoop(new DriveCommand(0.5, 0.5)); //not sure why not negative
					
					double now = Timer.getFPGATimestamp();
					double timePassed = now-startDropPeg;
					
					if(timePassed>0.5){
						gearMode = GearOption.DEFAULT;
					}
					break;
					
			}
								
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
		//robotLogger.log();
	}


	
	
	/*private final DataLogger logger = new DataLogger()
    {
        @Override
        public void log()
        {
			put("OperationalMode", operationalMode.getVal());
        }
    };*/
    
    //public DataLogger getLogger() { return logger; }
	
	
}
