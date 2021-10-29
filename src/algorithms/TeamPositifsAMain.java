package algorithms;

import java.util.ArrayList;
import java.util.Random;

import characteristics.IFrontSensorResult;
import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;

public class TeamPositifsAMain  extends Brain {
    //---PARAMETERS---//
    private static final double HEADINGPRECISION = 0.001;
    private static final double ANGLEPRECISION = 0.1;

    private static final int ALPHA = 0x1EADDA;
    private static final int BETA = 0x5EC0;
    private static final int GAMMA = 0x333;
    private static final int TEAM = 0xBADDAD;
    private static final int UNDEFINED = 0xBADC0DE0;

    private static final int FORWARD = 0xFA11BAC;
    private static final int ROGER = 0x0C0C0C0C;
    private static final int OVER = 0xC00010FF;

    private static final int TURNDEPART = 1;
    private static final int MOVEDEPART = 2;
    private static final int TURNRIGHT = 3;
    private static final int MOVERIGHT = 4;


    public static final double EAST = 0, //clockwise trigonometric unit, according to screen pixel coordinate reference
            NORTH_WEST = 1.25*Math.PI,

    WEST = Math.PI,
            SOUTH_WEST = 0.75*Math.PI,

    SOUTH = 0.5*Math.PI,
            SOUTH_EAST = 0.25*Math.PI,
            NORTH = 1.5*Math.PI,
            NORTH_EAST = 1.75*Math.PI,
            RIGHTTURNFULLANGLE = 0.5*Math.PI, //value set according to screen pixel coordinate reference
            LEFTTURNFULLANGLE = -0.5*Math.PI;


    private static final int SWINGTASK = 6;
    private static final int FREEZE = -1;
    private static final int SINK = 0xBADC0DE1;
    private static final int TURNSE = 10;
    private static final int MOVESE = 11;
    private static final int TURNETERNAL = 12;
    private static final int TURNEAST = 20;
    private static final int MOVEEAST = 21;
    private static final int TURNSOUTH = 22;
    private static final int WAITINGMATE = 23;
    private static final int MOVETOGETHER = 24;
    private static final int MOVERETURN = 25;
    private static final int FIRE = 31;
    private static final int FIREEAST = 32;
    private static final int FIRETOGETHER = 33;
    private static final int FIRERETURN = 34;
    private static final double FIREANGLEPRECISION = 30;
    private static final int TURNRETURN = 0;

    //---VARIABLES---//
    private int state;
    private int previousstate;
    private double myX,myY,oldY,oldX;
    private double ennemyPositionX,ennemyPositionY;
    private boolean isMoving,isMovingBack;
    private int whoAmI;
    private int fireRythm,rythm,counter;
    private double targetX,targetY;
    private boolean fireOrder;
    private boolean forwardOrder;
    private Random gen;
    private boolean friendlyFire;
    int countDown;

    //---CONSTRUCTORS---//
    public TeamPositifsAMain() { super(); gen = new Random();}

    //---ABSTRACT-METHODS-IMPLEMENTATION---//
    public void activate() {
        //ODOMETRY CODE
        whoAmI = GAMMA;
        for (IRadarResult o: detectRadar())
            if (isSameDirection(o.getObjectDirection(),NORTH)) whoAmI=ALPHA;
        for (IRadarResult o: detectRadar())
            if (isSameDirection(o.getObjectDirection(),SOUTH) && whoAmI!=GAMMA) whoAmI=BETA;
        if (whoAmI == GAMMA){
            myX=Parameters.teamAMainBot1InitX;
            myY=Parameters.teamAMainBot1InitY;
        } else {
            myX=Parameters.teamAMainBot2InitX;
            myY=Parameters.teamAMainBot2InitY;
        }
        if (whoAmI == ALPHA){
            myX=Parameters.teamAMainBot3InitX;
            myY=Parameters.teamAMainBot3InitY;
        }

        //INIT
        state=TURNDEPART;
        previousstate = TURNDEPART;
        isMoving=false;
        isMovingBack=false;
        forwardOrder=false;
        countDown = 0;
    }
    public void step() {
        //ODOMETRY CODE
        if (isMoving){
            myX+=Parameters.teamAMainBotSpeed*Math.cos(myGetHeading());
            myY+=Parameters.teamAMainBotSpeed*Math.sin(myGetHeading());
            isMoving=false;
        }
        if (isMovingBack){
            myX-=Parameters.teamAMainBotSpeed*Math.cos(myGetHeading());
            myY-=Parameters.teamAMainBotSpeed*Math.sin(myGetHeading());
            isMovingBack=false;
        }
        //DEBUG MESSAGE
        boolean debug=true;
        if (debug && whoAmI == ALPHA && state!=SINK) {
            sendLogMessage("#ALPHA *thinks* (x,y)= ("+(int)myX+", "+(int)myY+") theta= "+(int)(myGetHeading()*180/(double)Math.PI)+"Â°. #State= "+state);
        }
        if (debug && whoAmI == BETA && state!=SINK) {
            sendLogMessage("#BETA *thinks* (x,y)= ("+(int)myX+", "+(int)myY+") theta= "+(int)(myGetHeading()*180/(double)Math.PI)+"Â°. #State= "+state);
        }
        if (debug && whoAmI == GAMMA && state!=SINK) {
            sendLogMessage("#GAMMA *thinks* (x,y)= ("+(int)myX+", "+(int)myY+") theta= "+(int)(myGetHeading()*180/(double)Math.PI)+"Â°. #State= "+state);
        }

        //COMMUNICATION
        ArrayList<String> messages=fetchAllMessages();
        for (String m: messages) if (Integer.parseInt(m.split(":")[1])==whoAmI || Integer.parseInt(m.split(":")[1])==TEAM) process(m);

        //RADAR DETECTION
        friendlyFire=true;
        for (IRadarResult o: detectRadar()){
            if (o.getObjectType()==IRadarResult.Types.OpponentMainBot || o.getObjectType()==IRadarResult.Types.OpponentSecondaryBot) {
                if (whoAmI == ALPHA || whoAmI == BETA || whoAmI == GAMMA) {
                    double enemyX=myX+o.getObjectDistance()*Math.cos(o.getObjectDirection());
                    double enemyY=myY+o.getObjectDistance()*Math.sin(o.getObjectDirection());
                    broadcast(whoAmI+":"+TEAM+":"+FIRE+":"+enemyX+":"+enemyY+":"+OVER);

                    if(state == MOVEEAST) {
                        state = FIREEAST;
                    }
                    if(state == MOVETOGETHER) {
                        state = FIRETOGETHER;
                    }
                    if(state == MOVERETURN) {
                        state = FIRERETURN;
                    }
                }
            }
            if (o.getObjectType()==IRadarResult.Types.TeamMainBot || o.getObjectType()==IRadarResult.Types.TeamSecondaryBot || o.getObjectType()==IRadarResult.Types.Wreck) {
                if (fireOrder && onTheWay(o.getObjectDirection())) {
                    friendlyFire=false;
                }
            }
        }

        //AUTOMATON
        if (fireOrder) countDown++;
        if (countDown>=100) fireOrder=false;
        if (fireOrder && fireRythm==0) {
            firePosition(targetX,targetY);
            fireRythm++;
            return;
        }
        fireRythm++;
        if (fireRythm>=Parameters.bulletFiringLatency) fireRythm=0;
        if (state==TURNDEPART && !(isSameDirection(myGetHeading(),NORTH))) {
            stepTurn(Parameters.Direction.LEFT);
            return;
        }
        if (state==TURNDEPART && isSameDirection(myGetHeading(),NORTH)) {
            state=MOVEDEPART;
            myMove();
            return;
        }
        if (state==MOVEDEPART && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL) {
            myMove();
            return;
        }
        if (state==MOVEDEPART && detectFront().getObjectType()==IFrontSensorResult.Types.WALL) {
            state=TURNEAST;
            myMoveBack();
            return;
        }
        if (state==TURNEAST && !(isSameDirection(myGetHeading(),EAST))) {
            stepTurn(Parameters.Direction.RIGHT);
            return;
        }
        if (state==TURNEAST && isSameDirection(myGetHeading(),EAST)) {
            state=MOVEEAST;
            myMove();
            return;
        }
        if (state==MOVEEAST && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL && myX < 2700) {
            myMove();
            return;
        }
        if (state==MOVEEAST && (detectFront().getObjectType()==IFrontSensorResult.Types.WALL || myX >= 2700)) {
            state=TURNSOUTH;
            return;
        }
        if (state==TURNSOUTH && !(isSameDirection(myGetHeading(),SOUTH))) {
            stepTurn(Parameters.Direction.RIGHT);
            return;
        }
        if (state==TURNSOUTH && isSameDirection(myGetHeading(),SOUTH)) {
            if (whoAmI != ALPHA)
            {
                state=WAITINGMATE;
            }
            else {
                broadcast(whoAmI+":"+TEAM+":"+FORWARD+":"+OVER);
                state = MOVETOGETHER;
            }
            return;
        }

        if (state==WAITINGMATE && !forwardOrder) {
            fire(SOUTH+0.14*gen.nextDouble());
            return;
        }
        if (state==WAITINGMATE && forwardOrder) {
            state=MOVETOGETHER;
            fire(SOUTH+0.14*gen.nextDouble());
            return;
        }
        if (state==MOVETOGETHER && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL && myY < 1500) {
            myMove();
            return;
        }
        if (state==MOVETOGETHER && (detectFront().getObjectType()==IFrontSensorResult.Types.WALL || myY >= 1500)) {
            state=TURNRETURN;
            return;
        }

        if (state==TURNRETURN && !(isSameDirection(myGetHeading(),NORTH_WEST))) {
            stepTurn(Parameters.Direction.RIGHT);
            return;
        }
        if (state==TURNRETURN && isSameDirection(myGetHeading(),NORTH_WEST)) {
            state=MOVERETURN;
            myMove();
            return;
        }
        if (state==MOVERETURN && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL) {
            myMove();
            return;
        }
        if (state==MOVERETURN && detectFront().getObjectType()==IFrontSensorResult.Types.WALL) {
            return;
        }

        if (fireOrder) {
            firePosition(targetX,targetY);
        }
        if (state==SWINGTASK){
            if (fireRythm==0) {
                firePosition(700,1500);
                fireRythm++;
                return;
            }
            fireRythm++;
            if (fireRythm==Parameters.bulletFiringLatency) fireRythm=0;
            if (rythm==0) stepTurn(Parameters.Direction.LEFT); else myMove();
            rythm++;
            if (rythm==14) rythm=0;
            return;
        }

        if (state==FIREEAST && (detectFront().getObjectType()==IFrontSensorResult.Types.OpponentMainBot || detectFront().getObjectType()==IFrontSensorResult.Types.OpponentSecondaryBot)) {
            return;
        }
        if (state==FIREEAST && detectFront().getObjectType()!=IFrontSensorResult.Types.OpponentMainBot && detectFront().getObjectType()!=IFrontSensorResult.Types.OpponentSecondaryBot) {
            state = MOVEEAST;
            return;
        }

        if (state==FIRETOGETHER && (detectFront().getObjectType()==IFrontSensorResult.Types.OpponentMainBot || detectFront().getObjectType()==IFrontSensorResult.Types.OpponentSecondaryBot)) {
            return;
        }
        if (state==FIRETOGETHER && detectFront().getObjectType()!=IFrontSensorResult.Types.OpponentMainBot && detectFront().getObjectType()!=IFrontSensorResult.Types.OpponentSecondaryBot) {
            state = MOVETOGETHER;
            return;
        }

        if (state==FIRERETURN && (detectFront().getObjectType()==IFrontSensorResult.Types.OpponentMainBot || detectFront().getObjectType()==IFrontSensorResult.Types.OpponentSecondaryBot)) {
            return;
        }
        if (state==FIRERETURN && detectFront().getObjectType()!=IFrontSensorResult.Types.OpponentMainBot && detectFront().getObjectType()!=IFrontSensorResult.Types.OpponentSecondaryBot) {
            state = MOVERETURN;
            return;
        }
        if (state==SINK) {
            myMove();
            return;
        }
        if (true) {
            return;
        }
    }
    private void myMove(){
        isMoving=true;
        move();
    }
    private void myMoveBack(){
        isMovingBack=true;
        moveBack();
    }
    private double myGetHeading(){
        double result = getHeading();
        while(result<0) result+=2*Math.PI;
        while(result>2*Math.PI) result-=2*Math.PI;
        return result;
    }
    private double normalizeRadian(double angle){
        double result = angle;
        while(result<0) result+=2*Math.PI;
        while(result>=2*Math.PI) result-=2*Math.PI;
        return result;
    }
    private double valeurEntreMoinsPiEtPi(double angle){
        double result = angle;
        while(result<-Math.PI) result+=2*Math.PI;
        while(result>=Math.PI) result-=2*Math.PI;
        return result;
    }
    private boolean isSameDirection(double dir1, double dir2){
        return Math.abs(valeurEntreMoinsPiEtPi(normalizeRadian(dir1)-normalizeRadian(dir2)))<ANGLEPRECISION;
    }
    private boolean isRoughlySameDirection(double dir1, double dir2){
        return Math.abs(normalizeRadian(dir1)-normalizeRadian(dir2))<FIREANGLEPRECISION;
    }
    private void process(String message){
        if (Integer.parseInt(message.split(":")[2])==FORWARD) forwardOrder=true;
        if (Integer.parseInt(message.split(":")[2])==FIRE) {
            fireOrder=true;
            countDown=0;
            targetX=Double.parseDouble(message.split(":")[3]);
            targetY=Double.parseDouble(message.split(":")[4]);
        }
    }
    private void firePosition(double x, double y){
        if (myX<=x) fire(Math.atan((y-myY)/(x-myX)));
        else fire(Math.PI+Math.atan((y-myY)/(x-myX)));
        return;
    }
    private boolean onTheWay(double angle){
        if (myX<=targetX) return isRoughlySameDirection(angle,Math.atan((targetY-myY)/(double)(targetX-myX)));
        else return isRoughlySameDirection(angle,Math.PI+Math.atan((targetY-myY)/(double)(targetX-myX)));
    }
}