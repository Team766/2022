package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.framework.Context;
import com.team766.logging.Category;
import com.team766.controllers.PIDController;

public class PreciseTurn extends Procedure {
    PIDController controller;
    double final_angle;

    public PreciseTurn(double angle){
		loggerCategory = Category.AUTONOMOUS; //Delcare here since we aren't creating it, just changing its value
        this.final_angle = angle;
	}

    public void run(Context context){
        context.takeOwnership(Robot.drive);
        Robot.drive.resetGyro();
        controller = new PIDController(Robot.drive.P_turn,Robot.drive.I_turn,Robot.drive.D_turn, Robot.drive.min_turn, Robot.drive.max_turn, Robot.drive.threshold_turn);
        controller.setSetpoint(final_angle);
        while (controller.isDone() == false){
            controller.calculate(Robot.drive.getGyroAngle(),true);
            double turn = controller.getOutput();
            Robot.drive.setArcadeDrivePower(0,turn);
            context.yield();
        }
        Robot.drive.setArcadeDrivePower(0, 0);
    }
}