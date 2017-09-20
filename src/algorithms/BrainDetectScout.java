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

import java.text.MessageFormat;
import java.util.*;

import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;

public class BrainDetectScout extends Brain {

    private String name ;
    private double posX;
    private double posY;

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

        if(haut){
            if (gauche) {
                name = "schg";
                posX = Parameters.teamASecondaryBot1InitX;
                posY = Parameters.teamASecondaryBot1InitY;
                sendLogMessage("schg " +posX+" "+posY);
            }

            if (droite) {
                name = "schd";
                posX = Parameters.teamBSecondaryBot1InitX;
                posY = Parameters.teamBSecondaryBot1InitY;
                sendLogMessage("schd " +posX+" "+posY);
            }
        }
        if(bas){
            if (gauche) {
                name = "scbg";
                posX = Parameters.teamASecondaryBot2InitX;
                posY = Parameters.teamASecondaryBot2InitY;
                sendLogMessage("scbg " +posX+" "+posY);
            }
            if (droite) {
                name = "scbd";
                posX = Parameters.teamBSecondaryBot2InitX;
                posY = Parameters.teamBSecondaryBot2InitY;
                sendLogMessage("scbd " +posX+" "+posY);
            }
        }


    }

    public void step() {
//        if(name.equals("sc2g")){
//            sendLogMessage("Je suis le sc2g");
//            move();
//        }
//        if(name.equals("sc2d")){
//            sendLogMessage("Je suis le sc2d");
//            stepTurn(Parameters.Direction.RIGHT);
//        }
    }
}
