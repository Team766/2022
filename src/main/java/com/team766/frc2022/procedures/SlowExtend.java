package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;


public class SlowExtend extends Procedure{
	public void run(Context context){
		context.takeOwnership(Robot.elevator);
		Robot.elevator.setElevatorPower(-0.3);
		context.waitForSeconds(1);
		Robot.elevator.setElevatorPower(0);

	}
}
