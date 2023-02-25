package frc.robot;
import javax.print.CancelablePrintJob;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.AnalogInput;;

public class Manipulator {

    private static CANSparkMax armPitch;
    private static CANSparkMax intakePitch;
    private static CANSparkMax gripper;
    private static AnalogInput armPot;
    private static AnalogInput intakePot;

    public Manipulator(){

        armPitch = new CANSparkMax(5, MotorType.kBrushless);
        intakePitch = new CANSparkMax(6, MotorType.kBrushless);
        gripper = new CANSparkMax(7, MotorType.kBrushed);
        armPot = new AnalogInput(0);
        intakePot = new AnalogInput(1);

        armPitch.setInverted(false);
        intakePitch.setInverted(false);
        gripper.setInverted(false);
    }

    public static void armPot(){
        System.out.println(armPot.getValue());

    }



}