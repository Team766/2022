package com.team766.frc2020.procedures;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2020.Robot;
import com.team766.logging.Category;

public class TurnAngle extends Procedure {
	public TurnAngle() {
		loggerCategory = Category.DRIVE;
	}

	public void run(Context context) {
		log("Starting turn!");
		context.takeOwnership(Robot.drive);
		Robot.drive.resetGyro();
		log("Angle: " + Robot.drive.getGyroAngle());
		Robot.drive.setDrivePower(0.25, -0.25);
		context.waitFor(() -> Robot.drive.getGyroAngle() <= -45);
		Robot.drive.setDrivePower(0.10, -0.10);
		context.waitFor(() -> Robot.drive.getGyroAngle() <= -80);
		Robot.drive.setDrivePower(0.01, -0.01);
		context.waitFor(() -> Robot.drive.getGyroAngle() <= -90);
		Robot.drive.setDrivePower(0.0, 0.0);
	}
}