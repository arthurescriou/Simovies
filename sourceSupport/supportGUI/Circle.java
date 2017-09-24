package supportGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;







class Circle
{
  private Point center;
  private double radius;
  private Color c;
  
  protected Circle(Point center, double radius, Color c)
  {
    this.center = center;
    this.radius = radius;
    this.c = c;
  }
  
  protected Circle(Point center, double radius) { this.center = center;
    this.radius = radius;
    c = Color.BLACK;
  }
  
  protected Point getCenter()
  {
    return center;
  }
  
  protected double getRadius() { return radius; }
  
  protected Color getColor() {
    return c;
  }
  
  protected void setCenter(Point c) { center = c; }
  
  protected void setRadius(double r) {
    radius = r;
  }
  
  protected void setColor(Color c) { this.c = c; }
  

  protected void draw(Graphics2D g2d)
  {
    g2d.setStroke(new BasicStroke(4.0F, 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double(center.x - radius, center.y - radius, 2.0D * radius, 2.0D * radius));
  }
  
  protected void draw(Graphics2D g2d, int penSize) { g2d.setStroke(new BasicStroke(penSize, 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double(center.x - radius, center.y - radius, 2.0D * radius, 2.0D * radius));
  }
  
  protected void draw(Graphics2D g2d, int dx, int dy, double zoomFactor) { g2d.setStroke(new BasicStroke((int)(4.0D * zoomFactor), 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double((int)((center.x - radius) * zoomFactor) + dx, (int)((center.y - radius) * zoomFactor) + dy, 2.0D * radius * zoomFactor, 2.0D * radius * zoomFactor));
  }
  
  protected void draw(Graphics2D g2d, int dx, int dy, double zoomFactor, int penSize)
  {
    g2d.setStroke(new BasicStroke((int)(penSize * zoomFactor), 1, 1));
    g2d.setColor(c);
    g2d.draw(new Ellipse2D.Double((int)((center.x - radius) * zoomFactor) + dx, (int)((center.y - radius) * zoomFactor) + dy, 2.0D * radius * zoomFactor, 2.0D * radius * zoomFactor));
  }
}
