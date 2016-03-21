
package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.auto.AutoExecutor;
import org.camsrobotics.frc2016.auto.modes.*;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.teleop.Commands;
import org.camsrobotics.frc2016.teleop.TeleopManager;
import org.camsrobotics.lib.MultiLooper;
import org.camsrobotics.lib.NerdyIterativeRobot;
import org.camsrobotics.lib.NerdyMath;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * This is where the magic happens!
 * 
 * @author Wesley
 * 
 */
public class Robot extends NerdyIterativeRobot {
	
	MultiLooper slowControllers = new MultiLooper("SlowControllers", 1/100.0);
	
	AutoExecutor auto = new AutoExecutor(new RockWallAuto());
	
	Compressor compressor = HardwareAdapter.kCompressor;
	Drive drive = HardwareAdapter.kDrive;
	
	DriverInput driverInput = HardwareAdapter.kDriverInput;
	TeleopManager teleop = new TeleopManager();
	PowerDistributionPanel pdp = new PowerDistributionPanel();
	
	SendableChooser testCommands;
	
	enum TEST_COMMANDS	{
		DISABLED, DRIVE_LEFT, DRIVE_RIGHT, SHOOTER_RPM
	}
	
	enum AUTO_MODES	{
		LOW_BAR, CAT_D, RAMPARTS
	}
	
	AUTO_MODES autoMode = AUTO_MODES.CAT_D;
	
    public void robotInit() {
    	compressor.start();
    	
    	System.out.println("NerdyBot Mantis Initialization");
    	
    	slowControllers.addLoopable(drive);
    	
    	testCommands = new SendableChooser();
    	testCommands.addDefault("Disabled", TEST_COMMANDS.DISABLED);
    	testCommands.addObject("Drive Left", TEST_COMMANDS.DRIVE_LEFT);
    	testCommands.addObject("Drive Right", TEST_COMMANDS.DRIVE_RIGHT);
    	testCommands.addObject("Shooter RPM", TEST_COMMANDS.SHOOTER_RPM);
    	
    	SmartDashboard.putNumber("Vision P", Constants.kDriveVisionP);
    	SmartDashboard.putNumber("Vision I", Constants.kDriveVisionI);
    	SmartDashboard.putNumber("Vision D", Constants.kDriveVisionD);
    	
    	SmartDashboard.putNumber("Time", 5);
    	
    	
    	SmartDashboard.putNumber("Forward Priority", 1);

    }
    
    Timer autoTimer = new Timer();
    double lastTime;
    AHRS nav = HardwareAdapter.kNavX;
    
    public void autonomousInit() {
    	System.out.println("NerdyBot Mantis Autonomous Start");
    	integration = 0;
    	drive.zero();
    	
    	auto.start();
    	
    	nav.zeroYaw();

    	autoTimer.start();

    	slowControllers.start();
    	
    	lastTime = Timer.getFPGATimestamp();
    	
    	
    }
    
    double driveP = 0.044444;
    double driveI = 0.00044444;
    double integration = 0;
    double lastError = 0;
    
    public void autonomousPeriodic() {
    	driveP = SmartDashboard.getNumber("Drive P");
    	driveI = SmartDashboard.getNumber("Drive I");
    	
    	SmartDashboard.putNumber("Yaw", nav.getYaw());
    	
    	if(autoMode == AUTO_MODES.LOW_BAR)	{
    		
    	}	else if(autoMode == AUTO_MODES.CAT_D)	{
    		if(autoTimer.get() < 4)	{
	        	drive.driveOpenLoop(new DriveSignal(-1,-1));
	    	}	else	{
	    		drive.driveOpenLoop(DriveSignal.kStop);
	    	}
    	}	else if(autoMode == AUTO_MODES.RAMPARTS)	{
    		if(autoTimer.get() < SmartDashboard.getNumber("Time"))	{
    			double time = Timer.getFPGATimestamp();
    			double error = nav.getYaw();
    			double p = error * driveP;
    			integration += (error + lastError) * (time - lastTime)/2;
    			lastTime = time;
    			lastError = error;
    			double i = integration * driveI;
    			SmartDashboard.putNumber("P", p);
    			SmartDashboard.putNumber("I",i);
    			double pow = p + i;
    			SmartDashboard.putNumber("Pow", pow);
    			double leftPow  =  pow - 1;
    			double rightPow = -pow - 1;
    			SmartDashboard.putNumber("Left Power", leftPow);
    			SmartDashboard.putNumber("Right Power", rightPow);
    			SmartDashboard.putNumber("Yaw", error);
    			
    			double[] unnormalized = {leftPow, rightPow};
    		 	double[] normalized = NerdyMath.normalize(unnormalized, true);
    			
    			double leftNormalized = normalized[0];
    			double rightNormalized = normalized[1];
    			SmartDashboard.putNumber("Left Normalized", leftNormalized);
    			SmartDashboard.putNumber("Right Normalized", rightNormalized);
    			
    			drive.driveOpenLoop(new DriveSignal(leftNormalized, rightNormalized));
    		}	else	{
    			drive.driveOpenLoop(DriveSignal.kStop);
    		}
    	}
    	
    }

    public void teleopInit()	{
    	System.out.println("NerdyBot Mantis Teleoperated Start");
    	
    	drive.zero();
    	
    	slowControllers.start();
    }
    
    public void teleopPeriodic() {
    	Commands c = driverInput.update();
        teleop.update(c);
        
    	SmartDashboard.putData("PDP", pdp);
        
        drive.reportState();
        
        drive.setPID(SmartDashboard.getNumber("Vision P", Constants.kDriveVisionP), 
        		SmartDashboard.getNumber("Vision I", Constants.kDriveVisionI), 
        		SmartDashboard.getNumber("Vision D", Constants.kDriveVisionD));
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
    	
    }
    
    public void testPeriodic() {
    	LiveWindow.setEnabled(false);
    	
    	Commands c = driverInput.update();
        teleop.update(c);
    }
    
}
