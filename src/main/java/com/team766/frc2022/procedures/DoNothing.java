package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.logging.Category;

public class DoNothing extends Procedure {

	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		loggerCategory = Category.AUTONOMOUS;
		Robot.drive.setArcadeDrivePower(0, 0);
	}
	
}