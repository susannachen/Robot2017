package org.usfirst.frc.team686.robot2017;

import org.usfirst.frc.team686.robot2017.auto.AutoModeBase;
import org.usfirst.frc.team686.robot2017.auto.modes.DriveStraightMode;
import org.usfirst.frc.team686.robot2017.auto.modes.SquarePatternMode;
import org.usfirst.frc.team686.robot2017.auto.modes.StandStillMode;
import org.usfirst.frc.team686.robot2017.auto.*;
import org.usfirst.frc.team686.robot2017.auto.modes.*;
import org.usfirst.frc.team686.robot2017.lib.joystick.ArcadeDriveJoystick;
import org.usfirst.frc.team686.robot2017.lib.joystick.JoystickControlsBase;
import org.usfirst.frc.team686.robot2017.lib.util.Pose;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;




// TODO: learn how to make custom LabView? GUI interface


/**
 * Controls the interactive elements of SmartDashboard.
 *
 * Keeps the network tables keys in one spot and enforces autonomous mode
 * invariants.
 */
public class SmartDashboardInteractions 
{

    SendableChooser<AutoModeOption> autoModeChooser;
    SendableChooser<AutoStartOption> startPositionChooser;
    SendableChooser<Integer> autoShootChooser;
    
    enum AutoModeOption 
    {
        //PLACE_PEG("Place Peg"),
        STAND_STILL("Stand Still"),
        DRIVE_STRAIGHT("Drive Straight"),
        SQUARE_PATTERN("Square Pattern"),
    	//POINT_TURN_TEST("Point Turn Test"),
    	//VISION_DELAY_CALIB("Vision Delay Calibration");
        //DRIVE_STRAIGHT("Drive Straight"),
        STEAMWORKS_CENTER("Steamworks Center"),
        STEAMWORKS_BOILER("Steamworks Boiler"),
        STEAMWORKS_OTHER("Steamworks Other");
    	
        public final String name;

        AutoModeOption(String name) {
            this.name = name;
        }
    }

    SendableChooser<JoystickOption> joystickModeChooser;
    
    enum JoystickOption 
    {
        ARCADE_DRIVE("Arcade Drive");
        /*TRIGGER_DRIVE("Trigger Drive"),				// works for Xbox controller and Xbox steering wheel
        TANK_DRIVE("Tank Drive"),
        CHEESY_ARCADE_DRIVE("Cheesy Arcade Drive"),
        CHEESY_TRIGGER_DRIVE("Cheesy Trigger Drive"),
        CHEESY_2STICK_DRIVE("Cheesy Two-Stick Drive"),
        ADAM_ARCADE_DRIVE("Adam Arcade Drive"),
        POLAR_ARCADE_DRIVE("Polar Arcade Drive")
		*/
        public final String name;

        JoystickOption(String name) {
            this.name = name;
        }
    }
    
    
    public void initWithDefaults() 
    {
    	autoModeChooser = new SendableChooser<AutoModeOption>();
    	autoModeChooser.addObject(AutoModeOption.STAND_STILL.toString(),    AutoModeOption.STAND_STILL);
    	//autoModeChooser.addDefault( AutoModeOption.PLACE_PEG.toString(),      AutoModeOption.PLACE_PEG);
    	autoModeChooser.addObject( AutoModeOption.DRIVE_STRAIGHT.toString(), AutoModeOption.DRIVE_STRAIGHT);
    	autoModeChooser.addObject( AutoModeOption.SQUARE_PATTERN.toString(), AutoModeOption.SQUARE_PATTERN);
    	//autoModeChooser.addObject( AutoModeOption.POINT_TURN_TEST.toString(), AutoModeOption.POINT_TURN_TEST);
    	//autoModeChooser.addObject( AutoModeOption.VISION_DELAY_CALIB.toString(), AutoModeOption.VISION_DELAY_CALIB);
    	SmartDashboard.putData("Auto Mode Chooser", autoModeChooser);
    	
    	joystickModeChooser = new SendableChooser<JoystickOption>();
    	joystickModeChooser.addDefault(JoystickOption.ARCADE_DRIVE.toString(),        JoystickOption.ARCADE_DRIVE);
    	/*joystickModeChooser.addObject(JoystickOption.TRIGGER_DRIVE.toString(),        JoystickOption.TRIGGER_DRIVE);
    	joystickModeChooser.addObject(JoystickOption.TANK_DRIVE.toString(), 	      JoystickOption.TANK_DRIVE);
     	joystickModeChooser.addObject(JoystickOption.CHEESY_ARCADE_DRIVE.toString(),  JoystickOption.CHEESY_ARCADE_DRIVE);
    	joystickModeChooser.addObject(JoystickOption.CHEESY_TRIGGER_DRIVE.toString(), JoystickOption.CHEESY_TRIGGER_DRIVE);
    	joystickModeChooser.addObject(JoystickOption.CHEESY_2STICK_DRIVE.toString(),  JoystickOption.CHEESY_2STICK_DRIVE);
    	joystickModeChooser.addObject(JoystickOption.ADAM_ARCADE_DRIVE.toString(),    JoystickOption.ADAM_ARCADE_DRIVE);
    	joystickModeChooser.addObject(JoystickOption.POLAR_ARCADE_DRIVE.toString(),   JoystickOption.POLAR_ARCADE_DRIVE);
    	*/SmartDashboard.putData("Joystick Chooser", joystickModeChooser);
    	
     }
    
    
    public AutoModeBase getAutoModeSelection() 
    {
    	AutoModeOption selMode = (AutoModeOption)autoModeChooser.getSelected(); 
    	int selLane = startPositionChooser.getSelected().number;

        FieldDimensions fieldDimensions = new FieldDimensionsRed();
        DriverStation.Alliance alliance = DriverStation.getInstance().getAlliance();
        if (alliance == DriverStation.Alliance.Blue)
        {
        	fieldDimensions = new FieldDimensionsBlue();
        }

        System.out.print("Alliance detected as: ");
        if (alliance == DriverStation.Alliance.Red) {
            System.out.println("Red");
        } else if (alliance == DriverStation.Alliance.Blue) {
                System.out.println("Blue");
        } else {
            System.out.println("INVALID");
        }

        
        
    	switch (selMode)
    	{
    	
    	case STAND_STILL:
			return new StandStillMode();
			
//    	case DRIVE_STRAIGHT:
//			return new DriveStraightMode(0, false);
			
    	case STEAMWORKS_CENTER:
			return new StartToCenterPegToBoilerMode(fieldDimensions);
			
    	case STEAMWORKS_BOILER:
			return new StartToBoilerPegToBoilerMode(fieldDimensions);
			
    	case STEAMWORKS_OTHER:
			return new StartToOtherPegToOtherSideMode(fieldDimensions);
			
    	case SQUARE_PATTERN:
    		return new SquarePatternMode(selLane, false);
    		
    	//case POINT_TURN_TEST:
    	//	return new PointTurnTestMode();
    		
    	//case VISION_DELAY_CALIB:
		//	return new CalibrateVisionDelayMode();
    		
    	case START_TO_BOILER_PEG:
    		return new StartToBoilerPegMode(true);
		
		default:
            System.out.println("ERROR: unexpected auto mode: " + selMode);
			return new StandStillMode();
	    }
    }


    public JoystickControlsBase getJoystickControlsMode() 
    {
    	JoystickOption selMode = (JoystickOption)joystickModeChooser.getSelected(); 
    	
    	switch (selMode)
    	{
    	case ARCADE_DRIVE:
			return ArcadeDriveJoystick.getInstance();
			
    	/*case TRIGGER_DRIVE:
			return TriggerDriveJoystick.getInstance();
			
    	case TANK_DRIVE:
    		return TankDriveJoystick.getInstance();
    		
    	case CHEESY_ARCADE_DRIVE:
    		return CheesyArcadeDriveJoystick.getInstance();
    		
    	case CHEESY_TRIGGER_DRIVE:
    		return CheesyTriggerDriveJoystick.getInstance();
    		
    	case CHEESY_2STICK_DRIVE:
    		return CheesyTwoStickDriveJoystick.getInstance();

    	case ADAM_ARCADE_DRIVE:
			return AdamArcadeDriveJoystick.getInstance();

    	case POLAR_ARCADE_DRIVE:
    		return PolarArcadeDriveJoystick.getInstance();
    		    		*/
    	default:
            System.out.println("ERROR: unexpected joystick selection: " + selMode);
			return ArcadeDriveJoystick.getInstance();
    	}
    }
}
    
   


