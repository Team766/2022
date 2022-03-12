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
		context.releaseOwnership(Robot.gyro);

		new RetractElevator().run(context);
		new ArmsControl(true).run(context);
		context.waitForSeconds(0.2);
//Rung 1
		LaunchedContext extendElevatorCall = context.startAsync(new ExtendElevator());
		
		context.waitFor(extendElevatorCall);
		new ArmsControl(false).run(context);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		new ArmsControl(true).run(context);
		context.waitForSeconds(0.2);
//Rung 2
		extendElevatorCall = context.startAsync(new ExtendElevator());

		context.waitFor(extendElevatorCall);
		new ArmsControl(false).run(context);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		new ArmsControl(true).run(context);
//Rung 3
	}
}