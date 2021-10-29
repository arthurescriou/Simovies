/* ******************************************************
 * Simovies - Eurobot 2015 Robomovies Simulator.
 * Copyright (C) 2014 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: characteristics/Parameters.java 2014-10-19 buixuan.
 * ******************************************************/
package characteristics;

import java.util.HashMap;

class Team {
    public String aMain, bMain, aSecondary, bSecondary, name, avatarMain, avatarSecondary;
    private String packageName = "algorithms.";

    public Team(String name) {
        aMain = packageName + name + "AMain";
        bMain = packageName + name + "BMain";
        aSecondary = packageName + name + "ASecondary";
        bSecondary = packageName + name + "BSecondary";
        avatarMain = "avatars/" + name + "Main.png";
        avatarSecondary = "avatars/" + name + "Secondary.png";
        this.name = name;
    }

    public Team(String name, String packageName) {
        this.packageName = packageName;
        aMain = packageName + name + "AMain";
        bMain = packageName + name + "BMain";
        aSecondary = packageName + name + "ASecondary";
        bSecondary = packageName + name + "BSecondary";
        avatarMain = "avatars/" + name + "Main.png";
        avatarSecondary = "avatars/" + name + "Secondary.png";
        this.name = name;
    }
}

public class Parameters {
    //---------------------------//
    //---HARD-CODED-PARAMETERS---//
    //---------------------------//


    static String Aled = "Aled";
    static String TeamPositifs = "TeamPositifs";
    static String Superhero = "Superhero";

    private static HashMap<String, Team> initTeams() {
        Team mine = new Team("Hello");
        mine.bSecondary = "weUsedToLoveAsiats.WulaSecondary";
        mine.aSecondary = "weUsedToLoveAsiats.WulaSecondary";
        mine.bMain = "weUsedToLoveAsiats.WulaMain";
        mine.aMain = "weUsedToLoveAsiats.WulaMain";
        mine.avatarMain = "avatars/Bitcoin.png";
        mine.avatarSecondary = "avatars/ethereum.png";

        HashMap<String, Team> res = new HashMap<>();


        res.put(Aled, new Team(Aled));
        res.put(TeamPositifs, new Team(TeamPositifs));
        res.put(Superhero, new Team(Superhero));
        res.put("hello", mine);
        return res;
    }

    public static HashMap<String, Team> teams = initTeams();
    public static Team teamA = teams.get("hello");
    public static Team teamB = teams.get(Aled);

    public static enum Direction {LEFT, RIGHT, SOUTH, NORTH, EASTSOUTH, NORTHWEAST, NORTHWEASTSEC}

    ;
    public static final double EAST = 0, //clockwise trigonometric unit, according to screen pixel coordinate reference
            WEST = Math.PI,
            SOUTH = 0.5 * Math.PI,
            EASTSOUTH = 0.25 * Math.PI,
            NORTH = -0.5 * Math.PI,
            NORTH_WEST = 0.75 * Math.PI,
            NORTH_EAST = 0.25 * Math.PI,
            SOUTH_EAST = -0.25 * Math.PI,
            NORTHWEAST = -0.75 * Math.PI,
            NORTHWEASTSEC = -0.85 * Math.PI,
            RIGHTTURNFULLANGLE = 0.5 * Math.PI, //value set according to screen pixel coordinate reference
            LEFTTURNFULLANGLE = -0.5 * Math.PI;


    //-----------------------//
    //---TEAM-A-PARAMETERS---//
    //-----------------------//
    public static final String teamAName = teamA.name;
    public static final String teamAMainBotBrainClassName = teamA.aMain; //class given by name; is supposed to extends robotsimulator.Brain
    public static final String teamAMainBotAvatar = teamA.avatarMain; //path relative to location of ant build.xml file
    public static final double teamAMainBotRadius = 50, //1 unit = 1mm, body radius
            teamAMainBotFrontalDetectionRange = 300, //1 unit = 1mm, range of frontal sensor
            teamAMainBotFrontalDetectionAngle = 0, //UNUSED AT THE MOMENT, frontal sensor detection angle is suppoed to be absolute
            teamAMainBotSpeed = 1, //1 unit = 1mm, distance performed at step movement
            teamAMainBotStepTurnAngle = 0.01 * Math.PI, //trigonometric unit, angle performed at step turn action
            teamAMainBotHealth = 300, //FICTIONAL SIMOVIES
            teamAMainBot1InitX = 200, //1 unit = 1mm, coordinate of central point
            teamAMainBot1InitY = 800, //1 unit = 1mm, coordinate of central point
            teamAMainBot1InitHeading = EAST, //clockwise trigonometric unit, according to screen pixel coordinate reference
            teamAMainBot2InitX = 200, //1 unit = 1mm, coordinate of central point
            teamAMainBot2InitY = 1000, //1 unit = 1mm, coordinate of central point
            teamAMainBot2InitHeading = EAST, //clockwise trigonometric unit, according to screen pixel coordinate reference
            teamAMainBot3InitX = 200, //1 unit = 1mm, coordinate of central point
            teamAMainBot3InitY = 1200, //1 unit = 1mm, coordinate of central point
            teamAMainBot3InitHeading = EAST; //clockwise trigonometric unit, according to screen pixel coordinate reference

    public static final String teamASecondaryBotBrainClassName = teamA.aSecondary; //class given by name; is supposed to extends robotsimulator.Brain
    public static final String teamASecondaryBotAvatar = teamA.avatarSecondary; //path relative to location of ant build.xml file
    public static final double teamASecondaryBotRadius = 50, //1 unit = 1mm, body radius
            teamASecondaryBotFrontalDetectionRange = 500, //1 unit = 1mm, range of frontal sensor
            teamASecondaryBotFrontalDetectionAngle = 0, //UNUSED AT THE MOMENT, frontal sensor detection angle is suppoed to be absolute
            teamASecondaryBotSpeed = 3, //1 unit = 1mm, distance performed at step movement
            teamASecondaryBotStepTurnAngle = 0.01 * Math.PI, //trigonometric unit, angle performed at step turn action
            teamASecondaryBotHealth = 100, //FICTIONAL SIMOVIES
            teamASecondaryBot1InitX = 500, //1 unit = 1mm, coordinate of central point
            teamASecondaryBot1InitY = 800, //1 unit = 1mm, coordinate of central point
            teamASecondaryBot1InitHeading = EAST, //clockwise trigonometric unit, according to screen pixel coordinate reference
            teamASecondaryBot2InitX = 500, //1 unit = 1mm, coordinate of central point
            teamASecondaryBot2InitY = 1200, //1 unit = 1mm, coordinate of central point
            teamASecondaryBot2InitHeading = EAST; //clockwise trigonometric unit, according to screen pixel coordinate reference

    //-----------------------//
    //---TEAM-B-PARAMETERS---//
    //-----------------------//
    public static final String teamBName = teamB.name;
    public static final String teamBMainBotBrainClassName = "algorithms.MainRobot"; //class given by name; is supposed to extends robotsimulator.Brain
    public static final String teamBMainBotAvatar = teamB.avatarMain; //path relative to location of ant build.xml file
    public static final double teamBMainBotRadius = 50, //1 unit = 1mm, body radius
            teamBMainBotFrontalDetectionRange = 300, //1 unit = 1mm, range of frontal sensor
            teamBMainBotFrontalDetectionAngle = 0, //UNUSED AT THE MOMENT, frontal sensor detection angle is suppoed to be absolute
            teamBMainBotSpeed = 1, //1 unit = 1mm, distance performed at step movement
            teamBMainBotStepTurnAngle = 0.01 * Math.PI, //trigonometric unit, angle performed at step turn action
            teamBMainBotHealth = 300, //FICTIONAL SIMOVIES
            teamBMainBot1InitX = 2800, //1 unit = 1mm, coordinate of central point
            teamBMainBot1InitY = 800, //1 unit = 1mm, coordinate of central point
            teamBMainBot1InitHeading = WEST, //clockwise trigonometric unit, according to screen pixel coordinate reference
            teamBMainBot2InitX = 2800, //1 unit = 1mm, coordinate of central point
            teamBMainBot2InitY = 1000, //1 unit = 1mm, coordinate of central point
            teamBMainBot2InitHeading = WEST, //clockwise trigonometric unit, according to screen pixel coordinate reference
            teamBMainBot3InitX = 2800, //1 unit = 1mm, coordinate of central point
            teamBMainBot3InitY = 1200, //1 unit = 1mm, coordinate of central point
            teamBMainBot3InitHeading = WEST; //clockwise trigonometric unit, according to screen pixel coordinate reference

    public static final String teamBSecondaryBotBrainClassName = "algorithms.SecondaryRobot"; //class given by name; is supposed to extends robotsimulator.Brain
    public static final String teamBSecondaryBotAvatar = teamB.avatarSecondary; //path relative to location of ant build.xml file
    public static final double teamBSecondaryBotRadius = 50, //1 unit = 1mm, body radius
            teamBSecondaryBotFrontalDetectionRange = 500, //1 unit = 1mm, range of frontal sensor
            teamBSecondaryBotFrontalDetectionAngle = 0, //UNUSED AT THE MOMENT, frontal sensor detection angle is suppoed to be absolute
            teamBSecondaryBotSpeed = 3, //1 unit = 1mm, distance performed at step movement
            teamBSecondaryBotStepTurnAngle = 0.01 * Math.PI, //trigonometric unit, angle performed at step turn action
            teamBSecondaryBotHealth = 100, //FICTIONAL SIMOVIES
            teamBSecondaryBot1InitX = 2500, //1 unit = 1mm, coordinate of central point
            teamBSecondaryBot1InitY = 800, //1 unit = 1mm, coordinate of central point
            teamBSecondaryBot1InitHeading = WEST, //clockwise trigonometric unit, according to screen pixel coordinate reference
            teamBSecondaryBot2InitX = 2500, //1 unit = 1mm, coordinate of central point
            teamBSecondaryBot2InitY = 1200, //1 unit = 1mm, coordinate of central point
            teamBSecondaryBot2InitHeading = WEST; //clockwise trigonometric unit, according to screen pixel coordinate reference

    //-----------------------//
    //---BULLET-PARAMETERS---//
    //-----------------------//
    public static final double bulletVelocity = 10,
            bulletDamage = 10,
            bulletRadius = 5,
            bulletRange = 1000;
    public static final int bulletFiringLatency = 20;

    public static final double getRadius(boolean main, boolean teamGauche) {
        if (main) {
            if (teamGauche)
                return teamAMainBotRadius;
            else
                return teamBMainBotRadius;
        } else {
            if (teamGauche)
                return teamASecondaryBotRadius;
            else
                return teamBSecondaryBotRadius;
        }
    }
}
