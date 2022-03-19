package com.team766.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.team766.frc2022.procedures.Climb;
import com.team766.frc2022.procedures.SlowExtend;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AutonomousProcedure {
	Class<Climb> procedureClass();
}
