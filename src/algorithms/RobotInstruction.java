/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.Orders.*;
import static characteristics.Parameters.teamBSecondaryBotStepTurnAngle;
import static java.lang.Math.abs;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static tools.CoordHelper.cartToPol;

import tools.CartCoordinate;
import tools.PolarCoordinate;

public class RobotInstruction {

    private PolarCoordinate polarBear;
    private CartCoordinate objective;
    private Orders currentOrder = CHILL;
    private static final double delta = 0.1;

    private DetectBrain myBot;

    public RobotInstruction(DetectBrain myBot) {
        this.myBot = myBot;
        setObjective(myBot.getPos());
    }

    public PolarCoordinate getPolarBear() {
        return polarBear;
    }

    public CartCoordinate getObjective() {
        return objective;
    }

    public Orders getCurrentOrder() {
        return currentOrder;
    }

    public CartCoordinate getPosRobot() {
        return myBot.getPos();
    }

    private double getHeading() {
        return myBot.getHeading();
    }

    public void setObjective(CartCoordinate objective) {
        this.objective = objective;
        majObj();
    }

    public void majObj() {
        polarBear = cartToPol(getPosRobot(), objective);
        if (polarBear.getDist() > 2) {
            if (isHeading(polarBear.getAngle()))
                currentOrder = MOVE;
            else if (isLeCul(polarBear.getAngle()))
                currentOrder = MOVEBACK;
            else
                currentOrder = leftOrRight(polarBear.getAngle());
        } else
            currentOrder = CHILL;

    }

    public boolean isHeading(double dir) {
        double cosDir = cos(dir);
        double sinDir = sin(dir);
        double cosBot = cos(getHeading());
        double sinBot = sin(getHeading());

        return abs(cosDir - cosBot) < cos(teamBSecondaryBotStepTurnAngle) && abs(sinDir - sinBot) <
                        sin(teamBSecondaryBotStepTurnAngle);
    }

    public boolean isHeading(){
        return isHeading(polarBear.getAngle());
    }

    public boolean isLeCul(double dir) {
        double cosDir = cos(dir);
        double sinDir = sin(dir);
        double cosBot = - cos(getHeading());
        double sinBot = - sin(getHeading());

        return abs(cosDir - cosBot) < cos(teamBSecondaryBotStepTurnAngle) && abs(sinDir - sinBot) <
                        sin(teamBSecondaryBotStepTurnAngle);
    }

    public boolean isLeCul() {
        return isLeCul(polarBear.getAngle());
    }

    private Orders leftOrRight(double dir) {
        double cos = cos(getHeading()+dir);
        double sin = sin(getHeading()+dir);
        if(cos>0) {
            if (sin > 0)
                return TURNRIGHT;
            else
                return TURNLEFT;
        }else
            if(sin<0)
                return TURNLEFT;
            else return TURNRIGHT;

    }
//        double head = acos(cos(getHeading()));
//        double ass = -abs((getHeading() + PI) % (2 * PI));
//        double whereTo = dir % (2 * PI);
//
//        double diff = whereTo - head;
//        double assDiff = whereTo - ass;
//
//        if (abs(diff) < abs(assDiff))
//            return diff < 0 ? TURNLEFT : TURNRIGHT;
//        else
//            return assDiff < 0 ? TURNLEFT : TURNRIGHT;
//    }
}
