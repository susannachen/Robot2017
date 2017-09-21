package org.usfirst.frc.team686.robot2017.command_status;

import org.usfirst.frc.team686.robot2017.lib.util.DataLogger;
import org.usfirst.frc.team686.robot2017.lib.util.Kinematics.WheelSpeed;
import org.usfirst.frc.team686.robot2017.subsystems.Drive;
import org.usfirst.frc.team686.robot2017.subsystems.GearPickup;

import com.ctre.CANTalon.TalonControlMode;

import edu.wpi.first.wpilibj.Timer;

/**
 * A drivetrain command consisting of the left, right motor settings and whether the brake mode is enabled.  
 * The command is set by Drive.java, and read by DriveLoop.java, which sends it to the drive motors
 */



public class GearCommand
{    
 
	// The robot gear intake's various states
	enum GearMode{ INITIALIZE, DEFAULT, INTAKE, OUTTAKE_START, OUTTAKE; }
	
	// all member variables should be private to force other object to use the set/get access methods
	// which are synchronized to allow multi-thread synchronization	
	private Drive drive = Drive.getInstance();
	private GearMode gearMode = GearMode.DEFAULT;
	private double commandTime;
	private double startDropPeg;
	
	private GearPickup gearPickup = GearPickup.getInstance();
	
	public GearCommand()
	{
	   this(GearMode.DEFAULT);
	}


    public GearCommand(GearMode _mode) 
    {
    	setGearMode(_mode);
    }

    public synchronized void setGearMode(GearMode _gearMode) 
    {
    	gearMode = _gearMode;
		switch(gearMode){
		case INITIALIZE:
			gearPickup.down();
			gearPickup.stopIntake();
			gearMode = GearMode.DEFAULT;
			break;
		case DEFAULT:
			gearPickup.up();
			gearPickup.stopIntake();
			
			/*if(aButtonPressed){
				gearMode = GearMode.INTAKE;
			}else if(xButtonPressed){
				gearMode = GearOption.OUTTAKE_START;
			}*/
			
			break;
		case INTAKE:
			gearPickup.down();
			gearPickup.intake();
			/*if(!aButtonPressed){
				gearMode = GearOption.DEFAULT;
			}*/
			break;
		case OUTTAKE_START:
			setCommandTime();
			gearMode = GearMode.OUTTAKE;
		case OUTTAKE:
			gearPickup.down();
			gearPickup.outtake();
			drive.setOpenLoop(new DriveCommand(0.5, 0.5)); //not sure why not negative
			
			double now = Timer.getFPGATimestamp();
			double timePassed = now-startDropPeg;
			
			if(timePassed>0.5){
				gearMode = GearMode.DEFAULT;
			}
			break;
			
		}
    }
    public synchronized GearMode getGearMode() { return gearMode; }

	    
    public void   setCommandTime() { commandTime = Timer.getFPGATimestamp(); }
    public double getCommandTime() { return commandTime; } 
	 
    public static GearCommand DEFAULT() {return new GearCommand(GearMode.DEFAULT);}
    
    @Override
    public synchronized String toString() 
    {
    	return String.format("%s", gearMode);
    }
	    
	    
	    
	private final DataLogger logger = new DataLogger()
    {
        @Override
        public void log()
        {
        	synchronized (GearCommand.this)
        	{
	    		put("GearCommand/gearMode", gearMode.toString() );
        	}
        }
    };
    
    public DataLogger getLogger() { return logger; }
	    

    
}
