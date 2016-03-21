package org.camsrobotics.frc2016.teleop;

import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.lib.NerdyJoystick;

/**
 * Executes or outsources the various teleoperated commands.
 * 
 * @author Wesley
 *
 */
public class TeleopManager {
	private Drive m_drive = HardwareAdapter.kDrive;
	
	private NerdyJoystick m_driverLeftStick = HardwareAdapter.kDriveLeftStick;
	private NerdyJoystick m_driverRightStick = HardwareAdapter.kDriveRightStick;
	
	boolean rolling = false;
	
	public void update(Commands c)	{
		// Drive
		m_drive.driveOpenLoop(new DriveSignal(m_driverLeftStick.getY(), m_driverRightStick.getY()));

		switch(c.shiftCommand)	{
		case UP:
			m_drive.shiftUp();
			break;
		case DOWN:
			m_drive.shiftDown();
			break;
		}
		
	}
}
