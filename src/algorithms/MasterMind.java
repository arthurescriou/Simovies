/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.WhoAmI.TANK_1;
import static characteristics.IRadarResult.Types.OpponentMainBot;
import static characteristics.IRadarResult.Types.OpponentSecondaryBot;
import static characteristics.Parameters.Direction.LEFT;
import static characteristics.Parameters.Direction.RIGHT;

import java.util.ArrayList;
import java.util.Comparator;

import characteristics.IRadarResult;
import characteristics.Parameters;
import javafx.util.Pair;
import robotsimulator.Brain;
import tools.CartCoordinate;
import tools.CoordHelper;

public class MasterMind {

    private static MasterMind instance = new MasterMind();
    private ArrayList<Position> targets = new ArrayList<>();
    private ArrayList<BrainDetectScout> scouts = new ArrayList<>(2);
    private ArrayList<BrainDetectTank> tanks = new ArrayList<>(3);

    private Pair<Orders, Double> scout1 = new ArrayList<>();
    private Pair<Orders, Double> scout2 = new ArrayList<>();
    private Pair<Orders, Double> tank1 = new ArrayList<>();
    private Pair<Orders, Double> tank2 = new ArrayList<>();
    private Pair<Orders, Double> tank3 = new ArrayList<>();

    private MasterMind() {
    }

    public ArrayList<Position> getTargets() {
        return targets;
    }

    public void updateWarField() {
        ArrayList<Brain> tmp = new ArrayList<>();
        tmp.addAll(scouts);
        tmp.addAll(tanks);
        targets.clear();
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
                targets.add(new Position(rPos.getX(), rPos.getY(), r.getObjectType()));
            }
        }
    }

    public CartCoordinate fireForEffect() {
        double avgX = tanks.stream().mapToDouble(brain -> brain.getPos().getX()).average().orElse(0.0);
        double avgY = tanks.stream().mapToDouble(brain -> brain.getPos().getY()).average().orElse(0.0);
        CartCoordinate avg = new CartCoordinate(avgX, avgY);

        Position first_target = targets.stream().filter(pos -> pos.getTypes() == OpponentMainBot).sorted(Comparator
                        .comparingDouble(pos -> avg.distance(new CartCoordinate(pos.getX(), pos.getY())))).findFirst()
                        .orElse(null);
        Position second_target = targets.stream().filter(pos -> pos.getTypes() == OpponentSecondaryBot)
                        .sorted(Comparator
                                        .comparingDouble(pos -> avg
                                                        .distance(new CartCoordinate(pos.getX(), pos.getY()))))
                        .findFirst()
                        .orElse(null);
        if (first_target != null) {
            return new CartCoordinate(first_target.getX(), first_target.getY());
        }
        if (second_target != null) {
            return new CartCoordinate(second_target.getX(), second_target.getY());
        }
        return null;
    }

    public static MasterMind getInstance() {
        return instance;
    }

    public void addScout(BrainDetectScout scout) {
        scouts.add(scout);
    }

    public void addTanks(BrainDetectTank tank) {
        tanks.add(tank);
    }

    public Pair<Orders, Double> chooseSlaveArray(DetectBrain slave) {

        switch (slave.getName()) {
            case SCOUT_1:
                return scout1;
            case SCOUT_2:
                return scout2;
            case TANK_1:
                return tank1;
            case TANK_2:
                return tank2;
            case TANK_3:
                return tank3;
            default:
                return null;
        }

    }

    public void step(DetectBrain slave){
        if(slave.getName()!=TANK_1)
           return;

        updateWarField();

        if(scouts.get(0).getHeading()>Math.PI)
            scout1 = new Pair<>(Orders.TURN);

    }

    public void giveMeOrderMaster(DetectBrain slave) {
        Pair<Orders, Double> slaveOrders = chooseSlaveArray(slave);

        if (slaveOrders==null)
           return;

        switch (slaveOrders.getKey()) {
            case TURN:
                slave.stepTurn((slaveOrders.getValue() < 0) ? LEFT : RIGHT);
                break;
            case MOVE:
                slave.move(slaveOrders.getValue() > 0);
                break;
            case FIRE:
                slave.fire(slaveOrders.getValue());
                break;
            case CHILL:
                slave.sendLogMessage("cold one with the boys");
                break;
            default:
                slave.sendLogMessage("What the fuck ?");
        }
    }
}
