package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;

public class Climb extends Procedure{

	public void run(Context context){
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.climberEx);

		context.takeOwnership(Robot.climber);
		Robot.climber.resetGyro();
		context.releaseOwnership(Robot.climber);

		new RetractElevator().run(context);
		Robot.climberEx.setArmsPusher(true);
		context.waitForSeconds(0.2);
//Rung 1
		LaunchedContext extendElevatorCall = context.startAsync(new ExtendElevator());
		
		context.waitFor(extendElevatorCall);
		Robot.climberEx.setArmsPusher(false);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		Robot.climberEx.setArmsPusher(true);
		context.waitForSeconds(0.2);
//Rung 2
		extendElevatorCall = context.startAsync(new ExtendElevator());

		context.waitFor(extendElevatorCall);
		Robot.climberEx.setArmsPusher(false);
		context.waitForSeconds(0.2);
		new RetractElevator().run(context);

		Robot.climberEx.setArmsPusher(true);
//Rung 3
	}
}