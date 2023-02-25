package frc.robot;
import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.AnalogInput;;

public class Manipulator {

    private static CANSparkMax armPitch;
    private static CANSparkMax intakePitch;
    private static CANSparkMax gripper;
    private static AnalogInput armPot;
    private static AnalogInput intakePot;
    private static float desiredArmPosition = 100;

    public Manipulator(){

        armPitch = new CANSparkMax(5, MotorType.kBrushless);
            // fully up = 1570
            // fully down = 100
        intakePitch = new CANSparkMax(6, MotorType.kBrushless);
            // fully up = 4049 (not hitting arm when arm is fully down)
            // fully down = 
        gripper = new CANSparkMax(7, MotorType.kBrushed);
        armPot = new AnalogInput(0);
        intakePot = new AnalogInput(1);

        armPitch.setInverted(false);
        intakePitch.setInverted(false);
        gripper.setInverted(false);

        desiredArmPosition = 100;
    }

    public static void driveArmToPosition(){
        float deltaPosition = desiredArmPosition-armPot.getValue();
        float armSpeed = (float) MathUtil.clamp(deltaPosition, -0.2, 0.2);
        armPitch.set(armSpeed);
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
        desiredArmPosition = 1570;
    }

    public static void armDown(){
        desiredArmPosition = 100;
    }

    public static void armStop(){
        armPitch.stopMotor();
    }



}