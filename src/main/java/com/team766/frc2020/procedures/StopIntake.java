package com.team766.frc2020.procedures;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2020.Robot;

public class StopIntake extends Procedure {
	public void run(Context context) {
		context.takeOwnership(Robot.intakeArm);
		Robot.intakeArm.setPusher(false);

		context.takeOwnership(Robot.intakeWheels);
		Robot.intakeWheels.setWheels(0.0);
	}
}