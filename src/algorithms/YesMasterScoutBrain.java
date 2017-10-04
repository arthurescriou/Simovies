/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

public class YesMasterScoutBrain extends BrainDetectScout {

    @Override
    public void step() {
        super.step();
        MasterMind.getInstance().giveMeOrderMaster(this);
    }
}
