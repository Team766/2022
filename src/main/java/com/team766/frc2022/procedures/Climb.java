package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.hal.RobotProvider;
import com.team766.library.ValueProvider;
import com.team766.config.ConfigFileReader;

public class Climb extends Procedure{

	public void run(Context context){
		context.takeOwnership(Robot.elevator);

		context.takeOwnership(Robot.gyro);
		Robot.gyro.resetGyro();
		//context.releaseOwnership(Robot.gyro);

		Robot.elevator.setArmsPower(-1.0);
		log("retracting arms");
		//log("Extending Elevator");
		//new ExtendElevator().run(context);
		//Robot.elevator.resetElevatorPosition();
		//log("Reset elevator encoder");

		new RetractElevator().run(context);
		//Robot.elevator.setElevatorPosition(Robot.elevator.getElevatorBottom() - (int) ConfigFileReader.getInstance().getDouble("PIDclimber.elevatorPIDLeniency").get().doubleValue());
		Robot.elevator.setArmsPower(1.0);
		Robot.elevator.setElevatorPowerUnrestricted(-1.0);
		context.waitFor(() -> Robot.elevator.getLimitSwitchBottom());
		Robot.elevator.setElevatorPowerUnrestricted(0.0);
//Rung 1
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
//Rung 2
		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 3).run(context);

		double startTimeTwo = RobotProvider.instance.getClock().getTime();
		context.waitFor(() -> Robot.gyro.getGyroPitch() >= 39 || RobotProvider.instance.getClock().getTime() - startTimeTwo >= 5);

		new ExtendElevator().run(context);
		context.waitForSeconds(1.0);

		Robot.elevator.setArmsPower(-1.0);
		context.waitForSeconds(0.5);
//		new PIDElevator(Robot.elevator.getElevatorBottom() + (Robot.elevator.getElevatorTop() - Robot.elevator.getElevatorBottom()) / 2).run(context);
//		context.waitForSeconds(0.5);
		new RetractElevator().run(context);
		Robot.elevator.setArmsPower(1.0);
		Robot.elevator.setElevatorPowerUnrestricted(-1.0);
		context.waitFor(() -> Robot.elevator.getLimitSwitchBottom());
		context.waitForSeconds(0.5);
		Robot.elevator.setElevatorPowerTrueUnrestricted(-0.3);
		//Robot.elevator.setArmsPower(1.0); let it slide down
		context.waitForSeconds(1.0);
		Robot.elevator.setElevatorPowerUnrestricted(0.0);
		
	}
}