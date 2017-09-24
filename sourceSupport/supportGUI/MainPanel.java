package supportGUI;

import java.awt.CardLayout;
import javax.swing.JPanel;






class MainPanel
  extends JPanel
{
  private static final String GREETZ = HardCodedParameters.greetingsZoneId;
  private static final String SIM = HardCodedParameters.simulatorZoneId;
  
  private CardLayout layout;
  
  private DisplayGreetings greetz;
  private SimulatorPanel sim;
  
  protected MainPanel()
  {
    super(new CardLayout());
    
    greetz = new DisplayGreetings();
    sim = new SimulatorPanel();
    add(greetz, GREETZ);
    add(sim, SIM);
    layout = ((CardLayout)getLayout());
  }
  
  protected void start()
  {
    greetz.setSize(getSize());
    sim.setSize(getSize());
    greetz.start();
    sim.start();
    layout.show(this, GREETZ);
  }
  
  protected void firstAction()
  {
    greetz.stop();
    layout.show(this, SIM);
  }
  
  protected void shiftLeftAll() {
    sim.shiftLeftAll();
  }
  
  protected void shiftUpAll() {
    sim.shiftUpAll();
  }
  
  protected void shiftDownAll() {
    sim.shiftDownAll();
  }
  
  protected void shiftRightAll() {
    sim.shiftRightAll();
  }
  
  protected void zoomOut() {
    sim.zoomOut();
  }
  
  protected void zoomIn() {
    sim.zoomIn();
  }
  
  protected void pauseSimulation() {
    sim.pauseSimulation();
  }
  
  protected void startSimulation() {
    sim.startSimulation();
  }
  
  protected void reloadSimulation() {
    sim.reloadSimulation();
  }
  
  protected void startPauseSimulation() {
    sim.startPauseSimulation();
  }
  
  protected void removeTeamB() {
    sim.removeTeamB();
  }
  
  protected void defaultAction() {
    sim.defaultAction();
  }
}
