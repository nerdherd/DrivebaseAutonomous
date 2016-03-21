package org.camsrobotics.frc2016.teleop;

/**
 * Teleoperated commands
 * 
 * @author Wesley
 *
 */
public class Commands {
	
	public static enum DriveShiftCommands	{
		DOWN, UP
	}
	
	public DriveShiftCommands shiftCommand = DriveShiftCommands.DOWN;
	
	public boolean shooting = false;
	
	public boolean reset = false;
}
