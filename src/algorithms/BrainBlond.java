/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import static java.lang.Math.PI;
import static java.lang.Math.abs;

import characteristics.Parameters;
import robotsimulator.Brain;

public class BrainBlond extends Brain {

    @Override
    public void activate() {

    }

    @Override
    public void step() {
        move();
        return;

    }
}