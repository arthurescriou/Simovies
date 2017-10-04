/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import robotsimulator.Brain;

public class RussianBrain extends Brain{
    public RussianBrain() { super(); }

    public void activate() {
        sendLogMessage("Rush B");
    }
    public void step() {
        move();
        return;
    }

}
