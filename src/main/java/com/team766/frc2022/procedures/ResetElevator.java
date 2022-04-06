package com.team766.frc2022.procedures;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;

public class ResetElevator extends Procedure{

	public ResetElevator() {

	}

	public void run (Context context) {
		context.takeOwnership(Robot.elevator);
		loggerCategory = Category.ELEVATOR;
		try {
			while (!Robot.elevator.getLimitSwitchBottom()){
				Robot.elevator.setElevatorPowerUnrestricted(-0.6);
				context.yield();
			}
		} finally {
			Robot.elevator.setElevatorPowerUnrestricted(0.0);
		}
		Robot.elevator.resetElevatorPosition();
		log("Elevator reset!");
		
	}
}
