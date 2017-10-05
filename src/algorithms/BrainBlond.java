/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.cos;

import characteristics.Parameters;
import robotsimulator.Brain;

public class BrainBlond extends Brain {

    @Override
    public void activate() {

    }

    @Override
    public void step() {
        if (!isLeCul(17 * PI / 18)) {
            stepTurn(Parameters.Direction.RIGHT);
        }
        System.out.println("dir: " + leftOrRight(4 * PI / 6));
        System.out.println(getHeading() % (2 * PI));
        //        System.out.println(("bottom: " + isLeCul(PI)));

    }

    private boolean isHeading(double dir) {
//        double head = getHeading() % (2 * PI);
//        double dirdir = dir % (2 * PI);
//
//        return abs(head-dirdir) < Parameters.teamBSecondaryBotStepTurnAngle;

        return Math.abs(Math.sin(getHeading() - dir)) < Parameters.teamAMainBotStepTurnAngle && cos(getHeading()) < 0 ;
    }

    private boolean isLeCul(double dir) {
        return isHeading(dir + PI);
    }

    private Parameters.Direction leftOrRight(double dir) {
        double head = getHeading() % (2 * PI);
        double ass = (getHeading() + PI) % (2 * PI);
        double whereTo = dir % (2 * PI);

        double diff = whereTo - head;
        double assDiff = whereTo - ass;

        if (abs(diff) < abs(assDiff))
            return diff < 0 ? Parameters.Direction.LEFT : Parameters.Direction.RIGHT;
        else
            return assDiff < 0 ? Parameters.Direction.RIGHT : Parameters.Direction.LEFT;
    }
}