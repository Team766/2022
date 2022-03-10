package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;

public class StartShooter extends Procedure {
	public void run(Context context) {
		context.takeOwnership(Robot.shooter);

		Robot.shooter.startShoot();
	}
}