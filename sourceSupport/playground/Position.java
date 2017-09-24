package playground;

import java.awt.Color;







public class Position
{
  private Color color;
  private Enumerations.GroundType type;
  
  public Position(Color color, Enumerations.GroundType type)
  {
    this.color = color;
    this.type = type;
  }
  
  public Color getColor()
  {
    return color;
  }
  
  public Enumerations.GroundType getType() { return type; }
  
  public void setColor(Color color) {
    this.color = color;
  }
  
  public void setType(Enumerations.GroundType type) { this.type = type; }
  

  public boolean isAccessible()
  {
    return type != Enumerations.GroundType.WALL;
  }
  
  public boolean isStairWay() { return type == Enumerations.GroundType.STAIRWAYTOHEAVEN; }
}
