package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.framework.Context;
import com.team766.logging.Category;
import com.team766.config.ConfigFileReader;

public class DoNothingv2 extends Procedure {

	public void run(Context context) {
		context.takeOwnership(Robot.intake);
		Robot.intake.startArms();
	}
	
}