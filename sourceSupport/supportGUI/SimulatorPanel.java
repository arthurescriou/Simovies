package supportGUI;

import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import robotsimulator.SimulatorEngine;











class SimulatorPanel
  extends JPanel
{
  protected static final double userShrinkFactor = 0.25D;
  protected static final double logShrinkFactor = 0.15D;
  private DisplayGame displayGame;
  private UserPanel userPanel;
  private LogPanel logPanel;
  private SimulatorEngine engine;
  private boolean pause;
  
  protected SimulatorPanel()
  {
    super(new BorderLayout());
    
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    Border empty = BorderFactory.createEmptyBorder();
    
    displayGame = new DisplayGame();
    userPanel = new UserPanel();
    logPanel = new LogPanel();
    
    displayGame.setBorder(loweredbevel);
    userPanel.setBorder(empty);
    logPanel.setBorder(empty);
    
    add(displayGame, "Center");
    add(userPanel, "East");
    add(logPanel, "South");
    
    engine = new SimulatorEngine(displayGame, logPanel.getLogGUI());
    displayGame.bind(engine);
    logPanel.bind(engine);
    
    pause = true;
  }
  
  protected void start()
  {
    displayGame.start(getWidth() - (int)(0.25D * getWidth()), getHeight() - (int)(0.15D * getHeight()));
    userPanel.start((int)(0.25D * getWidth()), getHeight() - (int)(0.15D * getHeight()));
    logPanel.start(getWidth(), (int)(0.15D * getHeight()));
  }
  
  protected void shiftLeftAll()
  {
    displayGame.shiftLeftAll();
  }
  
  protected void shiftUpAll() {
    displayGame.shiftUpAll();
  }
  
  protected void shiftDownAll() {
    displayGame.shiftDownAll();
  }
  
  protected void shiftRightAll() {
    displayGame.shiftRightAll();
  }
  
  protected void zoomOut() {
    displayGame.zoomOut();
  }
  
  protected void zoomIn() {
    displayGame.zoomIn();
  }
  
  protected void pauseSimulation() {
    engine.pauseSimulation();
    pause = true;
  }
  
  protected void startSimulation() {
    engine.startSimulation();
    pause = false;
  }
  
  protected void reloadSimulation() {
    engine.reloadSimulation();
    pause = true;
  }
  
  protected void startPauseSimulation() {
    if (pause) {
      engine.startSimulation();
      pause = false;
    } else {
      engine.pauseSimulation();
      pause = true;
    }
  }
  
  protected void removeTeamB() {
    engine.removeTeamB();
  }
  
  protected void defaultAction() {}
}
