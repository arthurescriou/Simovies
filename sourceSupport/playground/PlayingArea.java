package playground;


public class PlayingArea
{
  private Position[][] position;
  
  private int xModifier;
  
  private int yModifier;
  
  private int realWidth;
  private int realHeight;
  private int width;
  private int height;
  
  public PlayingArea()
  {
    position = new Position['ರ']['ࣈ'];
    xModifier = 124;
    yModifier = 124;
    realWidth = 3248;width = 3000;
    realHeight = 2248;height = 2000;
  }
  









  public int getWidth()
  {
    return width;
  }
  
  public int getHeight() { return height; }
  
  public Position getPosition(int x, int y) {
    return position[(x + xModifier)][(y + yModifier)];
  }
  
  public void setPosition(int x, int y, Position p) { position[(x + xModifier)][(y + yModifier)] = p; }
}
