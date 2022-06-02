package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.logging.Category;

public class DefenseCross extends Procedure {

	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		loggerCategory = Category.PROCEDURES;

		Robot.drive.setFrontLeftAngle(45);
		Robot.drive.setFrontRightAngle(45);
		Robot.drive.setBackLeftAngle(45);
		Robot.drive.setBackRightAngle(45);
		

	}
}