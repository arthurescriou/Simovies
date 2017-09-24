package robotsimulator;

class Coordinates {

  protected double x;

  protected double y;

  protected Coordinates(double x, double y) {
    this.x = x;
    this.y = y;
  }

  protected double getX() {
    return x;
  }

  protected double getY() { return y; }

  protected double distance(Coordinates q) {
    return Math.sqrt((this.x - q.x) * (this.x - q.x) + (this.y - q.y) * (this.y - q.y));
  }
}
