package com.team766.frc2022;

import com.team766.framework.AutonomousProcedure;
import com.team766.frc2022.procedures.*;

public enum AutonomousModes {
	@AutonomousProcedure(procedureClass = Climb.class)Climb
	//@AutonomousProcedure(procedureClass = ExtendElevator.class)ExtendElevator

}
