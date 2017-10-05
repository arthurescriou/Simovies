/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import characteristics.Parameters;
import tools.*;

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

    private boolean isHeading(double dir) {
        return (abs(Math.sin(getHeading() - dir))<Parameters.teamBSecondaryBotStepTurnAngle)&&(Math.cos(getHeading() - dir)>0);
    }

    private boolean isLeCul(double dir){
        return isHeading(dir - PI);
    }

    public void setObjective(CartCoordinate objective) {
        this.objective = objective;
        polarBear = CoordHelper.cartToPol(getPosRobot(), objective);
        if (isHeading(polarBear.getAngle()))
            currentOrder = Orders.MOVE;
        else if (isLeCul(polarBear.getAngle()))
            currentOrder = Orders.MOVEBACK;
            else if (true){}


    }

    private Parameters.Direction leftOrRight(double dir){
        double differenceTete = getHeading() % (2 * PI) - dir % (2 * PI);
        double differenceFesse = (getHeading()+PI) % (2 * PI) - dir % (2 * PI);

        if(abs(differenceTete) < abs(differenceFesse)){
            return (differenceTete > 0) ? Parameters.Direction.LEFT : Parameters.Direction.RIGHT;
        }else{
            return (differenceFesse > 0) ? Parameters.Direction.LEFT : Parameters.Direction.RIGHT;
        }
    }
}
