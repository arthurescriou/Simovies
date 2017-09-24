package supportGUI;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;













class SelectionPanel
  extends JPanel
{
  private static final double charFactor = 0.175D;
  private Graphics2D g2d;
  
  protected SelectionPanel() {}
  
  protected void start(int width, int height)
  {
    setPreferredSize(new Dimension(width, height));
    repaint();
  }
  
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    

    int charSize = (int)(0.175D * getWidth() / 2.0D);
    int lineSize = (int)(1.1D * charSize);
    int indentSize = (int)(1.1D * lineSize);
    g2d = ((Graphics2D)g.create());
    


    FileLoader loader = new FileLoader();
    String name = loader.getTeamAName();
    g2d.setFont(new Font(g2d.getFont().getName(), 1, 2 * charSize));
    g2d.drawString("Team A:", 5, 2 * lineSize);
    for (int i = 0; i < name.length(); i++) {
      g2d.drawString(name.substring(i, i + 1), 4 * indentSize, (6 + 2 * i) * lineSize);
    }
  }
}
