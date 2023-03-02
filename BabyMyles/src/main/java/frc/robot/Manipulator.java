package frc.robot;

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
    private static DigitalInput gripperSwitch;
    private static float desiredArmPosition = 100;
    private static float desiredIntakePosition = 3000;
    private static float currentArmPosition = 100;

    //Range settings for the arm pitch
    private static final float ARM_MAX_UP = 2000;
    public static final float ARM_HIGH = 1700;
    public static final float ARM_MIDDLE = 1500;
    public static final float ARM_GROUND = 500;
    public static final float ARM_HOME = 100;
    private static final float ARM_MAX_DOWN = 90;

    //Range settings for the manipulator pitch
    private static final float INTAKE_MAX_UP = 4000;
    public static final float INTAKE_HIGH = 2100;
    public static final float INTAKE_MIDDLE = 620;
    public static final float INTAKE_GROUND = 1200;
    public static final float INTAKE_HOME = 3500;
    private static final float INTAKE_MAX_DOWN = 250;
    private static final float INTAKE_SAFE_DEPLOY = 400;
    private static final float mapRate = 0;

    public Manipulator(){

        //SPARK MAXs
        armPitch = new CANSparkMax(5, MotorType.kBrushless);
        intakePitch = new CANSparkMax(6, MotorType.kBrushless);
        gripper = new CANSparkMax(7, MotorType.kBrushed);

        //Sensors
        armPot = new AnalogInput(0);
        intakePot = new AnalogInput(2);
        gripperSwitch = new DigitalInput(0);

        //Are there any inverted?
        armPitch.setInverted(false);
        intakePitch.setInverted(false);
        gripper.setInverted(false);

        //Set smart amp limit LOWER
        gripper.setSmartCurrentLimit(6);
        
        //working variables
        desiredArmPosition = 100;
        desiredIntakePosition = 2000;
        currentArmPosition = armPot.getValue();
    }

    public static void controlManipulator(){
        driveArmToPosition();
        driveArmPitchStick();
    //    driveIntakeToPosition();
    //    driveIntakePitchStick();
        driveIntake();
    }

    private static void driveArmToPosition(){
        float currentArmPosition = armPot.getValue();
        float deltaPosition = desiredArmPosition-currentArmPosition;
        float armSpeed = (float) MathUtil.clamp(deltaPosition/2000, -0.3, 0.3);
        armPitch.set(armSpeed);

        if(currentArmPosition >= ARM_GROUND){
            Drivetrain.setSlowSpeed();
        }
    }

    private static void driveIntakeToPosition(){
        float safeIntakePosition = ensureSafeIntakeMovement(desiredIntakePosition);
        float deltaPosition = safeIntakePosition-intakePot.getValue();
        float intakeSpeed = (float) MathUtil.clamp(deltaPosition/4000, -0.1, 0.1);
        //System.out.println("safe" + safeIntakePosition +"delta" + deltaPosition+"spd"+intakeSpeed);
        //intakePitch.set(intakeSpeed);
    }

    private static float ensureSafeIntakeMovement(float desiredIntakePosition) {
        if(armPot.getValue() < INTAKE_SAFE_DEPLOY){
            return INTAKE_HOME;
        }else{
            return desiredIntakePosition;
        }
    }

    public static void gripperOpen(){
        gripper.set(-1f);
    }

    public static void gripperClose(){
        if (gripperSwitch.get()){
            // System.out.println("s");
        } 
        gripper.set(1f);
     
    }

    public static void gripperStop(){
        gripper.stopMotor();
    }

    public static void gripperTest(){
        gripper.set(1);
    }

    public static void setPosition(float armSetpoint,float intakeSetpoint){
        setArmWithLimits(armSetpoint);
        setIntakeWithLimits(intakeSetpoint);
    }

    private static void setArmWithLimits(float newSetpoint){
        desiredArmPosition = (float)MathUtil.clamp(newSetpoint, ARM_MAX_DOWN, ARM_MAX_UP);
    }

    private static void setIntakeWithLimits(float newSetpoint) {
        desiredIntakePosition = (float)MathUtil.clamp(newSetpoint, INTAKE_MAX_DOWN, INTAKE_MAX_UP);
    }

    private static void driveIntake(){
        if(armPot.getValue() < INTAKE_SAFE_DEPLOY){
            if(intakePot.getValue() > INTAKE_HOME){
                driveIntakePitchStick();
            }else{
                intakePitch.set(-0.5);
            }
        } else if(intakePot.getValue() > INTAKE_MAX_UP){
            intakePitch.set(0.26);

        } else if(intakePot.getValue() < INTAKE_MAX_DOWN){
              intakePitch.set(-0.26);
        } else if(armPot.getValue() > ARM_MAX_UP -300){
            if(intakePot.getValue() > 2200){
                intakePitch.set(0.5); 
            }else{
                driveIntakePitchStick();
            }
        } else{
            driveIntakePitchStick();
        }
    }

    //test space
    private static void driveIntakePitchStick(){
        float intakePitchSpeed = (float) (IO.controlJoystick.getRawAxis(IO.LY_STICK_AXIS));
        if(intakePitchSpeed < 0.05f && intakePitchSpeed > -0.05f){
            intakePitch.stopMotor();
        } else {
        intakePitch.set(intakePitchSpeed * 0.25);
        //System.out.print(armPitch);
        //desiredIntakePosition += 25*intakePitchSpeed;
        }
        //setIntakeWithLimits(intakePitchSpeed);
    }

    private static void driveArmPitchStick(){
        float armPitchChange = (float) (IO.controlJoystick.getRawAxis(IO.RY_STICK_AXIS));
        if(armPitchChange < 0.05f && armPitchChange > -0.05f){
        } else {
        desiredArmPosition += -25*armPitchChange;
        setArmWithLimits(desiredArmPosition);
        }
    }

    public static void armStop(){
        armPitch.stopMotor();
    }
}