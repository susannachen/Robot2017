package org.usfirst.frc.team686.bot.subsystems;

import org.usfirst.frc.team686.bot.Constants;
import org.usfirst.frc.team686.bot.auto.actions.DriveWithJoystick;

import com.ctre.CANTalon;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class DriveTrain extends Subsystem{
	public CANTalon lMotorMaster, lMotorSlave1, lMotorSlave2;
	public CANTalon rMotorMaster, rMotorSlave1, rMotorSlave2;
    
	private Encoder lEncoder = new Encoder(1, 2);
	private Encoder rEncoder = new Encoder(3, 4);
	
	private static final int kVelocityControlSlot = 0;
	private static final int kBaseLockControlSlot = 1;
	
	public DriveTrain() {
		super();
		
		lMotorMaster = new CANTalon(Constants.kLeftMotorMasterTalonId);
		lMotorSlave1 = new CANTalon(Constants.kLeftMotorSlave1TalonId);
		lMotorSlave2 = new CANTalon(Constants.kLeftMotorSlave2TalonId);
		rMotorMaster = new CANTalon(Constants.kRightMotorMasterTalonId);
		rMotorSlave1 = new CANTalon(Constants.kLeftMotorSlave1TalonId);
		rMotorSlave2 = new CANTalon(Constants.kLeftMotorSlave2TalonId);
		
		lMotorSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);
		lMotorSlave2.changeControlMode(CANTalon.TalonControlMode.Follower);
		rMotorSlave1.changeControlMode(CANTalon.TalonControlMode.Follower);
		rMotorSlave2.changeControlMode(CANTalon.TalonControlMode.Follower);
		lMotorSlave1.set(Constants.kLeftMotorMasterTalonId);		// give slave the TalonID of it's master
		lMotorSlave2.set(Constants.kLeftMotorMasterTalonId);		// give slave the TalonID of it's master
		rMotorSlave1.set(Constants.kRightMotorMasterTalonId);
		rMotorSlave2.set(Constants.kRightMotorMasterTalonId);
        
		// Get status at 100Hz
		lMotorMaster.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		rMotorMaster.setStatusFrameRateMs(CANTalon.StatusFrameRate.Feedback, 10);
		
		
        lMotorMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
        rMotorMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
		lMotorMaster.set(0);
		rMotorMaster.set(0);
		lMotorMaster.enableBrakeMode(false);
		rMotorMaster.enableBrakeMode(false);
		lMotorSlave1.enableBrakeMode(false);
		lMotorSlave2.enableBrakeMode(false);
		rMotorSlave1.enableBrakeMode(false);
		rMotorSlave2.enableBrakeMode(false);
		
		// Set up the encoders
		lMotorMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		rMotorMaster.setFeedbackDevice(CANTalon.FeedbackDevice.QuadEncoder);
		lMotorMaster.configEncoderCodesPerRev(Constants.kQuadEncoderCodesPerRev);	// using this API lets us program velocity in RPM in closed-loop modes
		rMotorMaster.configEncoderCodesPerRev(Constants.kQuadEncoderCodesPerRev);	// Talon SRX Software Reference Manual Section 17.2 API Unit Scaling
		lMotorMaster.setInverted(false);
		rMotorMaster.setInverted(false);
		lMotorMaster.reverseSensor(false); //CHANGE IF DRIVING DIRECTION WRONG
		rMotorMaster.reverseSensor(false);
		lMotorMaster.reverseOutput(false);
		rMotorMaster.reverseOutput(false);
		lMotorSlave1.reverseOutput(false);
		lMotorSlave2.reverseOutput(false);
		rMotorSlave1.reverseOutput(false);
		rMotorSlave2.reverseOutput(false);

		// Load velocity control gains
		lMotorMaster.setPID(Constants.kDriveVelocityKp, Constants.kDriveVelocityKi, Constants.kDriveVelocityKd,
				Constants.kDriveVelocityKf, Constants.kDriveVelocityIZone, Constants.kDriveVelocityRampRate,
				kVelocityControlSlot);
		rMotorMaster.setPID(Constants.kDriveVelocityKp, Constants.kDriveVelocityKi, Constants.kDriveVelocityKd,
				Constants.kDriveVelocityKf, Constants.kDriveVelocityIZone, Constants.kDriveVelocityRampRate,
				kVelocityControlSlot);
		// Load base lock control gains
		lMotorMaster.setPID(Constants.kDriveBaseLockKp, Constants.kDriveBaseLockKi, Constants.kDriveBaseLockKd,
				Constants.kDriveBaseLockKf, Constants.kDriveBaseLockIZone, Constants.kDriveBaseLockRampRate,
				kBaseLockControlSlot);
		rMotorMaster.setPID(Constants.kDriveBaseLockKp, Constants.kDriveBaseLockKi, Constants.kDriveBaseLockKd,
				Constants.kDriveBaseLockKf, Constants.kDriveBaseLockIZone, Constants.kDriveBaseLockRampRate,
				kBaseLockControlSlot);
		
		
		lEncoder.setDistancePerPulse(Constants.kDistancePerPulse);
		rEncoder.setDistancePerPulse(Constants.kDistancePerPulse);

		// Let's show everything on the LiveWindow
		LiveWindow.addActuator("Drive Train", "Left Motor Master", (CANTalon) lMotorMaster);
		LiveWindow.addActuator("Drive Train", "Left Motor Slave1", (CANTalon) lMotorSlave1);
		LiveWindow.addActuator("Drive Train", "Left Motor Slave2", (CANTalon) lMotorSlave2);
		LiveWindow.addActuator("Drive Train", "Left Motor Master", (CANTalon) rMotorMaster);
		LiveWindow.addActuator("Drive Train", "Left Motor Slave1", (CANTalon) rMotorSlave1);
		LiveWindow.addActuator("Drive Train", "Left Motor Slave2", (CANTalon) rMotorSlave2);

		LiveWindow.addSensor("Drive Train", "Left Encoder", lEncoder);
		LiveWindow.addSensor("Drive Train", "Right Encoder", rEncoder);
	}
	
	
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DriveWithJoystick());
	}
	
	public void log() {
		SmartDashboard.putNumber("Left Distance", lEncoder.getDistance());
		SmartDashboard.putNumber("Right Distance", rEncoder.getDistance());
		SmartDashboard.putNumber("Left Speed", lEncoder.getRate());
		SmartDashboard.putNumber("Right Speed", rEncoder.getRate());
	}
	
	public void drive(double lSpeed, double rSpeed) { //TODO: change to masters and slaves
		lMotorMaster.set(lSpeed);
		rMotorMaster.set(rSpeed);
	}
	
	public void drive(Joystick joy) {
		
		double lRawAxis = joy.getRawAxis(Constants.kXboxLStickYAxis);
		double rRawAxis = joy.getRawAxis(Constants.kXboxRStickYAxis);
		SmartDashboard.putNumber("Left Raw Axis", lRawAxis);
		SmartDashboard.putNumber("Right Raw Axis", rRawAxis);
		
		if(lRawAxis > -Constants.kDeadzone && lRawAxis < Constants.kDeadzone){ lRawAxis = 0; }
		if(rRawAxis > -Constants.kDeadzone && rRawAxis < Constants.kDeadzone){ rRawAxis = 0; }
		
		double lSpeed = lRawAxis*Constants.kSpeedMultiplier;
		double rSpeed = -rRawAxis*Constants.kSpeedMultiplier;
		
		
		lMotorMaster.set(lSpeed);
		rMotorMaster.set(rSpeed);

	}
	
	public void reset() {
		lEncoder.reset();
		rEncoder.reset();
	}
	
	public double getDistance() {
		return (lEncoder.getDistance() + rEncoder.getDistance()) / 2;
	}
	
}
