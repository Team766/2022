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

		new ArmsControl(false).run(context);
		Robot.elevator.setElevatorPowerUnrestricted(0.5);
		log("Set elevator power to 0.5");
		context.waitFor(() -> Robot.elevator.getLimitSwitchTop() == true);
		log("Hit limit switch");
		Robot.elevator.setElevatorPowerUnrestricted(0.0);
		log("set elevator power to 0");
		Robot.elevator.resetElevatorPosition();
		log("Reset elevator encoder");

		new RetractElevator().run(context);
		new ArmsControl(true).run(context);
		context.waitForSeconds(0.2);
//Rung 1
		new ExtendElevator(12000).run(context);

		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39);

		new ExtendElevator().run(context);

		new ArmsControl(false).run(context);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		new ArmsControl(true).run(context);
		context.waitForSeconds(0.2);
//Rung 2
		new ExtendElevator(12000).run(context);

		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39);

		new ExtendElevator().run(context);

		new ArmsControl(false).run(context);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		new ArmsControl(true).run(context);
//Rung 3
	}
}