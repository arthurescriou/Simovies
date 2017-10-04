/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

public class YesMasterTankBrain extends BrainDetectTank{

    @Override
    public void step() {
        super.step();
        MasterMind.getInstance().giveMeOrderMaster(this);
    }
}
