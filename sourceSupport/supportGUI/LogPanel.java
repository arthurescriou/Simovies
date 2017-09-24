package supportGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;
import robotsimulator.SimulatorEngine;









class LogPanel
  extends JPanel
{
  protected static final double userShrinkFactor = 0.25D;
  private LogMessage logMessage;
  private HelpMessage helpMessage;
  
  protected LogPanel()
  {
    super(new BorderLayout());
    
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    
    logMessage = new LogMessage();
    helpMessage = new HelpMessage();
    logMessage.setBorder(loweredbevel);
    helpMessage.setBorder(loweredbevel);
    add(logMessage, "Center");
    add(helpMessage, "East");
  }
  
  protected LogMessage getLogGUI()
  {
    return logMessage;
  }
  


  protected void bind(SimulatorEngine engine) { logMessage.bind(engine); }
  
  protected void start(int width, int height) {
    resetSize(width, height);
    logMessage.start(width - (int)(0.25D * width), height);
    helpMessage.start((int)(0.25D * width), height);
  }
  
  private void resetSize(int width, int height) {
    setPreferredSize(new Dimension(width, height));
  }
}
