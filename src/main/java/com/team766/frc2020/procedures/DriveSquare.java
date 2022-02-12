package com.team766.frc2020.procedures;

import com.team766.framework.Procedure;
import com.team766.logging.Category;
import com.team766.framework.Context;

public class DriveSquare extends Procedure {

	public DriveSquare() {
		loggerCategory = Category.AUTONOMOUS;
	}

	public void run(Context context) {
		for (int i = 0; i < 4; i++) {
			new DriveDistance().run(context);
			log("Driven Straight");
			new TurnAngle().run(context);
			log("Turn Corner");
		}
	}
}