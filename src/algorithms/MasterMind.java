/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.Orders.CHILL;
import static algorithms.StateMM.CHARGE;
import static algorithms.StateMM.DEPLOY;
import static algorithms.WhoAmI.TANK_1;
import static algorithms.WhoAmI.TANK_2;
import static characteristics.IRadarResult.Types.OpponentMainBot;
import static characteristics.IRadarResult.Types.OpponentSecondaryBot;
import static characteristics.Parameters.Direction.LEFT;
import static characteristics.Parameters.Direction.RIGHT;
import static java.lang.Math.*;
import static java.lang.Math.PI;

import java.util.*;
import java.util.stream.Collectors;

import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;
import tools.*;

//import javafx.util.Pair;

public class MasterMind {

    private boolean left;
    private static MasterMind instance = new MasterMind();
    private ArrayList<Position> targets = new ArrayList<>();
    private ArrayList<BrainDetectScout> scouts = new ArrayList<>(2);
    private ArrayList<BrainDetectTank> tanks = new ArrayList<>(3);

    private ArrayList<CartCoordinate> wrecks = new ArrayList<>();

    private int cptBot = 0;

    private StateMM state = DEPLOY;

    private RobotInstruction scout1;
    private RobotInstruction scout2;
    private RobotInstruction tank1;
    private RobotInstruction tank2;
    private RobotInstruction tank3;

    private Map<DetectBrain, RobotInstruction> instrucstions = new HashMap<>();

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
                        break;
                    case OpponentSecondaryBot:
                        break;
                    case TeamMainBot:
                        break;
                    case TeamSecondaryBot:
                        break;
                    case Wreck:
                        addWreck(rPos);
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

    public ArrayList<CartCoordinate> fireForEffect() {
        ArrayList<CartCoordinate> ennemies = new ArrayList<>();

        double avgX = tanks.stream().mapToDouble(brain -> brain.getPos().getX()).average().orElse(0.0);
        double avgY = tanks.stream().mapToDouble(brain -> brain.getPos().getY()).average().orElse(0.0);
        CartCoordinate avg = new CartCoordinate(avgX, avgY);

        List<CartCoordinate> collect = targets.stream().filter(pos -> pos.getTypes() == OpponentMainBot)
                        .map(pos -> new CartCoordinate(pos.getX(), pos.getY()))
                        .collect(Collectors.toList());
        ennemies.addAll(collect);

        List<CartCoordinate> collect2 = targets.stream().filter(pos -> pos.getTypes() == OpponentSecondaryBot)
                        .map(pos -> new CartCoordinate(pos.getX(), pos.getY()))
                        .collect(Collectors.toList());
        ennemies.addAll(collect2);

        return ennemies;

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
            left = scout.isTeamGauche();
            switch (scout.getName()) {
                case SCOUT_1:
                    scout1 = new RobotInstruction(scout);
                    instrucstions.put(scout, scout1);
                    scout1.setObjective(new CartCoordinate(left ? 500 : 2500, 650));
                    break;
                case SCOUT_2:
                    scout2 = new RobotInstruction(scout);
                    scout2.setObjective(new CartCoordinate(left ? 500 : 2500, 1450));
                    break;
            }
        }
        for (BrainDetectTank tank : tanks) {
            switch (tank.getName()) {
                case TANK_1:
                    tank1 = new RobotInstruction(tank);
                    instrucstions.put(tank, tank1);
                    tank1.setObjective(new CartCoordinate(left ? 350 : 2650, 450));
                    break;
                case TANK_2:
                    tank2 = new RobotInstruction(tank);
                    instrucstions.put(tank, tank2);
                    tank2.setObjective(new CartCoordinate(left ? 100 : 2900, 1050));
                    break;
                case TANK_3:
                    tank3 = new RobotInstruction(tank);
                    instrucstions.put(tank, tank3);
                    tank3.setObjective(new CartCoordinate(left ? 350 : 2650, 1650));
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

    private boolean shouldNotFire(BrainDetectTank tank, CartCoordinate target) {
        PolarCoordinate pc = CoordHelper.cartToPol(tank.getPos(), target);

        if (pc.getDist() > 1000) {
            return true;
        }
        return shouldNotFire(tank, pc.getAngle());
    }

    private boolean shouldNotFire(BrainDetectTank tank, double angle) {
        ArrayList<DetectBrain> allBot = new ArrayList<>();
        allBot.addAll(scouts);
        allBot.addAll(tanks);

        for (DetectBrain detectBrain : allBot) {
            double x = detectBrain.getPos().getX();
            double y = detectBrain.getPos().getY();

            PolarCoordinate bd = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x + 60, y + 60));
            PolarCoordinate hg = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x - 60, y - 60));
            PolarCoordinate bg = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x - 60, y + 60));
            PolarCoordinate hd = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x + 60, y - 60));

            if (isBetweenAngle(angle, hg.getAngle(), bd.getAngle()) || isBetweenAngle(angle, bg
                            .getAngle(), hd.getAngle())) {
                return true;
            }
        }
        for (CartCoordinate wreck : wrecks) {
            double x = wreck.getX();
            double y = wreck.getY();

            PolarCoordinate bd = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x + 60, y + 60));
            PolarCoordinate hg = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x - 60, y - 60));
            PolarCoordinate bg = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x - 60, y + 60));
            PolarCoordinate hd = CoordHelper.cartToPol(tank.getPos(), new CartCoordinate(x + 60, y - 60));

            if (isBetweenAngle(angle, hg.getAngle(), bd.getAngle()) || isBetweenAngle(angle, bg
                            .getAngle(), hd.getAngle())) {
                return true;
            }
        }
        return false;
    }

    private double normalizeAngle(double angle) {
        while (angle < -PI)
            angle += 2 * PI;
        while (angle > PI)
            angle -= 2 * PI;
        return angle;
    }

    private boolean isBetweenAngle(double angle, double a, double b) {
        a -= angle;
        b -= angle;
        normalizeAngle(a);
        normalizeAngle(b);
        if (a * b >= 0) {
            return false;
        } else
            return Math.abs(a - b) < PI;
    }

    private boolean done() {
        return scout1.isDone() && scout2.isDone() && tank1.isDone() && tank2.isDone() && tank3.isDone();
    }

    public void giveMeOrderMaster(DetectBrain slave) {
        boolean t1Ashot = false;
        boolean t2Ashot = false;
        boolean t3Ashot = false;
        boolean asShot = false;
        ArrayList<CartCoordinate> targets = fireForEffect();
        if (!targets.isEmpty()) {
            asShot = true;
            boolean tire1 = fire(targets.get(0), tank1.getMyBot());
            boolean tire2 = fire(targets.get(0), tank2.getMyBot());
            boolean tire3 = fire(targets.get(0), tank3.getMyBot());
            for (CartCoordinate target : targets) {
                if (tire1 && tire2 && tire3)
                    break;
                if (!tire1)
                    tire1 = fire(target, tank1.getMyBot());
                if (!tire2)
                    tire2 = fire(target, tank2.getMyBot());
                if (!tire3)
                    tire3 = fire(target, tank3.getMyBot());
            }
            t1Ashot = tire1;
            t2Ashot = tire2;
            t3Ashot = tire3;
        }

        if (slave.getName() == TANK_1) {

            if (scout1.isDone() && state == DEPLOY && tank1.isDone())
                scout1.setObjective(new CartCoordinate(left ? 250 : 2750, 650));

            if (scout2.isDone() && state == DEPLOY && tank3.isDone())
                scout2.setObjective(new CartCoordinate(left ? 250 : 2750, 1450));

            if (done()) {
                switch (state) {
                    case DEPLOY:
                        state = CHARGE;
                        scout1.setSpeedOf10(4);
                        scout1.setObjective(new CartCoordinate(left ? 3000 : 0, 650));
                        scout2.setSpeedOf10(4);
                        scout2.setObjective(new CartCoordinate(left ? 3000 : 0, 1455));
                        tank1.setObjective(new CartCoordinate(left ? 3000 : 0, 450));
                        tank2.setObjective(new CartCoordinate(left ? 3000 : 0, 1050));
                        tank3.setObjective(new CartCoordinate(left ? 3000 : 0, 1650));
                        break;
                }
            }

        }
        slave.logPosition();
        Orders currentOrder = CHILL;
        switch (slave.getName()) {

            case TANK_1:
                tank1.majObj();
                if (!t1Ashot) {
                    fire(tank1.fire(slave), slave);
                    currentOrder = tank1.getCurrentOrder();
                }
                slave.sendLogMessage("" + tank1.isDone() + " " + currentOrder);
                break;
            case TANK_2:
                tank2.majObj();
                if (!t2Ashot) {
                    fire(tank2.fire(slave), slave);
                    currentOrder = tank2.getCurrentOrder();
                }
                slave.sendLogMessage("" + tank2.isDone() + " " + currentOrder);
                break;
            case TANK_3:
                tank3.majObj();
                if (!t3Ashot) {
                    fire(tank3.fire(slave), slave);
                    currentOrder = tank3.getCurrentOrder();
                }
                slave.sendLogMessage("" + tank3.isDone() + " " + currentOrder);
                break;
            case SCOUT_1:
                scout1.majObj();
                if (!t1Ashot)
                    currentOrder = scout1.getCurrentOrder();
                slave.sendLogMessage("" + scout1.isDone() + " " + currentOrder);
                break;
            case SCOUT_2:
                scout2.majObj();
                if (!t3Ashot)
                    currentOrder = scout2.getCurrentOrder();
                slave.sendLogMessage("" + scout2.isDone() + " " + currentOrder);
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

    private void addWreck(CartCoordinate pos) {
        for (CartCoordinate wreck : wrecks) {
            if (abs(wreck.getX() - pos.getX()) < 1 && abs(wreck.getY() - pos.getY()) < 1)
                return;
        }
        wrecks.add(pos);
    }

    private boolean fire(double angle, DetectBrain bot) {
        BrainDetectTank tank = (BrainDetectTank) bot;
        if (!shouldNotFire(tank, angle)) {
            bot.fire(angle);
            return true;
        }
        return false;
    }

    private boolean fire(CartCoordinate target, DetectBrain bot) {
        BrainDetectTank tank = (BrainDetectTank) bot;
        if (!shouldNotFire(tank, target)) {
            instrucstions.get(bot).fire(target);
            return true;
        }
        return false;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isLeft() {
        return left;

    }
}
