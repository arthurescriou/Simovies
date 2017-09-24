package robotsimulator;



class Coordinates
{
  protected double x;
  

  protected double y;
  

  protected Coordinates(double x, double y)
  {
    this.x = x;
    this.y = y;
  }
  
  protected double getX()
  {
    return x;
  }
  
  protected double getY() { return y; }
  
  protected double distance(Coordinates q) {
    return Math.sqrt((x - x) * (x - x) + (y - y) * (y - y));
  }
}
