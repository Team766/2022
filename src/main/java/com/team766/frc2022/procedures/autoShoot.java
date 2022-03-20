package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.ShooterVelociltyUtil;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;
import com.team766.logging.Category;


public class autoShoot extends Procedure{
	public void run(Context context) {
		context.takeOwnership(Robot.shooter);
		context.takeOwnership(Robot.belts);
		loggerCategory = Category.AUTONOMOUS;

		double distance = Robot.limelight.limelightFilter(context);
		double power = ShooterVelociltyUtil.computeVelocityForDistance(distance);

		Robot.shooter.setVelocity(power);
		context.waitForSeconds(10);
		Robot.belts.startBelts();
		context.waitForSeconds(5);
		Robot.belts.stopBelts();
		Robot.shooter.stopShoot();
        
	}
}

