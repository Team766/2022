package com.team766.frc2022.procedures;

import javax.swing.text.AsyncBoxView;
import com.team766.framework.Context;
import com.team766.framework.LaunchedContext;
import com.team766.framework.Procedure;
import com.team766.frc2022.Robot;
import com.team766.frc2022.mechanisms.Elevator;

public class ArmsControl extends Procedure{
	private boolean position;
	
	public ArmsControl(boolean posIN){
		position = posIN;
	}
	
	public void run(Context context){
		context.takeOwnership(Robot.elevator);
		
		if(position == true){
			Robot.elevator.setArmsPower(1.0);
		}else{
			Robot.elevator.setArmsPower(-1.0);
		}
		
	}
}
