package robotsimulator;

public class Bullet
{
  private double x;
  private double y;
  private double heading;
  private double velocity;
  private double damage;
  private double radius;
  private double range;
  private int counter;
  
  public Bullet(double x, double y, double heading, double velocity, double damage, double radius, double range)
  {
    this.x = x;
    this.y = y;
    this.heading = heading;
    this.velocity = velocity;
    this.damage = damage;
    this.radius = radius;
    counter = ((int)(range / velocity) + 1);
  }
  
  public double getX() { return x; }
  
  public double getY() {
    return y;
  }
  
  public double getHeading() { return heading; }
  
  public double getVelocity() {
    return velocity;
  }
  
  public double getDamage() { return damage; }
  
  public double getRadius() {
    return radius;
  }
  
  public boolean isDestroyed() { return counter < 1; }
  
  public void step() {
    x += velocity * Math.cos(heading);
    y += velocity * Math.sin(heading);
    counter -= 1;
  }
}
