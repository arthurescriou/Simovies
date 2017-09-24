package supportGUI;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;






class Keymaps
  implements KeyListener
{
  private MainPanel mainPanel;
  private String state;
  
  protected Keymaps(MainPanel mainPanel)
  {
    this.mainPanel = mainPanel;
    state = HardCodedParameters.greetingsZoneId;
  }
  


  public void keyTyped(KeyEvent event)
  {
    if (state.equals(HardCodedParameters.greetingsZoneId)) {
      state = HardCodedParameters.simulatorZoneId;
      mainPanel.firstAction();
      return;
    }
    
    switch (event.getKeyChar()) {
    case 'h': 
      mainPanel.shiftLeftAll();
      break;
    case 'j': 
      mainPanel.shiftUpAll();
      break;
    case 'k': 
      mainPanel.shiftDownAll();
      break;
    case 'l': 
      mainPanel.shiftRightAll();
      break;
    case '-': 
      mainPanel.zoomOut();
      break;
    case '+': 
      mainPanel.zoomIn();
      break;
    case 'p': 
      mainPanel.pauseSimulation();
      break;
    case 's': 
      mainPanel.startSimulation();
      break;
    case ' ': 
      mainPanel.startPauseSimulation();
      break;
    case 'r': 
      mainPanel.reloadSimulation();
      break;
    case 'x': 
      mainPanel.removeTeamB();
      break;
    default: 
      mainPanel.defaultAction();
    }
    
  }
  

  public void keyPressed(KeyEvent event)
  {
    if (state.equals(HardCodedParameters.greetingsZoneId)) {
      state = HardCodedParameters.simulatorZoneId;
      mainPanel.firstAction();
      return;
    }
    
    switch (event.getKeyCode()) {
    case 37: 
      mainPanel.shiftLeftAll();
      break;
    case 38: 
      mainPanel.shiftUpAll();
      break;
    case 40: 
      mainPanel.shiftDownAll();
      break;
    case 39: 
      mainPanel.shiftRightAll();
    }
    
  }
  
  public void keyReleased(KeyEvent arg0)
  {
    if (state.equals(HardCodedParameters.greetingsZoneId)) {
      state = HardCodedParameters.simulatorZoneId;
      mainPanel.firstAction();
      return;
    }
  }
}
