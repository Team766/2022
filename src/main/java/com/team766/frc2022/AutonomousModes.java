package com.team766.frc2022;

import com.team766.framework.AutonomousProcedure;
import com.team766.frc2022.procedures.*;

public enum AutonomousModes {
	@AutonomousProcedure(procedureClass = PreciseTurn.class) PreciseTurn,
	@AutonomousProcedure(procedureClass = autoShoot.class) autoShoot,
	@AutonomousProcedure(procedureClass = DoNothing.class) DoNothing
}
