package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.framework.Context;
import com.team766.logging.Category;
import com.team766.config.ConfigFileReader;

public class DoNothing extends Procedure {

	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		loggerCategory = Category.AUTONOMOUS;
		double power = ConfigFileReader.getInstance().getDouble("drive.turn.minpower").valueOr(0.0);
		Robot.drive.setArcadeDrivePower(0, power,true);
		log(""+power);
	}
	
}