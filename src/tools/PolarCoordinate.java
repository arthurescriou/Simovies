/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package tools;

import static java.lang.Math.*;

public class PolarCoordinate {
    private double angle;
    private double dist;

    public PolarCoordinate(double angle, double dist) {
        this.angle = angle % 2* PI - PI;
        if (dist < 0) {
            throw new RuntimeException();
        }
        this.dist = dist;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public double getDist() {
        return dist;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }
}
