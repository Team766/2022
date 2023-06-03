package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.framework.Context;
import com.team766.logging.Category;
import com.ShooterVelociltyUtil;
import com.team766.controllers.PIDController;

public class AutonomousMode extends Procedure{
	public void run(Context context){
		loggerCategory = Category.AUTONOMOUS;

		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.belts);
		context.takeOwnership(Robot.limelight);
		context.takeOwnership(Robot.shooter);
		context.takeOwnership(Robot.elevator);

		Robot.drive.setArcadeDrivePower(-0.3, 0);
		context.waitForSeconds(0.3);
		Robot.drive.setArcadeDrivePower(0,0);
		log("Drive done");
		context.waitForSeconds(2);
		// double distance = Robot.limelight.limelightFilter(context);
		// log(""+distance);
		double power = ShooterVelociltyUtil.computeVelocityForDistance(3.141592);
		log(""+power);
		Robot.shooter.setVelocity(power);
		context.waitForSeconds(5);
		Robot.belts.startBelts();
		context.waitForSeconds(2);
		Robot.belts.stopBelts();
		Robot.shooter.stopShoot();
		Robot.elevator.setArmsPower(1.0);

		// Robot.drive.setArcadeDrivePower(-0.5, 0);
		// context.waitForSeconds(0.5);
		// Robot.drive.setArcadeDrivePower(-0.5, 0);
	}
}