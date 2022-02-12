package com.team766.frc2020.procedures;

import com.team766.framework.Procedure;
import com.team766.logging.Category;
import com.team766.framework.Context;

public class Pickup extends Procedure {

	public Pickup() {
		loggerCategory = Category.AUTONOMOUS;
	}

	public void run(Context context) {
		new StartIntake().run(context);
		new DriveStraight().run(context);
		log("Started");
		new StopIntake().run(context);
		log("Stopped");
		new DriveBackwards().run(context);
		log("Backwards");
		new TurnRight().run(context);
		log("Turned");
		context.waitForSeconds(1);
		for (int i = 0; i < 5; i++){
			new Launch().run(context);
			log("Launched");
		}
	}
}