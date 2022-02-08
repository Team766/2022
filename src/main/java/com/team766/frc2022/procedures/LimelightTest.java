package com.team766.frc2022.procedures;

import com.team766.framework.Procedure;
import com.team766.framework.Scheduler;
import com.team766.frc2022.Robot;
import com.team766.hal.MyRobot;
import com.team766.hal.RobotProvider;
import com.team766.hal.mock.TestRobotProvider;
import com.team766.EntryPoint;
import com.team766.config.ConfigFileReader;
import com.team766.framework.Context;
import com.team766.logging.Category;

public class LimelightTest extends Procedure{
	public LimelightTest(){
		loggerCategory = Category.AUTONOMOUS; //Delcare here since we aren't creating it, just changing its value
	}

	public void run(Context context){ //Contexts allow procedures to run at the same time, each under a different context (2 contexts in one function useless as it goes in the function order)
		context.takeOwnership(Robot.limelight);
		context.takeOwnership(Robot.drive);
		/*
		while (true){
			log("Distance: "+Robot.limelight.distanceFromTarget()+". Vertical Offset: "+Robot.limelight.verticalOffset());
			context.waitForSeconds(1);
		}
		*/
		double tx = Robot.limelight.horizontalOffset();
		log(tx+"First");
		while ((Math.abs(tx) > 0.3) ){
			tx = Robot.limelight.horizontalOffset();
			log(tx+"NotFirst");
			if (Math.abs(tx) >= 3.0 || tx == 0.0){
				log("A");
				if (tx > 0){
					Robot.drive.setArcadeDrivePower(0, 0.7);
				} else {
					Robot.drive.setArcadeDrivePower(0, -0.7);
				}
			} else if (Math.abs(tx) >= 1.0){
				log("B");
				if (tx > 0){
					Robot.drive.setArcadeDrivePower(0, 0.3);
				} else {
					Robot.drive.setArcadeDrivePower(0, -0.3);
				}
			} else {
				log("C");
				if (tx > 0){
					Robot.drive.setArcadeDrivePower(0, 0.1);
				} else {
					Robot.drive.setArcadeDrivePower(0, -0.1);
				}
			}
		}
		Robot.drive.setArcadeDrivePower(0, 0);
	}
}
