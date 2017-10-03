/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package algorithms;

import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.RadarResult;

public class Position {
    private double x;
    private double y;
    private RadarResult.Types types;

    public Position(double x, double y) {
        this.x = x;
        this.y = y;
        types = null;
    }

    public void setTypes(RadarResult.Types types) {
        this.types = types;
    }

    public RadarResult.Types getTypes() {
        return types;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
