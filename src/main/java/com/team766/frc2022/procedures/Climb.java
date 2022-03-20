package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;

public class Climb extends Procedure{

	public void run(Context context){
		context.takeOwnership(Robot.elevator);

		context.takeOwnership(Robot.gyro);
		Robot.gyro.resetGyro();
		//context.releaseOwnership(Robot.gyro);

		Robot.elevator.setArmsPower(-1.0);
		//log("Extending Elevator");
		//new ExtendElevator().run(context);
		//Robot.elevator.resetElevatorPosition();
		//log("Reset elevator encoder");

		new RetractElevator().run(context);
		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(0.2);
//Rung 1
		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 3).run(context);

		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39);

		new ExtendElevator().run(context);

		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(0.2);
//Rung 2
		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 3).run(context);

		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39);

		new ExtendElevator().run(context);

		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		Robot.elevator.setArmsPower(1.0);
	}
}