package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.framework.Context;
import com.team766.frc2022.Robot;

public class EStop extends Procedure{
	public void run(Context context) {
		context.takeOwnership(Robot.belts);
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.elevator);
		context.takeOwnership(Robot.intake);
		context.takeOwnership(Robot.shooter);
		
		Robot.belts.stopBelts();
		Robot.drive.setArcadeDrivePower(0.0, 0.0);
		Robot.elevator.setElevatorPower(0.0);
		Robot.intake.stopIntake();
		Robot.shooter.stopShoot();
	}
}
