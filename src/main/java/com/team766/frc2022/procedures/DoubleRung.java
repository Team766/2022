package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;

public class DoubleRung extends Procedure {
	public void run (Context context) {

		context.takeOwnership(Robot.elevator);
		double lastAngle = -180;

		for (int i = 0; i < 2; i++) {
			// Initial Arms Up
			Robot.elevator.setArmsPower(-1.0);

			// Pull the Robot Up
			Robot.elevator.setElevatorPower(-1.0);
			context.waitFor(() -> Robot.elevator.getElevatorPosition() < 10);
			context.waitForSeconds(0.5);

			//Arms Grab Bar
			Robot.elevator.setArmsPower(1.0);
			

			//Initial Extension of Elevator to Combat Wonky Physics
			Robot.elevator.setElevatorPower(0.5);
			context.waitForSeconds(0.1);
			Robot.elevator.setElevatorPower(-0.5);
			context.waitForSeconds(0.5);
			lastAngle = Robot.gyro.getGyroPitch();
			while (Robot.gyro.getGyroPitch() <= lastAngle) {
				lastAngle = Robot.gyro.getGyroPitch();
				context.yield();
			}

			//Extension of Elevator
			context.waitForSeconds(0.2);
			Robot.elevator.setElevatorPower(1.0);
			Robot.elevator.setArmsPower(1.0);
			//context.waitForSeconds(1.5);
			context.waitFor(() -> Robot.elevator.getElevatorPosition() > 660);
			lastAngle = Robot.gyro.getGyroPitch();
			while (Robot.gyro.getGyroPitch() >= lastAngle) {
				lastAngle = Robot.gyro.getGyroPitch();
				context.yield();
			}
			//context.waitForSeconds(0.3);
			/*while (Robot.gyro.getGyroPitch() <= lastAngle) {
				lastAngle = Robot.gyro.getGyroPitch();
				context.yield();
			}
			while (Robot.gyro.getGyroPitch() >= lastAngle) {
				lastAngle = Robot.gyro.getGyroPitch();
				context.yield();
			}*/
			
			//Retraction of Arms in Order for Elevator to Grab Bar
			Robot.elevator.setArmsPower(1.0);
			context.waitForSeconds(0.3);
			Robot.elevator.setArmsPower(-1.0);
			context.waitForSeconds(1.0);
		}
	}
}