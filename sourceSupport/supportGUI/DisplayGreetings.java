package supportGUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JPanel;
import javax.swing.Timer;











class DisplayGreetings
  extends JPanel
{
  private static final double logFactor = 0.15D;
  private static final double userFactor = 0.25D;
  private static final double charFactor = 0.1175D;
  private static final double charControlFactor = 0.01275D;
  private Graphics2D g2d;
  private Timer blingBling;
  
  protected DisplayGreetings()
  {
    blingBling = new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        repaint();
      }
    });
  }
  
  protected void start()
  {
    blingBling.start();
  }
  
  protected void stop() { blingBling.stop(); }
  
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);
    

    int charSize = (int)(1.1D * Math.min(0.017625D * getHeight(), 0.01275D * getWidth() / 0.75D));
    int lineSize = (int)(1.1D * charSize);
    int indentSize = (int)(1.1D * lineSize);
    g2d = ((Graphics2D)g.create());
    g2d.setFont(new Font(g2d.getFont().getName(), 1, charSize));
    g2d.drawString("Simovies - Eurobot 2015 Robomovies Simulator.", 5, getHeight() - 3 * lineSize);
    g2d.drawString("Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.", 5, getHeight() - 2 * lineSize);
    g2d.drawString("GPL version>=3 <http://www.gnu.org/licenses/>.", 5, getHeight() - lineSize);
    
    charSize = 3 * charSize;
    lineSize = (int)(1.1D * charSize);
    indentSize = (int)(1.1D * lineSize);
    
    g2d.setFont(new Font(g2d.getFont().getName(), 2, charSize));
    g2d.drawString("---Press any key to process---", getWidth() / 2 - 8 * charSize, (int)(0.6D * getHeight()));
    
    charSize = 4 * charSize;
    lineSize = (int)(1.1D * charSize);
    indentSize = (int)(1.1D * lineSize);
    
    String oldFontName = g2d.getFont().getName();
    Random gen = new Random();
    Font[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
    g2d.setColor(new Color((float)(0.75D * Math.random()), (float)(0.75D * Math.random()), (float)(0.75D * Math.random())));
    try {
      g2d.setFont(new Font(fonts[gen.nextInt(fonts.length)].getName(), 1, charSize));
    } catch (Exception _) {
      g2d.setFont(new Font(oldFontName, 1, charSize));
    }
    g2d.drawString("Simovies", getWidth() / 2 - (int)(2.5D * charSize), getHeight() / 3);
  }
}
