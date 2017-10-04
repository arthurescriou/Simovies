package supportGUI;

import java.awt.*;
import java.awt.geom.Ellipse2D;

import tools.CartCoordinate;

class Circle
{
  private CartCoordinate center;
  private double radius;
  private Color c;
  
  protected Circle(CartCoordinate center, double radius, Color c)
  {
    this.center = center;
    this.radius = radius;
    this.c = c;
  }
  
  protected Circle(CartCoordinate center, double radius) { this.center = center;
    this.radius = radius;
    c = Color.BLACK;
  }
  
  protected CartCoordinate getCenter()
  {
    return center;
  }
  
  protected double getRadius() { return radius; }
  
  protected Color getColor() {
    return c;
  }
  
  protected void setCenter(CartCoordinate c) { center = c; }
  
  protected void setRadius(double r) {
    radius = r;
  }
  
  protected void setColor(Color c) { this.c = c; }
  

  protected void draw(Graphics2D g2d)
  {
    g2d.setStroke(new BasicStroke(4.0F, 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, 2.0D * radius, 2.0D * radius));
  }
  
  protected void draw(Graphics2D g2d, int penSize) { g2d.setStroke(new BasicStroke(penSize, 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double(center.getX() - radius, center.getY() - radius, 2.0D * radius, 2.0D * radius));
  }
  
  protected void draw(Graphics2D g2d, int dx, int dy, double zoomFactor) { g2d.setStroke(new BasicStroke((int)(4.0D * zoomFactor), 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double((int)((center.getX() - radius) * zoomFactor) + dx, (int)((center.getY() - radius) * zoomFactor) + dy, 2.0D * radius * zoomFactor, 2.0D * radius * zoomFactor));
  }
  
  protected void draw(Graphics2D g2d, int dx, int dy, double zoomFactor, int penSize)
  {
    g2d.setStroke(new BasicStroke((int)(penSize * zoomFactor), 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double((int)((center.getX() - radius) * zoomFactor) + dx, (int)((center.getY() - radius) * zoomFactor) + dy, 2.0D * radius * zoomFactor, 2.0D * radius * zoomFactor));
  }
}
