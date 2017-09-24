package supportGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import javax.swing.JPanel;
import robotsimulator.Bot;
import robotsimulator.SimulatorEngine;






public class LogMessage
  extends JPanel
{
  private static final double charFactor = 0.1175D;
  private static final double charControlFactor = 0.01275D;
  private Graphics2D g2d;
  private SimulatorEngine engine;
  private int charSize;
  private int lineSize;
  private int indentSize;
  
  protected LogMessage()
  {
    addComponentListener(new ComponentListener() { public void componentHidden(ComponentEvent e) {}
      
      public void componentMoved(ComponentEvent e) {}
      
      public void componentShown(ComponentEvent e) {}
      public void componentResized(ComponentEvent e) { LogMessage.this.resetSize(getWidth(), getHeight()); }
    });
  }
  

  protected void bind(SimulatorEngine engine)
  {
    this.engine = engine;
  }
  
  protected void start(int width, int height) { resetSize(width, height); }
  
  private void resetSize(int width, int height) {
    setPreferredSize(new Dimension(width, height));
    charSize = ((int)Math.min(0.1175D * height, 0.01275D * width));
    lineSize = ((int)(1.1D * charSize));
    indentSize = ((int)(1.1D * lineSize));
    repaint();
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    

    g2d = ((Graphics2D)g.create());
    g2d.setFont(new Font(g2d.getFont().getName(), 1, charSize));
    g2d.drawString("A main 1: " + ((Bot)engine.getBots().get(0)).getLogMessage(), 5, lineSize);
    g2d.drawString("B main 1: " + ((Bot)engine.getBots().get(3)).getLogMessage(), 5 + getWidth() / 2, lineSize);
    g2d.drawString("A main 2: " + ((Bot)engine.getBots().get(1)).getLogMessage(), 5, 2 * lineSize);
    g2d.drawString("B main 2: " + ((Bot)engine.getBots().get(4)).getLogMessage(), 5 + getWidth() / 2, 2 * lineSize);
    g2d.drawString("A main 3: " + ((Bot)engine.getBots().get(2)).getLogMessage(), 5, 3 * lineSize);
    g2d.drawString("B main 3: " + ((Bot)engine.getBots().get(5)).getLogMessage(), 5 + getWidth() / 2, 3 * lineSize);
    g2d.drawString("A secondary 1: " + ((Bot)engine.getBots().get(6)).getLogMessage(), 5, 4 * lineSize);
    g2d.drawString("B secondary 1: " + ((Bot)engine.getBots().get(8)).getLogMessage(), 5 + getWidth() / 2, 4 * lineSize);
    g2d.drawString("A secondary 2: " + ((Bot)engine.getBots().get(7)).getLogMessage(), 5, 5 * lineSize);
    g2d.drawString("B secondary 2: " + ((Bot)engine.getBots().get(9)).getLogMessage(), 5 + getWidth() / 2, 5 * lineSize);
  }
}
