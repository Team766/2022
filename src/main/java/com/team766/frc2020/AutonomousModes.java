package com.team766.frc2020;

import com.team766.framework.AutonomousProcedure;
import com.team766.frc2020.procedures.*;

public enum AutonomousModes {
	@AutonomousProcedure(procedureClass = FollowLine.class)
	FollowLine,
	@AutonomousProcedure(procedureClass = TurnAngle.class)
	TurnAngle,
	@AutonomousProcedure(procedureClass = DriveDistance.class)
	DriveDistance,
	@AutonomousProcedure(procedureClass = Pickup.class)
	Pickup,
	@AutonomousProcedure(procedureClass = Launch.class)
	Launch,
	@AutonomousProcedure(procedureClass = DriveSquare.class)
	DriveSquare,
	@AutonomousProcedure(procedureClass = TurnRight.class)
	TurnRight,
	@AutonomousProcedure(procedureClass = DriveStraight.class)
	DriveStraight
}
