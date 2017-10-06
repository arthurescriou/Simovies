/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.Orders.CHILL;
import static algorithms.StateMM.*;
import static algorithms.WhoAmI.TANK_1;
import static characteristics.IRadarResult.Types.OpponentMainBot;
import static characteristics.IRadarResult.Types.OpponentSecondaryBot;
import static characteristics.Parameters.Direction.LEFT;
import static characteristics.Parameters.Direction.RIGHT;
import static java.lang.Math.sin;

import java.util.ArrayList;
import java.util.Comparator;

import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;
import tools.CartCoordinate;
import tools.CoordHelper;

//import javafx.util.Pair;

public class MasterMind {

    private static MasterMind instance = new MasterMind();
    private ArrayList<Position> targets = new ArrayList<>();
    private ArrayList<BrainDetectScout> scouts = new ArrayList<>(2);
    private ArrayList<BrainDetectTank> tanks = new ArrayList<>(3);

    private int cptBot = 0;

    private StateMM state = DEPLOY;

    private RobotInstruction scout1;
    private RobotInstruction scout2;
    private RobotInstruction tank1;
    private RobotInstruction tank2;
    private RobotInstruction tank3;

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
        cptBot++;
        scouts.add(scout);
        if (cptBot == 5)
            initInstructions();
    }

    private void initInstructions() {
        for (BrainDetectScout scout : scouts) {
            switch (scout.getName()) {
                case SCOUT_1:
                    scout1 = new RobotInstruction(scout);
                    scout1.setObjective(new CartCoordinate(500, 650));
                    break;
                case SCOUT_2:
                    scout2 = new RobotInstruction(scout);
                    scout2.setObjective(new CartCoordinate(500, 1450));
                    break;
            }
        }
        for (BrainDetectTank tank : tanks) {
            switch (tank.getName()) {
                case TANK_1:
                    tank1 = new RobotInstruction(tank);
                    tank1.setObjective(new CartCoordinate(350, 450));
                    break;
                case TANK_2:
                    tank2 = new RobotInstruction(tank);
                    tank2.setObjective(new CartCoordinate(100, 1050));
                    break;
                case TANK_3:
                    tank3 = new RobotInstruction(tank);
                    tank3.setObjective(new CartCoordinate(350, 1650));
                    break;
            }
        }
    }

    public void addTanks(BrainDetectTank tank) {
        cptBot++;
        tanks.add(tank);
    }

    public void step(DetectBrain slave) {
        if (slave.getName() != TANK_1)
            return;

        updateWarField();

    }

    private boolean done() {
        return scout1.isDone() && scout2.isDone() && tank1.isDone() && tank2.isDone() && tank3.isDone();
    }

    public void giveMeOrderMaster(DetectBrain slave) {
        CartCoordinate target = fireForEffect();
        if (target != null) {
            tank1.fire(target);
            tank2.fire(target);
            tank3.fire(target);
            return;
        }
        if (slave.getName() == TANK_1) {

            if (scout1.isDone() && state == DEPLOY && tank1.isDone())
                scout1.setObjective(new CartCoordinate(250, 650));

            if (scout2.isDone() && state == DEPLOY && tank3.isDone())
                scout2.setObjective(new CartCoordinate(250, 1450));

            if (done()) {
                switch (state) {
                    case DEPLOY:
                        state = CHARGE;
                        scout1.setSpeedOf10(4);
                        scout1.setObjective(new CartCoordinate(3000, 650));
                        scout2.setSpeedOf10(4);
                        scout2.setObjective(new CartCoordinate(3000, 1455));
                        tank1.setObjective(new CartCoordinate(3000, 1050));
                        tank2.setObjective(new CartCoordinate(3000, 1650));
                        tank3.setObjective(new CartCoordinate(3000, 1450));
                        break;
                }
            }

        }
        slave.logPosition();
        Orders currentOrder = CHILL;
        switch (slave.getName()) {

            case TANK_1:
                tank1.majObj();
                currentOrder = tank1.getCurrentOrder();
                //                slave.sendLogMessage("" + tank1.isDone() + " " + currentOrder);
                break;
            case TANK_2:
                tank2.majObj();
                currentOrder = tank2.getCurrentOrder();
                //                slave.sendLogMessage("" + tank2.isDone() + " " + currentOrder);
                break;
            case TANK_3:
                tank3.majObj();
                currentOrder = tank3.getCurrentOrder();
                //                slave.sendLogMessage("" + tank3.isDone() + " " + currentOrder);
                break;
            case SCOUT_1:
                scout1.majObj();
                currentOrder = scout1.getCurrentOrder();
                //                slave.sendLogMessage("" + scout1.isDone() + " " + currentOrder);
                break;
            case SCOUT_2:
                scout2.majObj();
                currentOrder = scout2.getCurrentOrder();
                //                slave.sendLogMessage("" + scout2.isDone() + " " + currentOrder);
                break;
        }

        switch (currentOrder) {
            case MOVE:
                slave.move();
                break;
            case MOVEBACK:
                slave.moveBack();
                break;
            case TURNRIGHT:
                slave.stepTurn(RIGHT);
                break;
            case TURNLEFT:
                slave.stepTurn(LEFT);
                break;
            case CHILL:
                break;

        }
    }
}
