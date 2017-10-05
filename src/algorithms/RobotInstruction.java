/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.Orders.MOVE;
import static algorithms.Orders.MOVEBACK;
import static characteristics.Parameters.Direction;
import static characteristics.Parameters.Direction.LEFT;
import static characteristics.Parameters.Direction.RIGHT;
import static characteristics.Parameters.teamBSecondaryBotStepTurnAngle;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static tools.CoordHelper.cartToPol;

import tools.CartCoordinate;
import tools.PolarCoordinate;

public class RobotInstruction {

    private PolarCoordinate polarBear;
    private CartCoordinate objective;
    private Orders currentOrder;
    private static final double delta = 0.1;

    private DetectBrain myBot;

    public RobotInstruction(DetectBrain myBot) {
        this.myBot = myBot;
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

    private double getHeading(){
        return myBot.getHeading();
    }

    public void setObjective(CartCoordinate objective) {
        this.objective = objective;
        polarBear = cartToPol(getPosRobot(), objective);
        if (isHeading(polarBear.getAngle()))
            currentOrder = MOVE;
        else if (isLeCul(polarBear.getAngle()))
            currentOrder = MOVEBACK;
            else if (true){}


    }

    private boolean isHeading(double dir) {
        double head = getHeading() % (2 * PI);
        double dirdir = dir % (2 * PI);

        return abs(head-dirdir) < teamBSecondaryBotStepTurnAngle;
    }

    private boolean isLeCul(double dir) {
        return isHeading(dir + PI);
    }

    private Direction leftOrRight(double dir) {
        double head = getHeading() % (2 * PI);
        double ass = (getHeading() + PI) % (2 * PI);
        double whereTo = dir % (2 * PI);

        double diff = whereTo - head;
        double assDiff = whereTo - ass;

        if (abs(diff) < abs(assDiff))
            return diff < 0 ? LEFT : RIGHT;
        else
            return assDiff < 0 ? LEFT : RIGHT;
    }
}
