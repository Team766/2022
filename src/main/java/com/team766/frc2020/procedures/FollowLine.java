package com.team766.frc2020.procedures;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2020.Robot;
 
public class FollowLine extends Procedure {
 
	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.lineSensors);

		boolean stuck = false;
		double speed = 1.0;
		double turn = 1.0;
		double stuckSpeed = 1.0;
		double stuckTurn = 1.0;
 
		while (true) {
			if (Robot.lineSensors.getLineSensorLeft()) {
				if (stuck && !Robot.lineSensors.getLineSensorCenter()) {
					Robot.drive.setArcadeDrivePower(0, -stuckTurn);
				}
				else {
					stuck = false;
					Robot.drive.setArcadeDrivePower(speed, -turn);
				}
			} else if (Robot.lineSensors.getLineSensorRight()) {
				if (stuck && !Robot.lineSensors.getLineSensorCenter()) {
					Robot.drive.setArcadeDrivePower(0, stuckTurn);
				}
				else {
					stuck = false;
					Robot.drive.setArcadeDrivePower(speed, turn);
				}
			} else if (Robot.lineSensors.getLineSensorCenter()) {
				Robot.drive.setArcadeDrivePower(stuck ? stuckSpeed : speed, 0);
			} else {
				Robot.drive.setArcadeDrivePower(stuck ? -stuckSpeed : -speed, 0);
				stuck = true;
			}
 
			context.yield();
		}
	}
 
}
