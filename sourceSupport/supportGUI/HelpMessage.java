package supportGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JPanel;







class HelpMessage
  extends JPanel
{
  private static final double charFactor = 0.1175D;
  private static final double charControlFactor = 0.051D;
  private Graphics2D g2d;
  private int charSize;
  private int lineSize;
  private int indentSize;
  
  protected HelpMessage()
  {
    addComponentListener(new ComponentListener() { public void componentHidden(ComponentEvent e) {}
      
      public void componentMoved(ComponentEvent e) {}
      
      public void componentShown(ComponentEvent e) {}
      public void componentResized(ComponentEvent e) { HelpMessage.this.resetSize(getWidth(), getHeight()); }
    });
  }
  



  protected void start(int width, int height) { resetSize(width, height); }
  
  private void resetSize(int width, int height) {
    setPreferredSize(new Dimension(width, height));
    charSize = ((int)Math.min(0.1175D * height, 0.051D * width));
    lineSize = ((int)(1.1D * charSize));
    indentSize = ((int)(1.1D * lineSize));
    repaint();
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    

    g2d = ((Graphics2D)g.create());
    g2d.setFont(new Font(g2d.getFont().getName(), 1, charSize));
    g2d.drawString("Keyboard:", 5, lineSize);
    g2d.drawString("s, p, spacebar: start and pause", indentSize, 2 * lineSize);
    g2d.drawString("r: reload (might take some time...)", indentSize, 3 * lineSize);
    g2d.drawString("h, j, k, l, arrow keys: move view", indentSize, 4 * lineSize);
    g2d.drawString("+, -: zoom view", indentSize, 5 * lineSize);
  }
}
