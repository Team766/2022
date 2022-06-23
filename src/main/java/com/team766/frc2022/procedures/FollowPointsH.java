package com.team766.frc2022.procedures;

import com.team766.framework.Context;
import com.team766.framework.Procedure;
import com.team766.frc2022.Point;

public class FollowPointsH extends Procedure {

	public void run(Context context) {
		Point[] pointList = {
			new Point(0, 0), 
			new Point(5, 0), 
			new Point(5, 5), 
			new Point(5, -5), 
			new Point(5, 0), 
			new Point(-5, 0), 
			new Point(-5, 5),
			new Point(-5, -5),
			new Point(-5, 0),
			new Point(0, 0)
		};
		new FollowPoints(pointList).run(context);
	}
	
}