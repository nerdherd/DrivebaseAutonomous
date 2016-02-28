
package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.auto.AutoExecutor;
import org.camsrobotics.frc2016.auto.modes.*;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.lib.MultiLooper;
import org.camsrobotics.lib.NerdyIterativeRobot;

/**
 * This is where the magic happens!
 * 
 * @author Wesley
 * 
 */
public class Robot extends NerdyIterativeRobot {
	
	MultiLooper slowControllers = new MultiLooper("SlowControllers", 1/100.0);
	
	AutoExecutor auto = new AutoExecutor(new DoNothingAuto());
	
	Drive drive = HardwareAdapter.kDrive;
	
    public void robotInit() {
    	System.out.println("NerdyBot Mantis Initialization");
    	
    	slowControllers.addLoopable(drive);
    }
    
    public void autonomousInit() {
    	System.out.println("NerdyBot Mantis Autonomous Start");
    	
    	drive.zero();
    	
    	auto.start();
    	
    	slowControllers.start();
    }

    public void autonomousPeriodic() {
    	
    }

    public void teleopInit()	{
    	System.out.println("NerdyBot Mantis Teleoperated Start");
    	
    	drive.zero();
    	
    	slowControllers.start();
    }
    
    public void teleopPeriodic() {

    }
    
    public void disabledInit()	{
    	System.out.println("NerdyBot Mantis Disabled...enable me!");
    	
    	auto.stop();
    	
    	slowControllers.stop();
    	
    	drive.stop();
    }
    
    public void disabledPeriodic()	{
    	
    }
    
    public void allPeriodic()	{
    	// TODO: add the logging
    }
    
}
