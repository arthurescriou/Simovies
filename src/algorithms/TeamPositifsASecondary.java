package algorithms;

import java.util.ArrayList;

import characteristics.IFrontSensorResult;
import characteristics.IRadarResult;
import characteristics.Parameters;
import robotsimulator.Brain;

public class TeamPositifsASecondary  extends Brain {
    //---PARAMETERS---//
    private static final double HEADINGPRECISION = 0.001;
    private static final double ANGLEPRECISION = 0.1;

    private static final int SECONDARYA2 = 0x1EADDA;
    private static final int BETA = 0x5EC0;
    private static final int SECONDARYA1 = 0x333;
    private static final int TEAM = 0xBADDAD;
    private static final int UNDEFINED = 0xBADC0DE0;

    private static final int FALLBACK = 0xFA11BAC;
    private static final int ROGER = 0x0C0C0C0C;
    private static final int OVER = 0xC00010FF;

    private static final int TURNDEPART = 1;
    private static final int MOVEDEPART = 2;
    private static final int TURNRIGHT = 3;
    private static final int MOVERIGHT = 4;
    private static final int TURNSE = 6;
    private static final int MOVESE = 7;
    private static final int TURNETERNAL = 5;


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

    private static final int MOVEBACKSOUTHTASK = 21;
    private static final int MOVESOUTHBISTASK = 22;
    private static final int MOVEBACKSOUTHBISTASK = 23;
    private static final int MOVESOUTHTERTASK = 24;
    private static final int PAUSETASK = 25;
    private static final int TURNSOUTHBISTASK = 51;
    private static final int SWINGTASK = 6;
    private static final int FREEZE = -1;
    private static final int SINK = 0xBADC0DE1;
    private static final int TURNEAST = 10;
    private static final int MOVEEAST = 11;
    private static final int TURNRETURNA = 12;
    private static final int MOVERETURNB = 13;
    private static final int TURNRETURNB = 14;
    private static final int TURNRETURNAL = 15;
    private static final double FIREANGLEPRECISION = Math.PI/(double)6;
    private static final int MOVETASK = 50;
    private static final int TURNLEFTTASK = 51;
    private static final int MOVERETURNA = 16;
    private static final int TURNNORTHR = 80;
    private static final int MOVENORTH = 81;

    //---VARIABLES---//
    private int state;
    private double oldAngle;
    private double myX,myY,oldY,oldX;
    private boolean isMoving,isMovingBack;
    private int whoAmI;
    private int fireRythm,rythm,counter;
    private boolean fallbackOrder;
    private boolean freeze;
    private boolean ISFIRED = false;

    //---CONSTRUCTORS---//
    public TeamPositifsASecondary() { super(); }

    //---ABSTRACT-METHODS-IMPLEMENTATION---//
    public void activate() {
        //ODOMETRY CODE
        whoAmI = SECONDARYA1;
        for (IRadarResult o: detectRadar())
            if (isSameDirection(o.getObjectDirection(),NORTH)) whoAmI=SECONDARYA2;

        if (whoAmI == SECONDARYA1){
            myX=Parameters.teamAMainBot1InitX;
            myY=Parameters.teamAMainBot1InitY;
        } else {
            myX=Parameters.teamAMainBot2InitX;
            myY=Parameters.teamAMainBot2InitY;
        }


        //INIT
        state=TURNDEPART;
        oldAngle=myGetHeading();
        isMoving=false;
        isMovingBack=false;
        fallbackOrder=false;
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
        if (debug && whoAmI == SECONDARYA2 && state!=SINK) {
            sendLogMessage("#ALPHA *thinks* (x,y)= ("+(int)myX+", "+(int)myY+") theta= "+(int)(myGetHeading()*180/(double)Math.PI)+"Â°. #State= "+state);
        }
        if (debug && whoAmI == SECONDARYA1 && state!=SINK) {
            sendLogMessage("#GAMMA *thinks* (x,y)= ("+(int)myX+", "+(int)myY+") theta= "+(int)(myGetHeading()*180/(double)Math.PI)+"Â°. #State= "+state);
        }

        //COMMUNICATION
        ArrayList<String> messages=fetchAllMessages();
        for (String m: messages) if (Integer.parseInt(m.split(":")[1])==whoAmI || Integer.parseInt(m.split(":")[1])==TEAM) process(m);

        //RADAR DETECTION
        freeze=false;
        for (IRadarResult o: detectRadar()){
            if(o.getObjectType()==IRadarResult.Types.BULLET){
                stepTurn(Parameters.Direction.RIGHT);
                ISFIRED = true;
                return;
            }
            if (ISFIRED) {
                state=TURNRETURNA;
                myMove();
                ISFIRED = false;
                return;
            }
            if (o.getObjectDistance()<=100 && !isRoughlySameDirection(o.getObjectDirection(),getHeading()) && o.getObjectType()!=IRadarResult.Types.BULLET) {
                freeze=true;
            }
        }
        if (freeze) return;


        //AUTOMATON
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
            oldX = myX;
            myMove();
            return;
        }
        if (state==MOVEEAST && myX < oldX + 100) {
            myMove();
            return;
        }
        if (state==MOVEEAST && myX >= oldX + 100) {
            state=TURNSE;
            return;
        }

        if (state==TURNSE && !(isSameDirection(myGetHeading(),SOUTH_EAST))) {
            stepTurn(Parameters.Direction.RIGHT);
            return;
        }
        if (state==TURNSE && isSameDirection(myGetHeading(),SOUTH_EAST)) {
            state=MOVESE;
            myMove();
            return;
        }
        if (state==MOVESE && (myX <= 2700 || myX >= 2800) && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL) {
            myMove();
            return;
        }
        if (state==MOVESE && (2700 <= myX && myX <= 2800 ||detectFront().getObjectType()==IFrontSensorResult.Types.WALL)) {
            state=TURNRETURNA;
            return;
        }

        if (state==TURNRETURNA && !(isSameDirection(myGetHeading(),NORTH_EAST))) {
            stepTurn(Parameters.Direction.LEFT);
            return;
        }
        if (state==TURNRETURNA && isSameDirection(myGetHeading(),NORTH_EAST)) {
            state=MOVERETURNA;
            oldX = myX;
            myMove();
            return;
        }
        if (state==MOVERETURNA &&  myX < oldX + 150) {
            myMove();
            return;
        }
        if (state==MOVERETURNA &&  myX >= oldX + 150) {
            state=TURNRETURNB;
            return;
        }

        if (state==TURNRETURNB && !(isSameDirection(myGetHeading(),NORTH_WEST - 0.3))) {
            stepTurn(Parameters.Direction.LEFT);
            return;
        }
        if (state==TURNRETURNB && isSameDirection(myGetHeading(),NORTH_WEST - 0.3)) {
            state=MOVERETURNB;
            myMove();
            return;
        }
        if (state==MOVERETURNB && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL) {
            myMove();
            return;
        }
        if (state==MOVERETURNB && detectFront().getObjectType()==IFrontSensorResult.Types.WALL) {
            state=TURNNORTHR;
            return;
        }
        if (state==TURNNORTHR && !(isSameDirection(myGetHeading(),NORTH))) {
            stepTurn(Parameters.Direction.RIGHT);
            return;
        }
        if (state==TURNNORTHR && isSameDirection(myGetHeading(),NORTH)) {
            state=MOVENORTH;
            oldY=myY;
            myMove();
            return;
        }
        if (state==MOVENORTH && myY > oldY - 150) {
            myMove();
            return;
        }
        if (state==MOVENORTH && myY <= oldY - 150) {
            state=TURNEAST;
            return;
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
        if (state==MOVETASK && detectFront().getObjectType()!=IFrontSensorResult.Types.WALL) {
            myMove();
            return;
        }
        if (state==MOVETASK && detectFront().getObjectType()==IFrontSensorResult.Types.WALL) {
            state=TURNLEFTTASK;
            oldAngle=myGetHeading();
            stepTurn(Parameters.Direction.LEFT);
            return;
        }
        if (state==TURNLEFTTASK && !(isSameDirection(getHeading(),oldAngle+LEFTTURNFULLANGLE))) {
            stepTurn(Parameters.Direction.LEFT);
            return;
        }
        if (state==TURNLEFTTASK && isSameDirection(getHeading(),oldAngle+LEFTTURNFULLANGLE)) {
            state=MOVETASK;
            myMove();
            return;
        }
        if (state==FREEZE) {
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
    private boolean isSameDirection(double dir1, double dir2){
        return Math.abs(dir1-dir2)<ANGLEPRECISION;
    }
    private boolean isRoughlySameDirection(double dir1, double dir2){
        return Math.abs(normalizeRadian(dir1)-normalizeRadian(dir2))<FIREANGLEPRECISION;
    }
    private void process(String message){
        if (Integer.parseInt(message.split(":")[2])==FALLBACK) fallbackOrder=true;
    }
    private void firePosition(int x, int y){
        if (myX<=x) fire(Math.atan((y-myY)/(double)(x-myX)));
        else fire(Math.PI+Math.atan((y-myY)/(double)(x-myX)));
        return;
    }
}