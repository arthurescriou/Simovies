/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import java.util.Random;

import characteristics.Parameters;
import weUsedToLoveAsiats.BrainDetectTank;

public class FighterBrain extends BrainDetectTank {

    // ---PARAMETERS---//
    private static final double HEADINGPRECISION = 0.001;
    private static final double ANGLEPRECISION = 0.1;
    private static final int ROCKY = 0x1EADDA;
    private static final int CARREFOUR = 0x5EC0;
    private static final int DARTY = 0x333;
    private static final int UNDEFINED = 0xBADC0DE;
    private int CPT = 0;

    enum BOUSSOLE {DEVANT, DERRIERE, GAUCHE, DROITE}

    ;
    // ---VARIABLES---//
    private boolean dodgeLeftTask, dodgeRightTask, dodgeTask, moveFrontTask, moveBackTask;
    private double distLateral, distTop;
    private boolean isMoving;
    private int whoAmI;
    private boolean doNotShoot;
    private int nbTurns = 0;
    private static Random rand = new Random();

    // ---CONSTRUCTORS---//
    public FighterBrain() {
        super();
    }

    private static int lol = 0;

    // ---ABSTRACT-METHODS-IMPLEMENTATION---//
    public void activate() {
        super.activate();
        // ODOMETRY CODE
        whoAmI = lol++;
        //        MasterMind.getInstance().addTanks(this);
        // INIT
        moveFrontTask = false;
        moveBackTask = false;
        dodgeTask = false;
        dodgeLeftTask = false;
        dodgeRightTask = false;
        isMoving = false;
    }

    public void step() {
        super.step();
        //        if(CPT <10){
        //            stepTurn(Parameters.Direction.RIGHT);
        //            CPT++;
        //            return;
        //        }
        logPosition();

        //        ArrayList<IRadarResult> radarResults;
        //        if (getHealth() <= 0)
        //            return;
        //        if (whoAmI % 3 == 2) {
        //            if (isSameDirection(getHeading(), Parameters.NORTH)) {
        //                distTop++;
        //            }
        //            if (isSameDirection(getHeading(), Parameters.EAST))
        //                distLateral++;
        //        }
        //        AUTOMATON
        //        /*** Permet de reculer lorsque trop rpes ***/
        //        if (moveBackTask && nbTurns == 0) {
        //            moveBackTask = false;
        //            dodgeObstacle();
        //        }
        //        if (moveBackTask && nbTurns > 0) {
        //            moveBack();
        //            nbTurns--;
        //            return;
        //        }
        //
        //        /*** Permet de reculer lorsque trop rpes ***/
        //        if (moveFrontTask && nbTurns == 0) {
        //            moveFrontTask = false;
        //        }
        //        if (moveFrontTask && nbTurns > 0) {
        //            move();
        //            nbTurns--;
        //            return;
        //        }
        //        /*** Permet au robot de se positioner vers son NORD ***/
        //        if (dodgeTask && nbTurns == 0) {
        //            dodgeTask = false;
        //            dodgeLeftTask = false;
        //            dodgeRightTask = false;
        //        }
        //        /***
        //         * Tant que le robot n'est pas bien positionne on tourne a droite
        //         * jusqu'a atteindre le NORD
        //         ***/
        //        if (dodgeTask && nbTurns > 0) {
        //            if (dodgeLeftTask)
        //                stepTurn(Parameters.Direction.LEFT);
        //            else
        //                stepTurn(Parameters.Direction.RIGHT);
        //            nbTurns--;
        //            return;
        //        }
        //
        //        /***
        //         * Si le robot n'est pas en mode tourner et qu'il detecte un wall alors
        //         * tourne a gauche
        //         ***/
        //        if ((detectFront().getObjectType() == IFrontSensorResult.Types.WALL || detectFront()
        //                        .getObjectType() == IFrontSensorResult.Types.Wreck)) {
        //            for (IRadarResult r : detectRadar()) {
        //                if (r.getObjectType() == IRadarResult.Types.Wreck && r.getObjectDistance() <= r
        //                                .getObjectRadius() + Parameters.teamAMainBotRadius + 80) {
        //                    dodgeObstacle(r.getObjectDirection(), r.getObjectDistance());
        //                    return;
        //                }
        //            }
        //            dodgeObstacle();
        //            return;
        //        }
        //
        //        if (!dodgeTask && !moveBackTask) {
        //            radarResults = detectRadar();
        //            int enemyFighters = 0, enemyPatrols = 0;
        //            double enemyDirection = 0;
        //            doNotShoot = false;
        //            for (IRadarResult r : radarResults) {
        //                /** Focus le Main **/
        //                if (r.getObjectType() == IRadarResult.Types.OpponentMainBot) {
        //                    enemyFighters++;
        //                    enemyDirection = r.getObjectDirection();
        //                }
        //                /** Au cas ou il ya un secondary **/
        //                if (r.getObjectType() == IRadarResult.Types.OpponentSecondaryBot) {
        //                    if (enemyFighters == 0)
        //                        enemyDirection = r.getObjectDirection();
        //                    enemyPatrols++;
        //                }
        //                /** Ne pas tirer sur friends **/
        //                if (r.getObjectType() == IRadarResult.Types.TeamMainBot
        //                                || r.getObjectType() == IRadarResult.Types.TeamSecondaryBot) {
        //                    if (isInFrontOfMe(r.getObjectDirection()) && enemyFighters + enemyPatrols == 0) {
        //                        doNotShoot = true;
        //                    }
        //                    if (r.getObjectDistance() <= r.getObjectRadius() + Parameters.teamAMainBotRadius + 80) {
        //                        dodgeObstacle(r.getObjectDirection(), r.getObjectDistance());
        //                        return;
        //                    }
        //                }
        //                /** Reculer si trop proche **/
        //                if (r.getObjectType() == IRadarResult.Types.TeamMainBot || r
        //                                .getObjectType() == IRadarResult.Types.TeamSecondaryBot || r
        //                                .getObjectType() == IRadarResult.Types.Wreck) {
        //                    if (r.getObjectDistance() <= r
        //                                    .getObjectRadius() + Parameters.teamAMainBotRadius + 20 && !dodgeTask) {
        //                        moveBackTast(r.getObjectDirection());
        //                        return;
        //                    }
        //                }
        //            }
        //
        //            /*** Comporte de base lorsque dennemi detecte ***/
        //            if (enemyFighters + enemyPatrols > 0) {
        //                attack(enemyDirection);
        //                return;
        //            }
        //        }
        //
        //        /*** DEFAULT COMPORTEMENT ***/
        //        double randDouble = Math.random();
        //        if (randDouble <= 0.60) {
        //            move();
        //            return;
        //        }
        //        if (randDouble <= 0.80) {
        //            stepTurn(Parameters.Direction.LEFT);
        //            return;
        //        }
        //        if (randDouble <= 1.00) {
        //            stepTurn(Parameters.Direction.RIGHT);
        //            return;
        //        }
    }

    private void dodgeObstacle() {
        dodgeTask = true;
        if (Math.random() > 0.5) {
            dodgeLeftTask = true;
        } else {
            dodgeRightTask = true;
        }
        nbTurns = rand.nextInt(40);
    }

    private void dodgeObstacle(double pos, double distance) {
        dodgeTask = true;
        if (isADroite(pos) && isDevant(pos)) {
            dodgeLeftTask = true;
            nbTurns = rand.nextInt(40);
            return;
        }
        if (isAGauche(pos) && isDevant(pos)) {
            dodgeRightTask = true;
            nbTurns = rand.nextInt(40);
            return;
        }
        if (isDevant(pos)) {
            moveBackTask = true;
            nbTurns = rand.nextInt(40);
            return;
        }
        if (isDerriere(pos)) {
            moveFrontTask = true;
            nbTurns = rand.nextInt(40);
            return;
        }
    }

    private void moveBackTast(double pos) {
        if (isDerriere(pos)) {
            moveFrontTask = true;
        } else {
            moveBackTask = true;
        }
        nbTurns = rand.nextInt(40);
    }

    private void myMove() {
        isMoving = !isMoving;
        if (isMoving)
            move();
        else if (!doNotShoot)
            fire(getHeading());
    }

    private void attack(double enemyDirection) {
        isMoving = !isMoving;
        if (isMoving) {
            if (isDerriere(enemyDirection))
                move();
            else
                moveBack();
            return;
        } else if (!doNotShoot) {
            fire(enemyDirection);
            return;
        }
        if (Math.random() >= 0.5) {
            stepTurn(Parameters.Direction.LEFT);
        } else {
            stepTurn(Parameters.Direction.RIGHT);
        }
    }

    private boolean isHeading(double dir) {
        return Math.abs(Math.sin(getHeading() - dir)) < HEADINGPRECISION;
    }

    private boolean isSameDirection(double dir1, double dir2) {
        return Math.abs(dir1 - dir2) < ANGLEPRECISION;
    }

    private boolean isInFrontOfMe(Double enemy) {
        double heading = getHeading();
        double left = 0.15 * Math.PI;
        double right = -0.15 * Math.PI;
        boolean res = enemy <= (heading + left) % (2 * Math.PI) && enemy >= (heading + right) % (2 * Math.PI);
        return res;
    }

    private boolean isDevant(double pos) {
        double heading = getHeading();
        double left = 0.5 * Math.PI;
        return pos <= (heading + left) % (2 * Math.PI) && pos >= (heading - left) % (2 * Math.PI);
    }

    private boolean isDerriere(double pos) {
        return !isDevant(pos);
    }

    private boolean isAGauche(double pos) {
        double heading = getHeading();
        double left = Math.PI;
        return pos <= heading % (2 * Math.PI) && pos >= (heading - left) % (2 * Math.PI);
    }

    private boolean isADroite(double pos) {
        return !isAGauche(pos);
    }
}
