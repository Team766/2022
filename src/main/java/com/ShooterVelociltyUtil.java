package com;
import java.util.*;

public class ShooterVelociltyUtil {

	private static final double[] distances = { 2.5, 3.0, 3.5, 4.0, 4.5, 5.0,6.0,7.0 };
	private static final double[] powers = { 3400.0, 3400.0, 3600.0, 3750.0, 3900.0, 4050.0,4300.0,4600.0};

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
