package com;
import java.util.*;
import com.team766.logging.Category;
import com.team766.hal.RobotProvider;
import com.team766.logging.Logger;
import com.team766.logging.Severity;
import com.team766.framework.*;

public class ShooterVelociltyUtil {

	private static final double[] distances = { 2.5, 3.0, 3.5, 4.0, 4.5, 5.0};
	private static final double[] powers = {3450,3610,3900,4100,4300,4700};

	public ShooterVelociltyUtil(){
		
	}

	public static double computeVelocityForDistance(double distance) {
		/*
		ArrayList<Double> list1 = new ArrayList<Double>();
		ArrayList<Double> list2 = new ArrayList<Double>();

		list1.add(3.0);
		list2.add(3500.0);
		list1.add(3.5);
		list2.add(3800.0);
		list1.add(4.0);
		list2.add(4200.0);
		list1.add(4.5);
		list2.add(4500.0); */

		if (distance < distances[0] || distance > distances[distances.length-1]) {
			Logger.get(Category.LIMELIGHT).logRaw(Severity.INFO, "Velocity set to 0 in ShooterVelocityUtil");
			return 0.0;
		} else {
			return findPoint(distance);
		}
	}

	public static double findPoint(double distance){
		int temp = 0;
		double slope = 0;
		double answer = 0;
		for (int i = 1; i< distances.length; i++){ //temp is equal to the distance value that is greater than inputted distance
			if (distance<distances[i]){
				temp = i;
				break;
			}
		}
		answer += powers[temp-1];
		slope = (powers[temp]-powers[temp-1])/(distances[temp]-distances[temp-1]);
		answer += slope*(distance-distances[temp-1]);
		return answer;
	}
		
}
