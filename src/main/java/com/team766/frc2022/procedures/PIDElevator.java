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
		
		loggerCategory = Category.ELEVATOR;
		double startPos = Robot.elevator.getElevatorPosition();
		boolean running = true;
		double pos = startPos;
		double power = 0.0;
		log("Starting PID. Goal: " + goal + " StartPos: " + startPos);
		if (goal != startPos){
			///log("Initial Condition (Should be true): " + Boolean.toString(Math.abs(pos - goal) > elevatorPIDLeniency.get() && running));
			while (Math.abs(pos - goal) > elevatorPIDLeniency.get() && running) {
				//log("" + Double.toString(Robot.elevator.getElevatorBottom() - pos));
				pos = Robot.elevator.getElevatorPosition();
				if (pos - goal < 0 && (Robot.elevator.getElevatorBottom() - pos < elevatorPIDLeniency.get())) {
					running = false;
					//log("Elevator Within Leniency: Moving Down");
				}
				if (pos - goal < 0 && (Robot.elevator.getLimitSwitchBottom())) {
					running = false;
					//log("Hit Bottom Limit Switch: Moving Down");
				}
				if (pos - goal > 0 && (pos - Robot.elevator.getElevatorTop() < elevatorPIDLeniency.get())) {
					running = false;
					//log("Elevator Within Leniency: Moving Up");
				}
				if (pos - goal > 0 && (Robot.elevator.getLimitSwitchTop())) {
					running = false;
					//log("Hit Top Limit Switch: Moving Up");
				}

					power = elevatorP.get() / Math.abs(goal - startPos) * (pos - goal);
				if (power < 0) {
					power = -1 * (Robot.elevator.getElevatorPower() /** Robot.elevator.getElevatorPower()*/);
				}
				log("Elevator: " + pos + " Goal: " + goal);
				//log("Power: " + ((1 / Robot.elevator.getElevatorPower()) * Math.max(Math.abs(power), 0.4) * Math.abs(power) / power));
				Robot.elevator.setElevatorPowerUnrestricted((1 / Robot.elevator.getElevatorPower()) * Math.max(Math.abs(power), 0.4) * Math.abs(power) / power);
				//context.yield();
			}
			Robot.elevator.setElevatorPower(0.0);
			log("Ending PID!");
			log("If no other end message logged, Elevator Within Leniency");
		}
	}

}
