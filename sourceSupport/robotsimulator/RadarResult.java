package robotsimulator;

import characteristics.IRadarResult;
import characteristics.IRadarResult.Types;

public class RadarResult
  implements IRadarResult
{
  private IRadarResult.Types type;
  private double direction;
  private double distance;
  private double radius;
  
  public RadarResult(IRadarResult.Types type, double direction, double distance, double radius)
  {
    this.type = type;
    this.direction = direction;
    this.distance = distance;
    this.radius = radius;
  }
  
  public IRadarResult.Types getObjectType() { return type; }
  
  public double getObjectDirection() {
    return direction;
  }
  
  public double getObjectDistance() { return distance; }
  
  public double getObjectRadius() {
    return radius;
  }
}
