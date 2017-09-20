/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.Parameters.SOUTH;
import static characteristics.Parameters.WEST;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.sin;

import java.util.Arrays;
import java.util.List;


import characteristics.IFrontSensorResult;
import characteristics.IFrontSensorResult.Types;
import characteristics.Parameters.Direction;
import robotsimulator.Brain;
import robotsimulator.FrontSensorResult;

public class BrainCanevas extends Brain {

    private  Direction DIR ;
    private static int CPT = 50;
    private boolean turnTask, turnRight, finished, taskOne;
    private double endTaskDirection;
    private int endTaskCounter;
    private static Types WALL = Types.WALL;
    private static Types TEAMMAIN = Types.TeamMainBot;

    private static List<Types> BOTS_ENNEMY = Arrays
                    .asList(Types.OpponentMainBot, Types.OpponentSecondaryBot);
    private static List<Types> BOTS_ALLIED = Arrays.asList(Types.TeamMainBot, Types.TeamSecondaryBot);

    public BrainCanevas() { super(); }

    public void activate() {
        turnTask = true;
        finished = false;
        taskOne = true;
        endTaskDirection = SOUTH;
        DIR = Direction.RIGHT;
        stepTurn(DIR);
        sendLogMessage("Turning RIIIIIGHT");
    }

    public void step() {
        if(turnTask) {
            if (isHeading(endTaskDirection)) {
                turnTask = false;
            }
            stepTurn(DIR);
            return;
        }
        if (detectFront().getObjectType() == Types.WALL) {
            sendLogMessage("MUUUUUUUUUUUUURR");
            turnTask = true;
            endTaskDirection = getHeading() +0.5 * PI;
            DIR = Direction.LEFT;

        }
        move();
    }

    private boolean isHeading(double dir){
        sendLogMessage("heading " + abs(sin(getHeading() - dir)));
        return abs(sin(getHeading()-dir))< 0.05;
    }
}
