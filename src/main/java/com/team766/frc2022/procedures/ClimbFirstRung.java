package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.hal.RobotProvider;
import com.team766.library.ValueProvider;
import com.team766.config.ConfigFileReader;

public class ClimbFirstRung extends Procedure{
	public void run(Context context){
		context.takeOwnership(Robot.elevator);

		new RetractElevator().run(context);
		Robot.elevator.setArmsPower(1.0);
		Robot.elevator.setElevatorPowerUnrestricted(-1.0);
		context.waitFor(() -> Robot.elevator.getLimitSwitchBottom());
		Robot.elevator.setElevatorPowerUnrestricted(0.0);
	}
}
