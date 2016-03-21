package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.teleop.Commands;
import org.camsrobotics.lib.NerdyButton;
import org.camsrobotics.lib.NerdyJoystick;

/**
 * Driver Interface
 * 
 * @author Wesley
 *
 */
public class DriverInput {
	
	private Commands m_commands;
	
	private NerdyJoystick m_driverLeftStick;
	private NerdyJoystick m_driverRightStick;
	
	private NerdyButton m_shiftUp;
	private NerdyButton m_shiftDown;
	
	public DriverInput(NerdyJoystick leftStick, NerdyJoystick rightStick)	{
		m_commands = new Commands();
		
		m_driverLeftStick = leftStick;
		m_driverRightStick = rightStick;
		
		m_shiftUp				= m_driverRightStick.getButton(1);
		m_shiftDown				= m_driverLeftStick.getButton(1);
	}
	
	public Commands update()	{
		// Refresh buttons
		m_shiftUp.update();
		m_shiftDown.update();
		
		/* Do the magic */
		
		// Drive
		if(m_shiftUp.wasPressed())	{
			m_commands.shiftCommand = Commands.DriveShiftCommands.UP;
		}	else if(m_shiftDown.wasPressed())	{
			m_commands.shiftCommand = Commands.DriveShiftCommands.DOWN;
		}
		
		return m_commands;
	}
	
}
