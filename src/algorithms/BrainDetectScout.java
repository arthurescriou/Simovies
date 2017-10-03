/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.Parameters.*;

import java.util.ArrayList;

import characteristics.*;
import tools.CartCoordinate;

public abstract class BrainDetectScout extends DetectBrain {

    private double cpt = 0;
    private boolean teamGauche;
    private MasterMind mm = MasterMind.getInstance();

    public void activate() {

        moveSpeed = teamASecondaryBotSpeed;
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
                myPosition = new CartCoordinate(teamASecondaryBot1InitX+50, teamASecondaryBot1InitY+50);
                logPosition();
            }

            if (droite) {
                name = "schd";
                teamGauche = false;
                myPosition = new CartCoordinate(teamBSecondaryBot1InitX+50, teamBSecondaryBot1InitY+50);
                logPosition();
            }
        }
        if (bas) {
            if (gauche) {
                name = "scbg";
                teamGauche = true;
                myPosition = new CartCoordinate(teamASecondaryBot2InitX+50, teamASecondaryBot2InitY+50);
                logPosition();
            }
            if (droite) {
                name = "scbd";
                teamGauche = false;
                myPosition = new CartCoordinate(teamBSecondaryBot2InitX+50, teamBSecondaryBot2InitY+50);
                logPosition();
            }
        }

    }

    public CartCoordinate getPosition() {
        return myPosition;
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


}
