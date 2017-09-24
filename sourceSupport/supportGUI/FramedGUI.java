package supportGUI;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;









class FramedGUI
  extends JFrame
{
  private static final double shrinkFactor = 0.95D;
  private static final int defaultWidth = 1280;
  private static final int defaultHeight = 1024;
  private MainPanel mainPanel;
  
  protected FramedGUI(String title) { this(title, 1280, 1024); }
  
  protected FramedGUI(String title, int width, int height) {
    super(title);
    
    setDefaultCloseOperation(3);
    
    mainPanel = new MainPanel();
    getContentPane().add(mainPanel);
    

    if ((width < 384.0D) || (height < 307.2D)) {
      pack();
    } else {
      double w = Toolkit.getDefaultToolkit().getScreenSize().getWidth() * 0.95D;
      double h = Toolkit.getDefaultToolkit().getScreenSize().getHeight() * 0.95D;
      setSize(new Dimension((int)w, (int)h));
    }
    mainPanel.setSize(getSize());
    mainPanel.start();
    
    setVisible(true);
    addKeyListener(new Keymaps(mainPanel));
  }
}
