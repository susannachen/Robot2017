package org.usfirst.frc.team686.robot2017.auto.modes;

import org.usfirst.frc.team686.robot2017.auto.AutoModeBase;
import org.usfirst.frc.team686.robot2017.auto.AutoModeEndedException;
import org.usfirst.frc.team686.robot2017.auto.actions.DriveStraightAction;

/**
 * Just drive in a straight line, using VelocityHeading mode
 */
public class DriveStraightMode extends AutoModeBase {

    public DriveStraightMode(int lane, boolean shouldDriveBack) 
    {
    }

    @Override
    protected void routine() throws AutoModeEndedException 
    {
    	System.out.println("Starting Auto Mode: Drive Straight");
    	
        runAction(new DriveStraightAction(48.0, 24.0));       		         
    }
}
