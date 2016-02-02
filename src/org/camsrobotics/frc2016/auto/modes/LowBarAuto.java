package org.camsrobotics.frc2016.auto.modes;

import org.camsrobotics.frc2016.auto.AutoMode;
import org.camsrobotics.frc2016.auto.actions.WaitForUltrasonicAction;
import org.camsrobotics.frc2016.auto.actions.WaitForVisionTargetAction;
import org.camsrobotics.frc2016.subsystems.Drive.DriveSignal;
import org.camsrobotics.frc2016.subsystems.controllers.DriveRotationController;
import org.camsrobotics.frc2016.subsystems.controllers.DriveStraightController;

/**
 * Lowbar and shoot
 * 
 * @author Wesley
 *
 */
public class LowBarAuto extends AutoMode {

	@Override
	public void run() {
		// Pass the Low Bar
		System.out.println("Strait .5");
		drive.setController(new DriveStraightController(3, .5));
		runAction(new WaitForUltrasonicAction(60, 15));
		
		// Turn until vision sees
		System.out.println("Rotate");
		drive.setController(new DriveRotationController(45, 0)); // Tolerance really does not matter
		runAction(new WaitForVisionTargetAction(15));
		
		// Stop
		System.out.println("stop");
		drive.driveOpenLoop(DriveSignal.kStop);
		
	}
	
}
