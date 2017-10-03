/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.IRadarResult.Types.BULLET;
import static characteristics.IRadarResult.Types.OpponentMainBot;
import static characteristics.Parameters.*;
import static java.lang.Math.abs;
import static java.lang.Math.random;
import static java.lang.Math.sin;
import static tools.CoordHelper.*;

import java.util.*;

import characteristics.*;
import robotsimulator.*;
import tools.CartCoordinate;
import tools.CoordHelper;

public abstract class BrainDetectTank extends DetectBrain {
    private boolean first = false;

    private static List<IRadarResult.Types> BOTS_ENNEMY = Arrays
                    .asList(IRadarResult.Types.OpponentMainBot, IRadarResult.Types.OpponentSecondaryBot);

    public void activate() {
        moveSpeed = teamAMainBotSpeed;

        int auDessus = 0;
        int enDessous = 0;
        boolean gauche = false;
        boolean droite = false;
        MasterMind.getInstance().addTanks(this);

        if (getHeading() == WEST)
            droite = true;
        if (getHeading() == EAST)
            gauche = true;

        for (IRadarResult r : detectRadar()) {
            if (r.getObjectDirection() == SOUTH)
                enDessous++;
            if (r.getObjectDirection() == NORTH)
                auDessus++;
        }

        if (auDessus == 0) {
            first = true;
            if (gauche) {
                name = "thg";
                myPosition = new CartCoordinate(Parameters.teamAMainBot1InitX + 50, Parameters.teamAMainBot1InitY + 50);
            }
            if (droite) {
                name = "thd";
                myPosition = new CartCoordinate(Parameters.teamBMainBot1InitX + 50, Parameters.teamBMainBot1InitY + 50);
            }
        }
        if (auDessus == 1 && enDessous == 1) {
            if (gauche) {
                name = "tmg";
                myPosition = new CartCoordinate(Parameters.teamAMainBot2InitX + 50, Parameters.teamAMainBot2InitY + 50);
            }
            if (droite) {
                name = "tmd";
                myPosition = new CartCoordinate(Parameters.teamBMainBot2InitX + 50, Parameters.teamBMainBot2InitY + 50);
            }
        }
        if (enDessous == 0) {
            if (gauche) {
                name = "tbg";
                myPosition = new CartCoordinate(Parameters.teamAMainBot3InitX + 50, Parameters.teamAMainBot3InitY + 50);
            }
            if (droite) {
                name = "tbd";
                myPosition = new CartCoordinate(Parameters.teamBMainBot3InitX + 50, Parameters.teamBMainBot3InitY + 50);
            }
        }

    }

    @Override
    public void step() {
        if (first)
            MasterMind.getInstance().updateWarField();
    }

    @Override
    public CartCoordinate getPos() {
        return myPosition;
    }

    @Override
    public ArrayList<IRadarResult> getReport() {
        return detectRadar();
    }



}
