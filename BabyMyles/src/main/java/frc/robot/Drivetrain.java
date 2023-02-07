

package frc.robot;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drivetrain {

      private static DifferentialDrive driveTrain;
    
      private static CANSparkMax frontRightDrive;
      private static CANSparkMax frontLeftDrive;
      private static CANSparkMax backRightDrive;
      private static CANSparkMax backLeftDrive;

      private static float speed;
      private static final float DEFAULT_SPEED = 0.65f;
      private static final float SLOW_SPEED = 0.4f;
      private static final float SPRINT_SPEED = 0.9f;

      private static final double RAMP_RATE = 0.5;

      private static boolean toggleSlowSpeed = true;

//   static SlewRateLimiter filter = new SlewRateLimiter(0.5);
//   static SlewRateLimiter filter2 = new SlewRateLimiter(0.5);

public Drivetrain() {

  frontRightDrive = new CANSparkMax(3, MotorType.kBrushless);
  frontLeftDrive = new CANSparkMax(4, MotorType.kBrushless);
  backRightDrive = new CANSparkMax(1, MotorType.kBrushless);
  backLeftDrive = new CANSparkMax(2, MotorType.kBrushless);

  frontRightDrive.setOpenLoopRampRate(RAMP_RATE);
  frontLeftDrive.setOpenLoopRampRate(RAMP_RATE);
  backRightDrive.setOpenLoopRampRate(RAMP_RATE);
  backLeftDrive.setOpenLoopRampRate(RAMP_RATE);

  frontRightDrive.setInverted(false);
  frontLeftDrive.setInverted(false);
  backRightDrive.setInverted(false);
  backLeftDrive.setInverted(false);

  backRightDrive.follow(frontRightDrive);
  backLeftDrive.follow(frontLeftDrive);

  driveTrain = new DifferentialDrive(frontLeftDrive, frontRightDrive);

  speed = DEFAULT_SPEED;

}

public static void driveArcade(float pRotation, float pSpeed) {
    driveTrain.arcadeDrive(pSpeed, pRotation);
  }
  
  public static void driveTank(float pLeftSpeed, float pRightSpeed) {
 //  driveTrain.tankDrive(filter.calculate(pLeftSpeed), filter2.calculate(pRightSpeed));
   driveTrain.tankDrive(pLeftSpeed, pRightSpeed);
  }

  public static void tankDriveWithJoystick() {
      float right = (float) (IO.driveJoystick.getRawAxis(IO.RY_STICK_AXIS)* speed);
      float left = (float) (IO.driveJoystick.getRawAxis(IO.LY_STICK_AXIS)* -speed);
  
      driveTank(left, right);
  }

  public static void toggleSlowSpeed() {
    if(toggleSlowSpeed) {
      toggleSlowSpeed = false;
  
      if(speed == DEFAULT_SPEED) {
        speed = SLOW_SPEED;
      } else {
        speed = DEFAULT_SPEED;
      }
    }
  }

    //We want a function that when we hold a button, changes speed to max (or high speed value)
    public static void sprintMode(Boolean sprint){
      if(sprint){
        speed = SPRINT_SPEED;
        System.out.println("Sprint lol");
      }
      else{
        speed = DEFAULT_SPEED;
        System.out.println("nah bruh");
      }
      
    }






}




