package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.frc2022.mechanisms.Elevator;

public class AutonDrive extends Procedure{
	public void run(Context context) {
		context.takeOwnership(Robot.drive);
		Robot.drive.setDrivePower(-1, -1);
		context.waitForSeconds(2);
		Robot.drive.setDrivePower(0 , 0);
	}
}

