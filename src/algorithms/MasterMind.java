/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static java.lang.Math.round;

import java.lang.reflect.Array;
import java.util.ArrayList;

import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;
import robotsimulator.RadarResult;
import tools.CartCoordinate;
import tools.CoordHelper;

public class MasterMind {

    private static MasterMind instance = new MasterMind();
    private Position[][] warField;
    private ArrayList<Position> target = new ArrayList<>();
    private ArrayList<Brain> scouts = new ArrayList<>(2);
    private ArrayList<Brain> tanks = new ArrayList<>(3);

    private MasterMind() {
        this.warField = new Position[3000][2000];
        for (int i = 0; i < 3000; i++) {
            Position[] tmp = warField[i];
            for (int j = 0; j < 2000; j++) {
                tmp[j] = new Position(i, j);
            }
        }
    }

    public ArrayList<Position> getTarget() {
        return target;
    }

    public Position[][] getWarField() {
        return warField;
    }

    //Launch at tank top
    public void updateWarField() {
        ArrayList<Brain> tmp = new ArrayList<>();
        tmp.addAll(scouts);
        tmp.addAll(tanks);
        target.clear();
        for (Brain bot : tmp) {
            ArrayList<IRadarResult> report = null;
            CartCoordinate pos = null;

            if (bot instanceof DetectBrain) {

                DetectBrain brainDetectScout = (DetectBrain) bot;
                report = brainDetectScout.getReport();
                pos = brainDetectScout.getPos();
            }


            for (IRadarResult r : report) {
                CartCoordinate rPos = CoordHelper
                                .polToCart(pos, r.getObjectDirection(), r.getObjectDistance());
                double radius = 0;
                switch (r.getObjectType()) {
                    case OpponentMainBot:
                    case OpponentSecondaryBot:
                    case TeamMainBot:
                    case TeamSecondaryBot:
                    case Wreck:
                        radius = Parameters.teamAMainBotRadius;
                        break;
                    case BULLET:
                        radius = Parameters.bulletRadius;
                        break;
                }
//                updatePart(rPos, radius, r.getObjectType());
                    target.add(new Position(rPos.getX(),rPos.getY(),r.getObjectType()));
            }
        }
    }

    private void updatePart(CartCoordinate rPos, double radius, RadarResult.Types type) {

        double rad = radius / 2;
        int beginX = (int) round(rPos.getX() - rad);
        beginX = (beginX < 0) ? 0 : beginX;
        int endX = (int) round(rPos.getX() + rad);
        endX = (endX > 3000) ? 3000 : endX;
        int beginY = (int) round(rPos.getY() - rad);
        beginY = (beginY < 0) ? 0 : beginY;
        int endY = (int) round(rPos.getY() + rad);
        endY = (endY > 2000) ? 2000 : endY;

        for (int x = beginX; x < endX; x++) {
            for (int y = beginY; y < endY; y++) {
                if (new CartCoordinate(x, y).distance(rPos) < radius) {
                    warField[x][y].setTypes(type);
                }
            }
        }
    }

    public static MasterMind getInstance() {
        return instance;
    }

    public void addScout(Brain scout) {
        scouts.add(scout);
    }
    public void addTanks(Brain tank) {
        tanks.add(tank);
    }

}
