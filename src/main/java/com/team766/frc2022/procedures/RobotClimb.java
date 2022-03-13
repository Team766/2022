package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.logging.Category;

public class RobotClimb extends Procedure{
	public void run (Context context) {
		context.takeOwnership(Robot.elevator);
		loggerCategory = Category.ELEVATOR;

		
	}
}
