package com.team766.frc2022;

import com.team766.framework.AutonomousProcedure;
import com.team766.frc2022.procedures.*;

public enum AutonomousModes {
	// @AutonomousProcedure(procedureClass = ClimbRung.class) ClimbRung,
	// @AutonomousProcedure(procedureClass = Climb.class)Climb
	//@AutonomousProcedure(procedureClass = Climb.class)Climb
	@AutonomousProcedure(procedureClass = SlowExtend.class)SlowExtend
	//@AutonomousProcedure(procedureClass = ExtendElevator.class)ExtendElevator
}
