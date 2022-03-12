package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;

public class RetractElevator extends Procedure{
	public void run(Context context){
		context.takeOwnership(Robot.climber);

		Robot.climber.setElevatorMotor(-0.5);
		context.waitFor(() -> Robot.climber.getEncoderDistance() >= -2000);
		Robot.climber.setElevatorMotor(0.0);

/*
		try{
			Robot.climber.setElevatorMotor(-1.0);
			context.waitFor(() -> Robot.climber.getEncoderDistance() <= 60);
			Robot.climber.setElevatorMotor(0.0);
			

			while(true){
				if(Robot.climber.getEncoderDistance() > 40){
					Robot.climber.setElevatorMotor(-0.4);
					context.waitFor(() -> Robot.climber.getEncoderDistance() <= 40);
					Robot.climber.setElevatorMotor(0.0);
				}
				context.yield();
			}
		}finally{
			Robot.climber.setElevatorMotor(0.0);
		}
*/
	}
}