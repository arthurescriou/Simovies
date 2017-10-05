/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static algorithms.Orders.TURNLEFT;
import static algorithms.Orders.TURNRIGHT;
import static characteristics.Parameters.teamBSecondaryBotStepTurnAngle;
import static java.lang.Math.PI;
import static java.lang.Math.abs;

public class YesMasterScoutBrain extends BrainDetectScout {

    @Override
    public void step() {
        super.step();
//        logPosition();


        MasterMind.getInstance().giveMeOrderMaster(this);
    }


}
