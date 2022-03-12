package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.framework.Context;
import com.team766.logging.Category;
import com.team766.controllers.PIDController;
import com.team766.hal.RobotProvider;

public class PreciseDrive extends Procedure {
    PIDController controller;
    double final_distance;
    double max_velocity = 3;
    double accdist = 3; //distance for robot to be at max velocity

    public PreciseDrive(double distance){
		loggerCategory = Category.AUTONOMOUS; //Delcare here since we aren't creating it, just changing its value
        this.final_distance = distance;
	}

    public void run(Context context){
        context.takeOwnership(Robot.drive);
        Robot.drive.resetEncoders();
        controller = new PIDController(Robot.drive.P_drive,Robot.drive.I_drive,Robot.drive.D_drive, Robot.drive.min_drive, Robot.drive.max_drive, Robot.drive.threshold_drive);
        double targetvelocity = 0; //setpoint velocity
        double tempvelocity = 0; //current velocity
        double tempdist = 0; //distance now
        double dist = 0; //previous distance
        double temptime = 0; //time now
        double time = RobotProvider.instance.getClock().getTime(); //previous time
        while (Math.abs(tempdist-final_distance) <= 0.1){
            temptime = RobotProvider.instance.getClock().getTime();
            tempdist = Robot.drive.getEncoderDistance();
            if (temptime-time >= 0.1){
                tempvelocity = (tempdist-dist)/(temptime-time);
                if (tempdist <= final_distance/2){
                    targetvelocity = Math.min(max_velocity,(max_velocity/accdist)*tempdist);
                } else {
                    targetvelocity = Math.min(max_velocity,(max_velocity/accdist)*(final_distance-tempdist));
                }
                dist = tempdist;
                time = temptime;
                controller.setSetpoint(targetvelocity);
                controller.calculate(tempvelocity,true);
                double forward = controller.getOutput();
                Robot.drive.setArcadeDrivePower(forward, 0);
            }
            context.yield();
        }
        Robot.drive.setArcadeDrivePower(0, 0);
    }
    /*
    */
}