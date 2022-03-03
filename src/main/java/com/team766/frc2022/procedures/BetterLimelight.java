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
import com.team766.controllers.PIDController;

public class BetterLimelight extends Procedure{
    PIDController controller;

    public BetterLimelight(){
		loggerCategory = Category.AUTONOMOUS; //Delcare here since we aren't creating it, just changing its value
	}

    public void run(Context context){
        //controller = new PIDController()
    }
}