package robotsimulator;

import characteristics.IBrain;
import characteristics.Parameters;
import characteristics.Parameters.Direction;
import java.util.ArrayList;

public abstract class Brain implements IBrain
{
  private Command currentCmd;
  private Bot bot;
  private String logMessage;
  private int counter;
  private double fireDirection;
  
  private static enum Command
  {
    NONE,  MOVE,  MOVEBACK,  STEPTURNLEFT,  STEPTURNRIGHT,  FIRE;
    

    private Command() {}
  }
  

  public Brain()
  {
    counter = 0;
    logMessage = "Ready to rumble.";
  }
  
  public abstract void activate();
  
  public abstract void step();
  
  public double getHeading()
  {
    return bot.getHeading();
  }
  
  public double getHealth() { return bot.getHealth(); }
  
  public FrontSensorResult detectFront() {
    return bot.detectFront();
  }
  
  public ArrayList<characteristics.IRadarResult> detectRadar() { return bot.detectRadar(); }
  

  public void move()
  {
    currentCmd = Command.MOVE;
  }
  
  public void moveBack() { currentCmd = Command.MOVEBACK; }
  
  public void stepTurn(Parameters.Direction dir) {
    if (dir == Parameters.Direction.RIGHT) {
      currentCmd = Command.STEPTURNRIGHT;
    } else
      currentCmd = Command.STEPTURNLEFT;
  }
  
  public void fire(double dir) {
    currentCmd = Command.FIRE;
    fireDirection = dir;
  }
  
  public void broadcast(String message) { bot.broadcast(message); }
  
  public ArrayList<String> fetchAllMessages() {
    return bot.fetchAllMessages();
  }
  
  public void sendLogMessage(String message)
  {
    logMessage = message;
  }
  
  protected void bind(Bot bot)
  {
    this.bot = bot;
  }
  
  protected String getLogMessage() { return logMessage; }
  
  protected void activation() {
    currentCmd = Command.NONE;
    activate();
    action();
  }
  
  protected void stepAction() { currentCmd = Command.NONE;
    step();
    action();
  }
  
  private void action() { if (bot.getHealth() <= 0.0D) return;
    switch (Brain.Command.values()[this.currentCmd.ordinal()]) {
    case MOVE:
      bot.move();
      break;
      case MOVEBACK:
      bot.moveBack();
      break;
      case STEPTURNLEFT:
      bot.stepTurnLeft();
      break;
      case STEPTURNRIGHT:
      bot.stepTurnRight();
      break;
    case FIRE:
      if ((counter < 1) && (bot.hasRocket())) {
        bot.fire(fireDirection);
        counter = 21;
      }
      break;
    }
    counter = Math.max(counter - 1, 0);
  }
}
