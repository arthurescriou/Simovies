package supportGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;








class UserPanel
  extends JPanel
{
  protected static final double menuShrinkFactor = 0.5D;
  private SelectionPanel selectionPanel;
  private MenuPanel menuPanel;
  
  protected UserPanel()
  {
    super(new BorderLayout());
    
    Border loweredbevel = BorderFactory.createLoweredBevelBorder();
    
    selectionPanel = new SelectionPanel();
    menuPanel = new MenuPanel();
    selectionPanel.setBorder(loweredbevel);
    menuPanel.setBorder(loweredbevel);
    add(selectionPanel, "Center");
    add(menuPanel, "East");
  }
  
  protected void start(int width, int height)
  {
    setPreferredSize(new Dimension(width, height));
    selectionPanel.setPreferredSize(new Dimension(width - (int)(0.5D * width), height));
    menuPanel.setPreferredSize(new Dimension((int)(0.5D * width), height));
  }
}
