/*
 * Copyright (C) by Courtanet, All Rights Reserved.
 */
package tools;

import static java.lang.Math.*;
import static java.lang.Math.cos;
import static java.lang.Math.round;
import static java.lang.Math.sin;

import java.awt.*;

import javafx.util.Pair;

public class CoordHelper {

    public static Pair<Double, Double> cartToPol(int origX, int origY, int toX, int toY) {
        return cartToPol(new Point(round(origX), round(origY)), new Point(round(toX), round(toY)));
    }

    public static Pair<Double, Double> cartToPol(int origX, int origY, Point dest) {
        return cartToPol(new Point(round(origX), round(origY)), dest);
    }

    public static Pair<Double, Double> cartToPol(Point orig, int toX, int toY) {
        return cartToPol(orig, new Point(round(toX), round(toY)));
    }

    public static Pair<Double, Double> cartToPol(Point orig, Point to) {
        return new Pair<>(atan2(to.getY() - orig.getY(), to.getX() - orig.getX()), orig.distance(to));
    }

    public static Point polToCart(Point orig, PolarCoordinate polCo) {
        return polToCart(orig, polCo.getAngle(), polCo.getDist());
    }

    public static Point polToCart(double origX, double origY, double angle, double dist) {
        long x = max(round(origX + (dist * cos(angle))),3000);
        long y = max(round(origY + (dist * sin(angle))),2000);
        return new Point((int) x, (int) y);
    }

    public static Point polToCart(Point orig, double angle, double dist) {
        return polToCart(orig.x , orig.y, angle, dist);
    }
}
