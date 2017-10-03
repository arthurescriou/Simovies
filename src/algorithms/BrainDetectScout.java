/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.Parameters.*;
import static java.lang.Math.random;

import java.util.ArrayList;

import characteristics.*;
import robotsimulator.Brain;
import tools.CartCoordinate;
import tools.CoordHelper;

public class BrainDetectScout extends Brain {

    private double cpt = 0;
    private String name;
    private CartCoordinate myPosition;
    private boolean teamGauche;
    private double moveSpeed = teamASecondaryBotSpeed;
    MasterMind mm = MasterMind.getInstance();

    public void activate() {

        mm.addScout(this);
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

        mm.updateWarField();

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
        double newX = CoordHelper.polToCart(myPosition, getHeading(), moveSpeed).getX();
        double newY = CoordHelper.polToCart(myPosition, getHeading(), moveSpeed).getY();
        double objX;
        double objY;
        boolean bull = false;
        for (IRadarResult r : detectRadar()) {
            CartCoordinate position = null;
            double d = Double.MAX_VALUE;
            switch (r.getObjectType()) {
                case OpponentMainBot:
                case OpponentSecondaryBot:
                case TeamMainBot:
                case TeamSecondaryBot:
                case Wreck:
                    d = r.getObjectDistance();
                    break;
                case BULLET:
                    bull = true;
                    break;
            }
            CartCoordinate botPos = CoordHelper
                            .polToCart(myPosition, r.getObjectDirection(), r.getObjectDistance());
            if (!bull)
                if (!collision)
                    collision = (newX - botPos.getX()) * (newX - botPos.getX()) + (newY - botPos
                                    .getY()) * (newY - botPos
                                    .getY()) < (Parameters.teamAMainBotRadius + 50) * (Parameters.teamAMainBotRadius
                                    + 50);
            //                collision = d < 2.1 * Parameters.teamASecondaryBotRadius;
        }

        if (!collision && getHealth() > 0) {
            myPosition = CoordHelper.polToCart(myPosition, getHeading(), moveSpeed);
        }
        logPosition();
        move();

    }

    public ArrayList<IRadarResult> getReport() {
        return detectRadar();
    }

    public CartCoordinate getPos() {
        return myPosition;
    }

    public String getName() {
        return name;
    }

    private void logPosition() {
        sendLogMessage(name + " x:" + myPosition.getX() + " y:" + myPosition.getY());
    }
}
