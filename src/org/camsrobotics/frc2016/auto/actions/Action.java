package org.camsrobotics.frc2016.auto.actions;

import org.camsrobotics.frc2016.HardwareAdapter;
import org.camsrobotics.frc2016.subsystems.Drive;
import org.camsrobotics.frc2016.subsystems.Intake;
import org.camsrobotics.frc2016.subsystems.Lifter;
import org.camsrobotics.frc2016.subsystems.Shooter;

/**
 * It does a thing
 * 
 * @author Wesley
 *
 */
public abstract class Action {
	protected Drive drive = HardwareAdapter.kDrive;
	
	public abstract boolean isFinished();
	
	public abstract void update();
	
	public abstract void disable();
	
	public abstract void start();
}
