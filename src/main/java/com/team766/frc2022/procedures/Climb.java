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
		log("retracting arms");
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
//41.56 = no swing, arms retract | 42.58 = no swing, arms extend | 22.8 = rung bottom contact | 20.12 = rung top contact(piston is in between) | 

		new ExtendElevator().run(context);

		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(0.5);
//Rung 2
		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 3).run(context);

		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39);

		new ExtendElevator().run(context);
		context.waitForSeconds(0.2);

		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.5);
		new RetractElevator().run(context);
		context.waitForSeconds(0.1);
		Robot.elevator.setElevatorPower(-0.25);
		context.waitForSeconds(2);
		Robot.elevator.setElevatorPower(0);
		//Robot.elevator.setArmsPower(1.0); let it slide down
	}
}