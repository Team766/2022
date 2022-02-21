package com.team766.frc2022.procedures;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;

public class PIDElevator extends Procedure{
	private int goal;
	private double pValue;
	private double leniency;

	private ValueProvider<Double> elevatorP;
	private ValueProvider<Double> elevatorPIDLeniency;

	public PIDElevator(int target) {
		goal = target;
		pValue = ConfigFileReader.getInstance().getDouble("PIDclimber.elevatorP").get(); //1
		leniency = ConfigFileReader.getInstance().getDouble("PIDclimber.elevatorPIDLeniency").get(); //5
	}

	public void run (Context context) {
		context.takeOwnership(Robot.elevator);
		loggerCategory = Category.DRIVE;
		double startPos = Robot.elevator.getElevatorPosition();
		if (goal != startPos){
			while (Math.abs(Robot.elevator.getElevatorPosition() - goal) > leniency) {
				Robot.elevator.setElevatorPower(pValue / (goal - startPos) * (goal - Robot.elevator.getElevatorPosition()));
				context.yield();
			}
		}
	}

}
