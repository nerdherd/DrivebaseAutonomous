package org.camsrobotics.frc2016;

import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.lib.Gearbox;
import org.camsrobotics.lib.NerdyJoystick;

import com.kauailabs.navx_mxp.AHRS;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.Ultrasonic;

/**
 * Hardware Components
 * 
 * @author Wesley
 *
 */
public class HardwareAdapter {
	// Driver Input
	public static final NerdyJoystick kDriveLeftStick	= new NerdyJoystick(0);
	public static final NerdyJoystick kDriveRightStick	= new NerdyJoystick(1);
	public static final NerdyJoystick kButtonBox		= new NerdyJoystick(2);
	
	// Motors
	public static final Talon kDriveFrontLeft		= new Talon(2);
	public static final Talon kDriveFrontRight		= new Talon(4);
	public static final Talon kDriveBackLeft		= new Talon(0);
	public static final Talon kDriveBackRight		= new Talon(3);
	
	// Pneumatics
	public static final Compressor kCompressor			= new Compressor();
	public static final DoubleSolenoid kLeftShifter		= new DoubleSolenoid(1,2);
	public static final DoubleSolenoid kRightShifter	= new DoubleSolenoid(3,4);
	
	// Sensors
	public static final AHRS kNavX						= new AHRS(new SerialPort(57600, SerialPort.Port.kMXP));
	public static final Encoder kDriveLeftEncoder		= new Encoder(0,1);
	public static final Encoder kDriveRightEncoder		= new Encoder(2,3);
	public static final Ultrasonic kUltrasonic			= new Ultrasonic(4,5);
	
	// Gearboxes
	public static final Gearbox kDriveLeftGearbox		= new Gearbox(kDriveFrontLeft, kDriveBackLeft, kDriveLeftEncoder, kLeftShifter);
	public static final Gearbox kDriveRightGearbox		= new Gearbox(kDriveFrontRight, kDriveBackRight, kDriveRightEncoder, kRightShifter);
	
	// Subsystems
	public static final Drive kDrive = new Drive(kDriveLeftGearbox, kDriveRightGearbox, kNavX);
}
