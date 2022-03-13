package com.team766.frc2022.procedures;

import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.library.ValueProvider;
import com.team766.logging.Category;

public class PIDElevator extends Procedure{
	private int goal;

	private ValueProvider<Double> elevatorP;
	private ValueProvider<Double> elevatorPIDLeniency;

	public PIDElevator(int target) {
		goal = target;
		elevatorP = ConfigFileReader.getInstance().getDouble("PIDclimber.elevatorP"); //1
		elevatorPIDLeniency = ConfigFileReader.getInstance().getDouble("PIDclimber.elevatorPIDLeniency"); //5
	}

	public void run (Context context) {
		context.takeOwnership(Robot.elevator);
		loggerCategory = Category.DRIVE;
		double startPos = Robot.elevator.getElevatorPosition();
		boolean running = true;
		double pos = startPos;
		if (goal != startPos){
			while (Math.abs(pos - goal) > elevatorPIDLeniency.get() && running) {
				pos = Robot.elevator.getElevatorPosition();
				if (pos - goal < 0 && (Robot.elevator.getElevatorBottom() - pos < Robot.elevator.getElevatorLeniency() || Robot.elevator.getLimitSwitchBottom())) {
					running = false;
				}
				if (pos - goal > 0 && (pos - Robot.elevator.getElevatorTop() < Robot.elevator.getElevatorLeniency() || Robot.elevator.getLimitSwitchTop())) {
					running = false;
				}
				Robot.elevator.setElevatorPower(elevatorP.get() / Math.abs(goal - startPos) * (pos - goal));
				context.yield();
			}
		}
	}

}
