package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;

public class ClimbRung extends Procedure {
	public void run (Context context) {

		context.takeOwnership(Robot.elevator);

		// Initial Arms Up
		Robot.elevator.setArmsPower(-1.0);

		// Pull the Robot Up
		Robot.elevator.setElevatorPower(-1.0);
		context.waitForSeconds(1.5);

		//Arms Grab Bar
		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(1.5);

		//Initial Extension of Elevator to Combat Wonky Physics
		Robot.elevator.setElevatorPower(0.5);
		context.waitForSeconds(0.1);
		Robot.elevator.setElevatorPower(-0.5);
		context.waitForSeconds(0.2);
		Robot.elevator.setElevatorPower(0.0);
		context.waitForSeconds(1.5);

		//Extension of Elevator
		Robot.elevator.setElevatorPower(1.0);
		context.waitForSeconds(5.0);

		//Retraction of Arms in Order for Elevator to Grab Bar
		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(1.5);

		//Retraction of Elevator to Prevent Arms from Getting Stuck
		Robot.elevator.setElevatorPower(-1.0);
		context.waitForSeconds(1.5);
		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(1.5);
		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(1.5);
	}
}