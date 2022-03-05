package com.team766.frc2022;

import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;

public class Constants{

    public static final double ksVolts = 0.93067;
    public static final double kvVoltSecondsPerMeter = 0.47559;
    public static final double kaVoltSecondsSquaredPerMeter = 0.38576;
    
    // Example value only - as above, this must be tuned for your drive!
    public static final double kPDriveVel = 0; // 4.6185;
    public static final double kMaxSpeedMetersPerSecond = 3;
    public static final double kMaxAccelerationMetersPerSecondSquared = 3;
    public static final double kRamseteB = 2;
    public static final double kRamseteZeta = 0.7;

    public static final double kTrackwidthMeters = 0.66;
    public static final DifferentialDriveKinematics kDriveKinematics = new DifferentialDriveKinematics(kTrackwidthMeters);
    
    public Constants(){
    }
}