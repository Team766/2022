package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.logging.Category;

public class ClimbRung extends Procedure {
	public void run (Context context) {
		context.takeOwnership(Robot.elevator);
		loggerCategory = Category.ELEVATOR;
		

		// Initial Arms Up
		log("First arms up, initialization");
		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.5);
		// Pull the Robot Up
		log("Pull robot up");
		Robot.elevator.setElevatorPower(-1.0);
		context.waitForSeconds(5);

		//Arms Grab Bar
		log("Arms grab bar");
		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(1.5);

		//Initial Extension of Elevator to Combat Wonky Physics
		/*Robot.elevator.setElevatorPower(0.5);
		context.waitForSeconds(0.1);
		Robot.elevator.setElevatorPower(-0.5);
		context.waitForSeconds(0.2);
		Robot.elevator.setElevatorPower(0.0);
		context.waitForSeconds(1.5);
		*/

		//Extension of Elevator
		log("Extend elevator");
		Robot.elevator.setElevatorPower(0.3);
		context.waitForSeconds(6.0);
		Robot.elevator.setElevatorPower(0);
		log("Extend arms");
		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(1.5);
		log("Retract elevator");
		Robot.elevator.setElevatorPower(-0.80);
		context.waitForSeconds(7.0);
		Robot.elevator.setElevatorPower(0);
		log("Retract arms");
		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(1.5);





	}
}