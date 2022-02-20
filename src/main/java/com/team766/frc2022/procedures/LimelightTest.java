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
import java.util.*;

public class LimelightTest extends Procedure{
	public LimelightTest(){
		loggerCategory = Category.AUTONOMOUS; //Delcare here since we aren't creating it, just changing its value
	}

	public double findMax(double num1, double num2){
		if (Math.abs(num1)>Math.abs(num2)){
			return num1;
		} else {
			return num2;
		}
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
		ArrayList<Double> list = new ArrayList<Double>();
		double k = 0.015;
		double minimum = 0.15; // 0.1 to bypass static friction, 0.05 to bypass kinetic friction
		double time = RobotProvider.instance.getClock().getTime();
		while (true){
			double tx = Robot.limelight.horizontalOffset();
			list.add(tx);
			// log(tx+""+Robot.limelight.areTargets());
			double temptime = RobotProvider.instance.getClock().getTime();
			if (temptime-time >= 0.3){
				Collections.sort(list);
				tx = list.get(list.size()/2);
				list = new ArrayList<Double>();
				time = temptime;
			} else {
				continue;
			}
			
			log(tx+"");

			if (tx == 0){
				Robot.drive.setArcadeDrivePower(0,0.3);
			} else {
				if (Math.abs(tx)>10){
					Robot.drive.setArcadeDrivePower(0,k*tx);
				} else {
					if (tx>0){
						Robot.drive.setArcadeDrivePower(0,minimum);
					} else {
						Robot.drive.setArcadeDrivePower(0,-1*minimum);
					}
				}
			}
			
		}
	}
}
