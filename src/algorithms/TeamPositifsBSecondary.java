/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: algorithms/CampBot.java 2014-11-04 buixuan.
 * ******************************************************/
package algorithms;

        import robotsimulator.Brain;
        import characteristics.IFrontSensorResult;
        import characteristics.Parameters;
        import characteristics.IRadarResult;
        import robotsimulator.RadarResult;

        import static characteristics.IRadarResult.Types.OpponentMainBot;
        import static characteristics.IRadarResult.Types.OpponentSecondaryBot;


public class TeamPositifsBSecondary extends Brain {
    private static final int ROCKY = 0x1EADDA;
    private static final int MARIO = 0x5EC0;
    private static final int UNDEFINED = 0xBADC0DE0;
    private static final double ANGLEPRECISION = 0.01;
    private boolean turnTask,turnRight,finished,taskOne,moveTask, moveTo;
    private double endTaskDirection;
    private int endTaskCounter;
    private static IFrontSensorResult.Types WALL=IFrontSensorResult.Types.WALL;
    private static IFrontSensorResult.Types TEAMMAIN=IFrontSensorResult.Types.TeamMainBot;
    private double oldAngle;
    double myX;
    double myY;
    double enemyX;
    double enemyY;
    private int whoAmI;
    public TeamPositifsBSecondary() { super(); }

    public void activate() {
        turnTask=false;
        finished=false;
        moveTask=true;
        moveTo=false;
        taskOne=true;
        oldAngle=myGetHeading();
        endTaskDirection=getHeading()+0.4*Math.PI;
        sendLogMessage("Moving and healthy.");
        whoAmI = ROCKY;
        for (IRadarResult o: detectRadar())
            if (isSameDirection(o.getObjectDirection(),Parameters.NORTH)) whoAmI=UNDEFINED;
        if (whoAmI == ROCKY){
            myX=Parameters.teamBSecondaryBot1InitX;
            myY=Parameters.teamBSecondaryBot1InitY;
        } else {
            myX=Parameters.teamBSecondaryBot2InitX;
            myY=Parameters.teamBSecondaryBot2InitY;
        }
    }
    public void step() {
        if(moveTo && moveTask) {

            sendLogMessage("J'avance vers l'ennemie : " +myX+","+myY);
//			if (detectFront().getObjectType()==WALL||detectFront().getObjectType()==TEAMMAIN)
//			{
//				System.out.println("je suis là");
//				stepTurn(Parameters.Direction.RIGHT);
//			}
            if(myX>=enemyX+20 && myX<=enemyY-20 && myY>=enemyX+20 && myY<=enemyY-20) {
                moveTo=false;
                moveTask=false;
                sendLogMessage("Je suis sur lennemie ");
            }
            else {

                move();
            }
        }
        else {
//			if (detectFront().getObjectType()==WALL||detectFront().getObjectType()==TEAMMAIN)
//			{
//				System.out.println("je suis là B");
//				stepTurn(Parameters.Direction.RIGHT);
//			}
            IRadarResult target = null;
            for (IRadarResult o: detectRadar()) {
                if(o.getObjectType()==IRadarResult.Types.OpponentSecondaryBot){
                    target = o;
                    sendLogMessage("Cible detecte : "+o.getObjectDistance() );
                    if(!isSameDirection(myGetHeading(), normalizeRadian(target.getObjectDirection()))){
                        endTaskDirection = normalizeRadian(target.getObjectDirection());
                        turnTask=true;
                        moveTask=false;
                        moveTo = true;
                        sendLogMessage("Je suis pas dessus : " +myX+","+myY);
                        enemyX=myX+o.getObjectDistance()*Math.cos(normalizeRadian(target.getObjectDirection()));
                        enemyY=myY+o.getObjectDistance()*Math.sin(normalizeRadian(target.getObjectDirection()));
                        break;
                    }
                    if(isSameDirection(myGetHeading(),normalizeRadian(target.getObjectDirection()))){
                        sendLogMessage("Je suis dessus : " + endTaskDirection);
                        turnTask=false;
                        moveTask=true;
                        break;
                    }
                }
            }
            if(moveTask) {
                myX+=Parameters.teamASecondaryBotSpeed*Math.cos(myGetHeading());
                myY+=Parameters.teamASecondaryBotSpeed*Math.sin(myGetHeading());
                if(!isSameDirection(myGetHeading(),Parameters.NORTHWEASTSEC)){
                    stepTurn(Parameters.Direction.RIGHT);
                    return;
                }
                move();
            }
            if (getHealth()<=0) { sendLogMessage("I'm dead.");return; }
            if (finished) { sendLogMessage("Camping point. Task complete.");return; }
            if (turnTask) {

                if (isHeading(endTaskDirection)) {
                    turnTask=false;
                    if (taskOne) endTaskCounter=200; else endTaskCounter=100;
                    moveTask=true;
                    move();
                } else {
                    stepTurn(Parameters.Direction.LEFT);
                }

            } else {

                moveTask=true;
                move();
            }
        }
    }

    /*
    if (endTaskCounter>0) {
      endTaskCounter--;
      move();
      return;
    } else {
      taskOne=false;
      if (detectFront().getObjectType()==WALL||detectFront().getObjectType()==TEAMMAIN)
      {
        stepTurn(Parameters.Direction.RIGHT);
        move();
      }

      turnTask=true;
      endTaskDirection=getHeading()+0.5*Math.PI;
      return;
    }
  }*/
    private boolean isHeading(double dir){
        return Math.abs(Math.sin(getHeading()-dir))<Parameters.teamBSecondaryBotStepTurnAngle;
    }
    private double normalizeRadian(double angle){
        double result = angle;
        while(result<0) result+=2*Math.PI;
        while(result>=2*Math.PI) result-=2*Math.PI;
        return result;
    }
    private boolean isSameDirection(double dir1, double dir2){
        return Math.abs(normalizeRadian(dir1)-normalizeRadian(dir2))<ANGLEPRECISION;
    }
    private double myGetHeading(){
        return normalizeRadian(getHeading());
    }

}