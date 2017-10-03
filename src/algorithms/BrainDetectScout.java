/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.Parameters.*;
import static characteristics.Parameters.EAST;
import static characteristics.Parameters.NORTH;
import static characteristics.Parameters.SOUTH;
import static characteristics.Parameters.WEST;
import static characteristics.Parameters.teamASecondaryBotRadius;
import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.sin;
import static java.text.MessageFormat.format;

import java.awt.*;
import java.text.MessageFormat;
import java.util.*;

import javax.print.attribute.standard.PageRanges;
import javax.smartcardio.Card;

import characteristics.*;
import robotsimulator.*;
import tools.CartCoordinate;
import tools.CoordHelper;

public class BrainDetectScout extends Brain {

    private double cpt = 0;
    private String name;
    private CartCoordinate myPosition;
    private boolean teamGauche;
    private double moveSpeed = teamASecondaryBotSpeed;

    public void activate() {


        Parameters.
        BrainDetectScoutMaster.getInstance().add(this);
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
                myPosition = new CartCoordinate(teamASecondaryBot1InitX, teamASecondaryBot1InitY);
                logPosition();
            }

            if (droite) {
                name = "schd";
                teamGauche = false;
                myPosition = new CartCoordinate(teamBSecondaryBot1InitX, teamBSecondaryBot1InitY);
                logPosition();
            }
        }
        if (bas) {
            if (gauche) {
                name = "scbg";
                teamGauche = true;
                myPosition = new CartCoordinate(teamASecondaryBot2InitX, teamASecondaryBot2InitY);
                logPosition();
            }
            if (droite) {
                name = "scbd";
                teamGauche = false;
                myPosition = new CartCoordinate(teamBSecondaryBot2InitX, teamBSecondaryBot2InitY);
                logPosition();
            }
        }

    }

    public CartCoordinate getPosition() {
        return myPosition;
    }

    public void step() {

        if (random() < 0.5) {
            if (random() < 0.5) {
                stepTurn(Direction.RIGHT);
            } else {
                stepTurn(Direction.LEFT);
            }
            return;
        }
        boolean collision = false;
        if (detectFront().getObjectType() == IFrontSensorResult.Types.WALL) {
            stepTurn(Direction.RIGHT);
            return;
        }

        for (IRadarResult r : detectRadar()) {
            CartCoordinate position = null;
            double d = Double.MAX_VALUE;
            switch (r.getObjectType()) {

                case OpponentMainBot:
                case OpponentSecondaryBot:
                case TeamMainBot:
                case TeamSecondaryBot:
                case Wreck:
                    d= r.getObjectDistance() ;
                    break;
            }
            if (!collision) {
                collision = d < 2.1 * Parameters.teamASecondaryBotRadius;
            }
        }

        if (!collision) {
            myPosition = CoordHelper.polToCart(myPosition, getHeading(), moveSpeed);
        }
        logPosition();
        move();

    }

    public String getName() {
        return name;
    }

    private void logPosition() {
        sendLogMessage(name + " x:" + myPosition.getX() + " y:" + myPosition.getY());
    }
}
