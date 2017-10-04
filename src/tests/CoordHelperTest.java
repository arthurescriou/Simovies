package tests;

import org.junit.Assert;
import org.junit.Test;

import tools.CartCoordinate;

/**
 * Created by yfouquer on 04/10/17.
 */
public class CoordHelperTest {

    @Test
    public void cartToPol1() throws Exception {
        CartCoordinate p = new CartCoordinate(10, 10);
        CartCoordinate q = new CartCoordinate(10, 15);

        float angle = (float) Math.atan2(q.getY() - p.getY(), q.getX() - p.getY());
        //        float angle = (float) Math.toRadians(Math.toDegrees(Math.atan2(q.getY() - p.getY(), q.getX()- p
        // .getY())));
        System.out.println(angle);
        //        double angle = CoordHelper.cartToPol(p, q).getAngle();
        Assert.assertEquals(angle, Math.PI / 2, 0.01);
    }

    @Test
    public void cartToPol2() throws Exception {
        CartCoordinate p = new CartCoordinate(10, 10);
        CartCoordinate q = new CartCoordinate(100, 10);
        float angle = (float) Math.atan2(q.getY() - p.getY(), q.getX() - p.getY());
        //float angle = (float) Math.toRadians(Math.toDegrees(Math.atan2(q.getY() - p.getY(), q.getX()- p.getY())));
        System.out.println(angle);
        //        double angle = CoordHelper.cartToPol(p, q).getAngle();
        Assert.assertEquals(angle, 0, 0.01);
    }

    @Test
    public void cartToPol3() throws Exception {
        CartCoordinate p = new CartCoordinate(10, 10);
        CartCoordinate q = new CartCoordinate(15, 15);
        float angle = (float) Math.atan2(q.getY() - p.getY(), q.getX() - p.getY());
        //        float angle = (float) Math.toRadians(Math.toDegrees(Math.atan2(q.getY() - p.getY(), q.getX()- p
        // .getY())));
        System.out.println(angle);
        //        double angle = CoordHelper.cartToPol(p, q).getAngle();
        Assert.assertEquals(angle, Math.PI / 4, 0.01);
    }


}