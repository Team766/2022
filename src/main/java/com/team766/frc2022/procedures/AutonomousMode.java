package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.framework.Context;
import com.team766.logging.Category;
import com.ShooterVelociltyUtil;
import com.team766.controllers.PIDController;

public class AutonomousMode extends Procedure{
	public void run(Context context){
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.belts);
		context.takeOwnership(Robot.limelight);
		context.takeOwnership(Robot.shooter);

		Robot.drive.setArcadeDrivePower(-0.5, 0);
		context.waitForSeconds(0.25);
		Robot.drive.setArcadeDrivePower(0,0);
		context.waitForSeconds(0.5);
		log(""+3);
		if (3 != 0){
			double power = ShooterVelociltyUtil.computeVelocityForDistance(3);
			Robot.shooter.setVelocity(power);
			if(power == 0.0) {
				log("out of range");
			} else {
				log("set velocity to " + power);
			}
		}
		context.waitForSeconds(5);
		Robot.belts.startBelts();
		context.waitForSeconds(2);
		Robot.belts.stopBelts();
		Robot.shooter.stopShoot();

		// Robot.drive.setArcadeDrivePower(-0.5, 0);
		// context.waitForSeconds(0.5);
		// Robot.drive.setArcadeDrivePower(-0.5, 0);
	}
}
