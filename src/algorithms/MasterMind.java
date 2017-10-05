/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.Orders.*;
import static algorithms.WhoAmI.TANK_1;
import static characteristics.IRadarResult.Types.OpponentMainBot;
import static characteristics.IRadarResult.Types.OpponentSecondaryBot;
import static characteristics.Parameters.Direction.LEFT;
import static characteristics.Parameters.Direction.RIGHT;
import static java.lang.Math.PI;

import java.util.ArrayList;
import java.util.Comparator;

import characteristics.IRadarResult;
import characteristics.Parameters;
//import javafx.util.Pair;
import robotsimulator.Brain;
import tools.CartCoordinate;
import tools.CoordHelper;

public class MasterMind {

    private static MasterMind instance = new MasterMind();
    private ArrayList<Position> targets = new ArrayList<>();
    private ArrayList<BrainDetectScout> scouts = new ArrayList<>(2);
    private ArrayList<BrainDetectTank> tanks = new ArrayList<>(3);

    private int cptBot = 0;

    private StateMM state = StateMM.DEPLOY;

    private RobotInstruction scout1;
    private RobotInstruction scout2;
    private RobotInstruction tank1;
    private RobotInstruction tank2;
    private RobotInstruction tank3;

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
                    scout1.setObjective(new CartCoordinate(500, 2000));
                    System.out.println("scout1: " + scout1.getPolarBear().getAngle());
                    break;
                case SCOUT_2:
                    scout2 = new RobotInstruction(scout);
                    scout2.setObjective(new CartCoordinate(500, 0));
                    System.out.println("scout2: " + scout2.getPolarBear().getAngle());
                    break;
            }
            System.out.println("lala");
        }
        for (BrainDetectTank tank : tanks) {
            switch (tank.getName()) {
                case TANK_1:
                    tank1 = new RobotInstruction(tank);

                    break;
                case TANK_2:
                    tank2 = new RobotInstruction(tank);
                    break;
                case TANK_3:
                    tank3 = new RobotInstruction(tank);
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

    public void giveMeOrderMaster(DetectBrain slave) {
        if (slave.getName() == TANK_1) {

        }

        Orders currentOrder = CHILL;
        switch (slave.getName()) {

            case TANK_1:
                tank1.majObj();
                currentOrder = tank1.getCurrentOrder();
                break;
            case TANK_2:
                tank2.majObj();
                currentOrder = tank2.getCurrentOrder();
                break;
            case TANK_3:
                tank3.majObj();
                currentOrder = tank3.getCurrentOrder();
                break;
            case SCOUT_1:
                scout1.majObj();
                currentOrder = scout1.getCurrentOrder();
                break;
            case SCOUT_2:
                scout2.majObj();
                currentOrder = scout2.getCurrentOrder();
                break;
        }
        slave.sendLogMessage(scout2.getPolarBear().getAngle()+"=>"+(slave.getHeading() % (2 * PI))+": "+((slave.getHeading() + PI) % (2 * PI)));
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
