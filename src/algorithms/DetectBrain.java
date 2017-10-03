/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static tools.CoordHelper.isOutOfBound;
import static tools.CoordHelper.polToCart;

import java.util.ArrayList;

import characteristics.*;
import robotsimulator.Brain;
import tools.CartCoordinate;

public abstract class DetectBrain extends Brain {

    protected CartCoordinate myPosition;
    protected String name;
    protected double moveSpeed;

    public abstract CartCoordinate getPos();

    public abstract ArrayList<IRadarResult> getReport();

    protected void logPosition() {
        sendLogMessage(name + " x:" + myPosition.getX() + " y:" + myPosition.getY());
    }

    private void move(boolean back) {
        double speed = back ? -moveSpeed : moveSpeed;
        boolean collision = false;
        if (detectFront().getObjectType() == IFrontSensorResult.Types.WALL) {
            stepTurn(Parameters.Direction.RIGHT);
            return;
        }
        CartCoordinate newCartCoordinate = polToCart(myPosition, getHeading(), speed);
        boolean bull = false;
        for (IRadarResult r : detectRadar()) {
            double d = Double.MAX_VALUE;
            switch (r.getObjectType()) {
                case OpponentMainBot:
                case OpponentSecondaryBot:
                case TeamMainBot:
                case TeamSecondaryBot:
                case Wreck:
                    break;
                case BULLET:
                    bull = true;
                    break;
            }
            CartCoordinate botPos = polToCart(myPosition, r.getObjectDirection(), r.getObjectDistance());
            if (!bull)
                if (!collision)
                    collision = (newCartCoordinate.getX() - botPos.getX()) * (newCartCoordinate.getX() - botPos
                                    .getX()) + (newCartCoordinate.getY() - botPos
                                    .getY()) * (newCartCoordinate.getY() - botPos
                                    .getY()) < (Parameters.teamAMainBotRadius + 50) * (Parameters.teamAMainBotRadius
                                    + 50);
        }

        if (!isOutOfBound(newCartCoordinate)) {
            if (!collision && getHealth() > 0) {
                myPosition = newCartCoordinate;
            }
        }

            logPosition();
        if (back)
            super.moveBack();
        else
            super.move();

    }

    @Override
    public void move() {
        move(false);
    }

    @Override
    public void moveBack() {
        move(true);
    }
}
