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
import java.util.*;

public class BetterLimelight extends Procedure {
    PIDController controller;
    double stopdist = ConfigFileReader.getInstance().getDouble("limelight.dist").valueOr(0.0); //distance in front of reflective tape we want to be; this is actually the distance from robot to the center of the hub at 5000 power

    public BetterLimelight(){
		loggerCategory = Category.AUTONOMOUS; //Delcare here since we aren't creating it, just changing its value
	}

    public void run(Context context){
        context.takeOwnership(Robot.limelight);
        ArrayList<Double> list = new ArrayList<Double>();
        double prev_time = RobotProvider.instance.getClock().getTime();
        double turnangle = 0; //actual angle that we filter out
        double distance = 0;

        while (true){ //filters out x offset
            double cur_time = RobotProvider.instance.getClock().getTime();
            double angle = Robot.limelight.horizontalOffset();
            if (angle != 0){
                list.add(angle);
            }
            if (cur_time-prev_time >= 0.3){
                Collections.sort(list);
                if (list.isEmpty()){
                    log("Stop trolling. There is no target in this direction.");
                } else {
                    turnangle = list.get(list.size()/2);
                    list.clear();
                }
                break;
            }
            context.yield();
        }
        log(""+turnangle);

       // new PreciseTurn(turnangle).run(context);
        
        while (true){ //filters out distance
            double cur_time = RobotProvider.instance.getClock().getTime();
            double dist = Robot.limelight.distanceFromTarget();
            if (dist != 0){
                list.add(dist);
            }
            if (cur_time-prev_time >= 0.3){
                Collections.sort(list);
                if (list.isEmpty()){
                    log("Stop trolling. There is no target in this direction.");
                } else {
                    distance = list.get(list.size()/2);
                }
                break;
            }
            context.yield();
        }

        log(""+(distance-stopdist));
        new PreciseDrive(distance-stopdist).run(context);
        
        context.startAsync(new activateShooter());
        context.waitForSeconds(2);
        Robot.belts.startBelts();
        context.waitForSeconds(1);
        Robot.shooter.stopShoot();
        Robot.belts.stopBelts();
    }
    /* 1. Drive and Turn at the same time.
       2. Better filtering algorithm.
       3. Make a helper method.
       */
}