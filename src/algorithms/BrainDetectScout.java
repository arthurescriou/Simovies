/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.Parameters.EAST;
import static characteristics.Parameters.NORTH;
import static characteristics.Parameters.SOUTH;
import static characteristics.Parameters.WEST;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.sin;
import static java.text.MessageFormat.format;

import java.awt.*;
import java.text.MessageFormat;
import java.util.*;

import characteristics.*;
import robotsimulator.Brain;
import tools.CoordHelper;

public class BrainDetectScout extends Brain {

    private double cpt = 0;
    private String name;
    private double posX;
    private double posY;
    private boolean teamGauche;
    private double moveSpeed = Parameters.teamASecondaryBotSpeed;

    public void activate() {
        boolean haut = false;
        boolean bas = false;
        boolean gauche = false;
        boolean droite = false;

        if (getHeading() == WEST)
            droite = true;
        if (getHeading() == EAST)
            gauche = true;

        for (IRadarResult r : detectRadar()) {
            if (r.getObjectDirection() == SOUTH)
                haut = true;

            if (r.getObjectDirection() == NORTH)
                bas = true;

        }

        if (haut) {
            if (gauche) {
                name = "schg";
                teamGauche = true;
                posX = Parameters.teamASecondaryBot1InitX;
                posY = Parameters.teamASecondaryBot1InitY;
                logPosition();
            }

            if (droite) {
                name = "schd";
                teamGauche = false;
                posX = Parameters.teamBSecondaryBot1InitX;
                posY = Parameters.teamBSecondaryBot1InitY;
                logPosition();
            }
        }
        if (bas) {
            if (gauche) {
                name = "scbg";
                teamGauche = true;
                posX = Parameters.teamASecondaryBot2InitX;
                posY = Parameters.teamASecondaryBot2InitY;
                logPosition();
            }
            if (droite) {
                name = "scbd";
                teamGauche = false;
                posX = Parameters.teamBSecondaryBot2InitX;
                posY = Parameters.teamBSecondaryBot2InitY;
                logPosition();
            }
        }

    }

    public void step() {
                if (cpt <10){
                    cpt++;
                    stepTurn(Parameters.Direction.RIGHT);
                    return;
                }
        boolean collision = false;
        int i = 0;
        for (IRadarResult r : detectRadar()) {
            Double radius = 0.0;
            switch (r.getObjectType()) {

                case OpponentMainBot:
                    radius = Parameters.getRadius(true, !teamGauche);
                    break;
                case OpponentSecondaryBot:
                    radius = Parameters.getRadius(false, !teamGauche);
                    break;
                case TeamMainBot:
                    radius = Parameters.getRadius(true, teamGauche);
                    break;
                case TeamSecondaryBot:
                    radius = Parameters.getRadius(false, teamGauche);
                    break;
                case Wreck:
                    //TODO
                    radius = Parameters.getRadius(true, !teamGauche);
                    break;
            }
            if (detectFront().getObjectType() == IFrontSensorResult.Types.WALL) {
                return;
            }
            if (r.getObjectDistance() < radius * 3)
                collision = true;
        }

        if (!collision) {
            Point newPos = CoordHelper.polToCart(posX, posY, getHeading(), moveSpeed);
            posX = newPos.x;
            posY = newPos.y;
        }
        logPosition();
        move();

    }

    private void logPosition() {
        sendLogMessage(name + " x:" + posX + " y:" + posY);
    }
}