/*----------------------------------------------------------------------------*/
/* Copyright (c) FIRST 2008. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package edu.wpi.first.wpilibj.templates;

import edu.wpi.first.wpilibj.AnalogChannel;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Victor;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class RobotTemplate extends IterativeRobot {

    Joystick xBox;
    AnalogChannel encoder;
    Victor vic;
    Solenoid sol4, sol5, sol7, sol8;
    DigitalInput digit2, digit3;
    boolean frontSwitch, good2go = true, goArm1 = false, goArm2 = false,
            shoot = false, good2go2 = false;
    int grabCount = 0, shootCount = 0;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        xBox = new Joystick(1);
        encoder = new AnalogChannel(2);
        vic = new Victor(5);
        sol4 = new Solenoid(4);
        sol5 = new Solenoid(5);
        sol7 = new Solenoid(7);
        sol8 = new Solenoid(8);
        digit2 = new DigitalInput(2);
        digit3 = new DigitalInput(3);
        frontSwitch = digit3.get();
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        if (xBox.getRawButton(5)) {
            goArm1 = true;
        }
        if (goArm1) {
            vic.set(-.25);
        }
        if (encoder.getVoltage() >= 3.5 && good2go) {
            vic.set(0);
            goArm1 = false;
        }
        if (frontSwitch == true) {
            good2go = false;
            vic.set(.5);
            sol4.set(true);
            sol5.set(false);
            grabCount++;
        }
        if (grabCount >= 10){
            frontSwitch = false;
            vic.set(0);
            grabCount = 0;
        }
        if (xBox.getRawButton(6)){
            goArm2 = true;
        }
        if (goArm2){
            vic.set(-.25);
            good2go2 = true;
        }
        if (encoder.getVoltage() <= 1.5 && good2go2){
            vic.set(0);
            sol4.set(false);
            sol5.set(true);
            good2go2 = false;
        }
        if (xBox.getRawAxis(3) >= .9){
            shoot = true;
        }
        if (shoot){
            sol7.set(false);
            sol8.set(true);
            shootCount++;
        }
        if (shootCount >= 50){
            shoot = false;
            sol7.set(true);
            sol8.set(false);
            shootCount = 0;
            frontSwitch = digit3.get();
            good2go = true;
        }

    }

    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    }
}
