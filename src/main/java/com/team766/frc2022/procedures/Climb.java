package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;

public class Climb extends Procedure{

	public void run(Context context){
		context.takeOwnership(Robot.drive);
		context.takeOwnership(Robot.climberEx);

		context.takeOwnership(Robot.climber);
		Robot.climber.resetGyro();
		context.releaseOwnership(Robot.climber);

		LaunchedContext extendElevatorCall = context.startAsync(new ExtendElevator());

		context.waitForSeconds(0.5);

		Robot.drive.setArcadeDrivePower(0.5, 0.0);

		context.waitForSeconds(1.5);

		Robot.drive.setArcadeDrivePower(0.0, 0.0);
		extendElevatorCall.stop();
		LaunchedContext retractElevatorCall = context.startAsync(new RetractElevator());

		context.waitForSeconds(1.5);

		Robot.climberEx.setArmsPusher(true);

		retractElevatorCall.stop();

		context.waitForSeconds(0.5);

		extendElevatorCall = context.startAsync(new ExtendElevator());

		context.waitForSeconds(0.1);

		extendElevatorCall.stop();
//good^
		retractElevatorCall = context.startAsync(new RetractElevator());

		context.waitFor(() -> Robot.climber.getGyroAngle() <= -60.0);

		retractElevatorCall.stop();

		extendElevatorCall = context.startAsync(new ExtendElevator());
//copy end
		context.waitFor(() -> Robot.climber.getGyroAngle() >= -40.0);

		Robot.climberEx.setArmsPusher(false);

		context.waitForSeconds(0.2);

		extendElevatorCall.stop();
		retractElevatorCall = context.startAsync(new RetractElevator());

		context.waitForSeconds(0.2);

		retractElevatorCall.stop();

		context.waitForSeconds(0.2);

		retractElevatorCall = context.startAsync(new RetractElevator());

//copy end 2

		context.waitForSeconds(1.0);

		Robot.climberEx.setArmsPusher(true);

		retractElevatorCall.stop();

		extendElevatorCall = context.startAsync(new ExtendElevator());

		context.waitForSeconds(0.1);

		extendElevatorCall.stop();

		retractElevatorCall = context.startAsync(new RetractElevator());

		context.waitFor(() -> Robot.climber.getGyroAngle() <= -50.0);

		retractElevatorCall.stop();

		extendElevatorCall = context.startAsync(new ExtendElevator());

		context.waitFor(() -> Robot.climber.getGyroAngle() >= -40.0);

		Robot.climberEx.setArmsPusher(false);

		context.waitForSeconds(0.2);

		extendElevatorCall.stop();
		retractElevatorCall = context.startAsync(new RetractElevator());

		context.waitForSeconds(0.2);

		retractElevatorCall.stop();

		









/*
		Robot.climber.setElevatorMotor(1.0);

		context.waitForSeconds(1.0);

		Robot.drive.setArcadeDrivePower(0.5, 0.0);

		context.waitForSeconds(1.0);

		Robot.climber.setElevatorMotor(-1.0);
		Robot.drive.setArcadeDrivePower(0.0, 0.0);

		context.waitForSeconds(1.0);

		Robot.climber.setArmsPusher(true);

		context.waitForSeconds(1.0);

		Robot.climber.setElevatorMotor(1.0);

		context.waitForSeconds(0.1);

		Robot.climber.setElevatorMotor(-1.0);

		context.waitForSeconds(0.9);

		Robot.climber.setElevatorMotor(1.0);

		context.waitForSeconds(1.5);

		Robot.climber.setElevatorMotor(-1.0);

		context.waitForSeconds(1.0);

		Robot.climber.setArmsPusher(false);
		Robot.climber.setElevatorMotor(1.0);

		context.waitForSeconds(1.0);

		Robot.climber.setElevatorMotor(-0.5);

		context.waitForSeconds(1.0);

		Robot.climber.setArmsPusher(true);

		context.waitForSeconds(1.3);

		Robot.climber.setElevatorMotor(1.0);

		context.waitForSeconds(2.0);

		Robot.climber.setArmsPusher(false);

		context.waitForSeconds(0.5);
		
		Robot.climber.setElevatorMotor(-1.0);
*/
		
	}
}