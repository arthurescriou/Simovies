package supportGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;







class Line
{
  private Point p;
  private Point q;
  private Color c;
  
  protected Line(Point p, Point q, Color c)
  {
    this.p = p;
    this.q = q;
    this.c = c;
  }
  
  protected Line(Point p, Point q) { this.p = p;
    this.q = q;
    c = Color.BLACK;
  }
  
  protected Point getP()
  {
    return p;
  }
  
  protected Point getQ() { return q; }
  
  protected Color getColor() {
    return c;
  }
  
  protected void setP(Point p) { this.p = p; }
  
  protected void setQ(Point q) {
    this.q = q;
  }
  
  protected void setColor(Color c) { this.c = c; }
  
  protected double distance() {
    return p.distance(q);
  }
  
  protected void draw(Graphics2D g2d)
  {
    g2d.setStroke(new BasicStroke(4.0F, 1, 1));
    g2d.setColor(c);
    g2d.drawLine(p.x, p.y, q.x, q.y);
  }
  
  protected void draw(Graphics2D g2d, int penSize) { g2d.setStroke(new BasicStroke(penSize, 1, 1));
    g2d.setColor(c);
    g2d.drawLine(p.x, p.y, q.x, q.y);
  }
  
  protected void draw(Graphics2D g2d, int dx, int dy, double zoomFactor) { g2d.setStroke(new BasicStroke((int)(4.0D * zoomFactor), 1, 1));
    g2d.setColor(c);
    g2d.drawLine((int)(p.x * zoomFactor) + dx, (int)(p.y * zoomFactor) + dy, (int)(q.x * zoomFactor) + dx, (int)(q.y * zoomFactor) + dy);
  }
  
  protected void draw(Graphics2D g2d, int dx, int dy, double zoomFactor, int penSize) {
    g2d.setStroke(new BasicStroke((int)(penSize * zoomFactor), 1, 1));
    g2d.setColor(c);
    g2d.drawLine((int)(p.x * zoomFactor) + dx, (int)(p.y * zoomFactor) + dy, (int)(q.x * zoomFactor) + dx, (int)(q.y * zoomFactor) + dy);
  }
}
