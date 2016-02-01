
package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.auto.AutoExecutor;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.MultiLooper;
import org.camsrobotics.lib.NerdyJoystick;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * This is where the magic happens!
 * 
 * @author Wesley
 * 
 */
public class Robot extends IterativeRobot {
	
	MultiLooper slowControllers = new MultiLooper("SlowControllers", 1/100.0);
	
	AutoExecutor auto = new AutoExecutor(AutoExecutor.Mode.LOW_BAR);
	
	Compressor compressor = HardwareAdapter.kCompressor;
	Drive drive = HardwareAdapter.kDrive;
	
	NerdyJoystick driveLeftJoy = HardwareAdapter.kDriveLeftStick;
	NerdyJoystick driveRightJoy = HardwareAdapter.kDriveRightStick;
	
    public void robotInit() {
    	compressor.start();
    	
    	System.out.println("NerdyBot Apotheosis Initialization");
    	
    	slowControllers.addLoopable(drive);
    }
    
    public void autonomousInit() {
    	System.out.println("NerdyBot Apotheosis Autonomous Start");
    	
    	drive.resetEncoders();
    	
    	auto.start();
    	
    	slowControllers.start();
    }

    public void autonomousPeriodic() {
    	
    }

    public void teleopInit()	{
    	System.out.println("NerdyBot Apotheosis Teleoperated Start");
    	
    	drive.resetEncoders();
    	slowControllers.start();
    }
    
    public void teleopPeriodic() {
    	drive.driveOpenLoop(new DriveSignal(driveLeftJoy.getTrueY(), driveRightJoy.getTrueY()));
    }
    
    public void disabledInit()	{
    	System.out.println("NerdyBot Apotheosis Disabled...enable me!");
    	
    	auto.stop();
    	
    	slowControllers.stop();
    	
    	drive.driveOpenLoop(DriveSignal.kStop);
    	drive.stop();
    }
    
    public void disabledPeriodic()	{
    	
    }
    
    public void allPeriodic()	{
    	// TODO: add the logging
    }
    
}
