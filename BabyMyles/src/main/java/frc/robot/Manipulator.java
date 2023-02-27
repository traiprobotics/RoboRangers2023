package frc.robot;
import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;;

public class Manipulator {

    private static CANSparkMax armPitch;
    private static CANSparkMax intakePitch;
    private static CANSparkMax gripper;
    private static AnalogInput armPot;
    private static AnalogInput intakePot;
    private static DigitalInput gripperPot;
    private static float desiredArmPosition = 100;
    private static float desiredIntakePosition = 2000;

    

    public Manipulator(){

        armPitch = new CANSparkMax(5, MotorType.kBrushless);
            // fully up = 1570
            // fully down = 100
        intakePitch = new CANSparkMax(6, MotorType.kBrushless);
            // fully up = 4049 (not hitting arm when arm is fully down)
            // fully down = 
        gripper = new CANSparkMax(7, MotorType.kBrushed);
        armPot = new AnalogInput(0);
        intakePot = new AnalogInput(2);
        gripperPot = new DigitalInput(0);

        armPitch.setInverted(false);
        intakePitch.setInverted(false);
        gripper.setInverted(false);

        desiredArmPosition = 100;
        desiredIntakePosition = 2000;
    }

    public static void driveArmToPosition(){
        float deltaPosition = desiredArmPosition-armPot.getValue();
        float armSpeed = (float) MathUtil.clamp(deltaPosition/1700, -0.35, 0.35);
        //System.out.println(armSpeed);
        armPitch.set(armSpeed);
    }

    public static void driveIntakeToPosition(){
        float deltaPosition = desiredIntakePosition-intakePot.getValue();
        float intakeSpeed = (float) MathUtil.clamp(deltaPosition/1700, -0.35, 0.35);
        //System.out.println(armSpeed);
        intakePitch.set(intakeSpeed);
    }

    public static void gripperOpen(){
        gripper.set(-0.3f);
    }

    public static void gripperClose(){
        gripper.set(0.3f);
 /*       if (gripperPot.get()){
       
      } else {
      gripper.set(0.3f);
       }
 */     
    }

    public static void gripperStop(){
        gripper.stopMotor();
    }

    public static void gripperHold(){
        gripper.set(0.05);
    }

    public static void gripperTest(){
        gripper.set(1);
    }

    public static void armPot(){
        System.out.println(armPot.getValue());
    }

    public static void intakePot(){
        System.out.println(intakePot.getValue());
    }

    public static void intakeUp(){
        intakePitch.set(-0.1f);
    }

    public static void intakeDown(){
        intakePitch.set(0.1f);
    }

    public static void intakeStop(){
        intakePitch.stopMotor();
    }

    public static void armUp(){
        desiredArmPosition = 1700;
    }

    public static void armMid(){
        desiredArmPosition = 1200;
    }

    public static void armGround(){
        desiredArmPosition = 500;
    }

    public static void armDown(){
        desiredArmPosition = 100;
    }

    public static void armStop(){
        armPitch.stopMotor();
    }

    //test space
    public static void driveIntakePitchStick(){
        float intakePitchSpeed = (float) (IO.controlJoystick.getRawAxis(IO.LY_STICK_AXIS));
        if(intakePitchSpeed < 0.05f && intakePitchSpeed > -0.05f){
            intakePitch.stopMotor();
        } else {
        intakePitch.set(intakePitchSpeed * 0.25);
        }
    }

    public static void driveArmPitchStick(){
        float armPitchChange = (float) (IO.controlJoystick.getRawAxis(IO.RY_STICK_AXIS));
        if(armPitchChange < 0.05f && armPitchChange > -0.05f){
            
        } else {
        desiredArmPosition += -25*armPitchChange;
        }
    }
}