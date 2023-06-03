package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.hal.RobotProvider;
import com.team766.library.ValueProvider;
import com.team766.config.ConfigFileReader;

public class ClimbRung extends Procedure {
	public void run (Context context) {
		context.takeOwnership(Robot.elevator);
		//loggerCategory = Category.ELEVATOR;


		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 3).run(context);

		double startTime = RobotProvider.instance.getClock().getTime();
		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39 || RobotProvider.instance.getClock().getTime() - startTime >= 5);
//41.56 = no swing, arms retract | 42.58 = no swing, arms extend | 22.8 = rung bottom contact | 20.12 = rung top contact(piston is in between) | 

		new ExtendElevator().run(context);

		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(1);
//		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 2).run(context);
//		context.waitForSeconds(0.5);
		new RetractElevator().run(context);
		Robot.elevator.setArmsPower(1.0);
		Robot.elevator.setElevatorPowerUnrestricted(-1.0);
		context.waitFor(() -> Robot.elevator.getLimitSwitchBottom());
		Robot.elevator.setElevatorPowerUnrestricted(0.0);








/*
		// Initial Arms Up
		log("First arms up, initialization");
		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.5);
		// Pull the Robot Up
		log("Pull robot up");
		Robot.elevator.setElevatorPower(-1);
		while(Math.abs(Robot.elevator.getElevatorPosition()) > 1300 ){
			if(Robot.elevator.getLimitSwitchBottom()) break;
			context.waitForSeconds(0.2);
		};
		Robot.elevator.setElevatorPower(0);
		//Arms Grab Bar
		log("Arms grab bar");
		Robot.elevator.setArmsPower(1.0);
		context.waitForSeconds(0.5);
		
		for (int i = 0; i < 2; i++) {

			//Initial Extension of Elevator to Combat Wonky Physics
			/*Robot.elevator.setElevatorPower(0.5);
			context.waitForSeconds(0.1);
			Robot.elevator.setElevatorPower(-0.5);
			context.waitForSeconds(0.2);
			Robot.elevator.setElevatorPower(0.0);
			context.waitForSeconds(1.5);
			*/

			//Extension of Elevator
			/*
			log("Extend elevator");
			Robot.elevator.setElevatorPower(0.4);
			while(Math.abs(Robot.elevator.getElevatorPosition()) < 17500 ){
				if(Robot.elevator.getLimitSwitchTop()) break;
				context.waitForSeconds(0.2);
			};
			Robot.elevator.setElevatorPower(0);
			log("Extend arms");
			Robot.elevator.setArmsPower(-1.0);
			context.waitForSeconds(1);
			log("Retract elevator");
			Robot.elevator.setElevatorPower(-1);
			while(Math.abs(Robot.elevator.getElevatorPosition()) > 1300 ){
				if(Robot.elevator.getLimitSwitchBottom()) break;
				context.waitForSeconds(0.2);
			};
			Robot.elevator.setElevatorPower(0);
			log("Retract arms");
			Robot.elevator.setArmsPower(1.0);
			context.waitForSeconds(1.0);
		}
*/



	}
}