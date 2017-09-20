/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/BrainCanevas.java 2014-10-19 buixuan.
 * ******************************************************/
package algorithms;

import static characteristics.IRadarResult.Types.BULLET;
import static characteristics.IRadarResult.Types.OpponentMainBot;
import static characteristics.Parameters.EAST;
import static characteristics.Parameters.NORTH;
import static characteristics.Parameters.SOUTH;
import static characteristics.Parameters.WEST;
import static java.lang.Math.abs;
import static java.lang.Math.sin;

import java.util.*;

import characteristics.*;
import robotsimulator.*;

public class BrainDetectTank extends Brain {

    private String name;

    private static List<IRadarResult.Types> BOTS_ENNEMY = Arrays
                    .asList(IRadarResult.Types.OpponentMainBot, IRadarResult.Types.OpponentSecondaryBot);
    private double posX;
    private double posY;

    public void activate() {
        int auDessus = 0;
        int enDessous = 0;
        boolean gauche = false;
        boolean droite = false;

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
            if (gauche) {
                name = "thg";
                posX = Parameters.teamAMainBot1InitX;
                posY = Parameters.teamAMainBot1InitY;
                sendLogMessage("thg " +posX +" "+posY);
            }
            if (droite) {
                name = "thd";
                posX = Parameters.teamBMainBot1InitX;
                posY = Parameters.teamBMainBot1InitY;
                sendLogMessage("thd " +posX +" "+posY);
            }
        }
        if (auDessus == 1 && enDessous == 1) {
            if (gauche) {
                name = "tmg";
                posX = Parameters.teamAMainBot2InitX;
                posY = Parameters.teamAMainBot2InitY;
                sendLogMessage("tmg " +posX +" "+posY);
            }
            if (droite) {
                name = "tmd";
                posX = Parameters.teamBMainBot2InitX;
                posY = Parameters.teamBMainBot2InitY;
                sendLogMessage("tmd " +posX +" "+posY);
            }
        }
        if (enDessous == 0) {
            if (gauche) {
                name = "tbg";
                posX = Parameters.teamAMainBot3InitX;
                posY = Parameters.teamAMainBot3InitY;
                sendLogMessage("tbg " +posX +" "+posY);
            }
            if (droite) {
                name = "tbd";
                posX = Parameters.teamBMainBot3InitX;
                posY = Parameters.teamBMainBot3InitY;
                sendLogMessage("tbd " +posX +" "+posY);
            }
        }

    }

    public void step() {
//
//        if (name.contains("g")) {
//            if (!detectRadar().isEmpty()) {
//                IRadarResult r = detectRadar().stream().filter(ir -> ir.getObjectType().equals(BULLET)).findFirst()
//                                .orElse(null);
//                if (r != null) {
//                    System.out.println(r);
//                    fire(r.getObjectDirection());
//                    return;
//                }
//
//            }
//
//        }
//
//        if (!detectRadar().isEmpty()) {
//            IRadarResult r = detectRadar().stream().filter(ir -> ir.getObjectType().equals(OpponentMainBot)).findFirst()
//                            .orElse(null);
//            if (r != null) {
//                System.out.println(r);
//                fire(r.getObjectDirection());
//                return;
//            }
//
//        }
//
//        if (name.equals("thg")) {
//            sendLogMessage("Je suis le haut gauche et je tourne vers la gauche");
//            stepTurn(Parameters.Direction.LEFT);
//        }
//        if (name.equals("tmg")) {
//            sendLogMessage("Je suis le mid guache");
//            //move();
//        }
//        if (name.equals("tbg")) {
//            sendLogMessage("Je suis le bas gayche et je tourne vers la droite");
//            stepTurn(Parameters.Direction.RIGHT);
//        }
//        if (name.equals("thd")) {
//            sendLogMessage("Je suis le haut droit et je tourne vers la gauche");
//            stepTurn(Parameters.Direction.LEFT);
//        }
//        if (name.equals("tmd")) {
//            sendLogMessage("Je suis le mid droit");
//            move();
//        }
//        if (name.equals("tbd")) {
//            sendLogMessage("Je suis le bas droit et je tourne vers la droite");
//            stepTurn(Parameters.Direction.RIGHT);
//        }
    }

}
