/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package tools;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import java.awt.*;
import java.awt.geom.Point2D.Double;

import javafx.util.Pair;

public class CoordHelper {

    public static PolarCoordinate cartToPol(int origX, int origY, int toX, int toY) {
        return cartToPol(new Point(round(origX), round(origY)), new Point(round(toX), round(toY)));
    }

    public static PolarCoordinate cartToPol(int origX, int origY, Point dest) {
        return cartToPol(new Point(round(origX), round(origY)), dest);
    }

    public static PolarCoordinate cartToPol(Point orig, int toX, int toY) {
        return cartToPol(orig, new Point(round(toX), round(toY)));
    }

    public static PolarCoordinate cartToPol(Point orig, Point to) {
        return new PolarCoordinate(atan2(to.getY() - orig.getY(), to.getX() - orig.getX()), orig.distance(to));
    }

    public static CartCoordinate poltoCart(CartCoordinate orig, PolarCoordinate polCo) {
        return polToCart(orig, polCo.getAngle(), polCo.getDist());
    }

    public static CartCoordinate polToCart(double origX, double origY, double angle, double dist) {
        double x = origX + (dist * cos(angle));
        x = (x > 3000) ? 3000 : x;
        x = (x < 0) ? 0 : x;
        double y = origY + (dist * sin(angle));
        y = (y > 2000) ? 2000 : y;
        y = (y < 0) ? 0 : y;
        return new CartCoordinate(x, y);
    }

    public static CartCoordinate polToCart(CartCoordinate orig, double angle, double dist) {
        return polToCart(orig.getX(), orig.getY(), angle, dist);
    }
}
