package robotsimulator;

import java.util.ArrayList;
import java.util.Iterator;

import characteristics.IRadarResult;

public class SimulatorEngine {

    private supportGUI.DisplayGame gameGUI;
    private supportGUI.LogMessage logGUI;
    private playground.PlayingArea world;
    private Bot aMain1;
    private Bot aMain2;
    private Bot aMain3;
    private Bot aSecondary1;
    private Bot aSecondary2;
    private Bot bMain1;
    private Bot bMain2;
    private Bot bMain3;
    private Bot bSecondary1;
    private Bot bSecondary2;
    private characteristics.IBrain aMainBrain1;
    private characteristics.IBrain aMainBrain2;
    private characteristics.IBrain aMainBrain3;
    private characteristics.IBrain aSecondaryBrain1;
    private characteristics.IBrain aSecondaryBrain2;
    private characteristics.IBrain bMainBrain1;
    private characteristics.IBrain bMainBrain2;
    private characteristics.IBrain bMainBrain3;
    private characteristics.IBrain bSecondaryBrain1;
    private characteristics.IBrain bSecondaryBrain2;
    private ArrayList<Bot> bots;
    private ArrayList<Bullet> bullets;
    private ArrayList<Bullet> explosions;
    private javax.swing.Timer gameClock;
    private boolean started;
    private boolean teamB;

    public SimulatorEngine(supportGUI.DisplayGame game, supportGUI.LogMessage log) {
        gameGUI = game;
        logGUI = log;
        init();
        teamB = true;
    }

    private void init() {
        supportGUI.FileLoader loader = new supportGUI.FileLoader();
        world = new playground.PlayingArea();
        bullets = new ArrayList();
        explosions = new ArrayList();
        aMainBrain1 = loader.getTeamAMainBotBrain();
        aMainBrain2 = loader.getTeamAMainBotBrain();
        aMainBrain3 = loader.getTeamAMainBotBrain();
        aSecondaryBrain1 = loader.getTeamASecondaryBotBrain();
        aSecondaryBrain2 = loader.getTeamASecondaryBotBrain();
        bMainBrain1 = loader.getTeamBMainBotBrain();
        bMainBrain2 = loader.getTeamBMainBotBrain();
        bMainBrain3 = loader.getTeamBMainBotBrain();
        bSecondaryBrain1 = loader.getTeamBSecondaryBotBrain();
        bSecondaryBrain2 = loader.getTeamBSecondaryBotBrain();
        bots = new ArrayList();
        aMain1 = new Bot(50.0D, 300.0D, loader
                        .getTeamAMainBotSpeed(), 0.031415926535897934D, 200.0D, 800.0D, 0.0D, 300.0D, true, (Brain)
                        aMainBrain1, 5367678);

        aMain2 = new Bot(50.0D, 300.0D, loader
                        .getTeamAMainBotSpeed(), 0.031415926535897934D, 200.0D, 1000.0D, 0.0D, 300.0D, true, (Brain)
                        aMainBrain2, 5367678);

        aMain3 = new Bot(50.0D, 300.0D, loader
                        .getTeamAMainBotSpeed(), 0.031415926535897934D, 200.0D, 1200.0D, 0.0D, 300.0D, true, (Brain)
                        aMainBrain3, 5367678);

        bMain1 = new Bot(50.0D, 300.0D, loader
                        .getTeamBMainBotSpeed(), 0.031415926535897934D, 2800.0D, 800.0D, 3.141592653589793D, 300.0D,
                        true, (Brain) bMainBrain1, 15539326);

        bMain2 = new Bot(50.0D, 300.0D, loader
                        .getTeamBMainBotSpeed(), 0.031415926535897934D, 2800.0D, 1000.0D, 3.141592653589793D, 300.0D,
                        true, (Brain) bMainBrain2, 15539326);

        bMain3 = new Bot(50.0D, 300.0D, loader
                        .getTeamBMainBotSpeed(), 0.031415926535897934D, 2800.0D, 1200.0D, 3.141592653589793D, 300.0D,
                        true, (Brain) bMainBrain3, 15539326);

        aSecondary1 = new Bot(50.0D, 500.0D, loader
                        .getTeamASecondaryBotSpeed(), 0.031415926535897934D, 500.0D, 800.0D, 0.0D, 100.0D, false,
                        (Brain) aSecondaryBrain1, 5367678);

        aSecondary2 = new Bot(50.0D, 500.0D, loader
                        .getTeamASecondaryBotSpeed(), 0.031415926535897934D, 500.0D, 1200.0D, 0.0D, 100.0D, false,
                        (Brain) aSecondaryBrain2, 5367678);

        bSecondary1 = new Bot(50.0D, 500.0D, loader
                        .getTeamBSecondaryBotSpeed(), 0.031415926535897934D, 2500.0D, 800.0D, 3.141592653589793D,
                        100.0D, false, (Brain) bSecondaryBrain1, 15539326);

        bSecondary2 = new Bot(50.0D, 500.0D, loader
                        .getTeamBSecondaryBotSpeed(), 0.031415926535897934D, 2500.0D, 1200.0D, 3.141592653589793D,
                        100.0D, false, (Brain) bSecondaryBrain2, 15539326);

        bots.add(aMain1);
        bots.add(aMain2);
        bots.add(aMain3);
        bots.add(bMain1);
        bots.add(bMain2);
        bots.add(bMain3);
        bots.add(aSecondary1);
        bots.add(aSecondary2);
        bots.add(bSecondary1);
        bots.add(bSecondary2);
        for (Bot bot : bots) {
            bot.bind(this);
        }
        gameGUI.repaint();
        logGUI.repaint();

        gameClock = new javax.swing.Timer(10, new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                for (Bot bot : bots) {
                    bot.step();
                }
                gameGUI.repaint();
                logGUI.repaint();
            }

        });
        started = false;
    }

    public int getWorldWidth() {
        return world.getWidth();
    }

    public int getWorldHeight() { return world.getHeight(); }

    public playground.PlayingArea getWorld() {
        return world;
    }

    public ArrayList<Bot> getBots() { return bots; }

    public double getTeamAMainBotRadius() {
        return aMain1.getRadius();
    }

    public double getTeamBMainBotRadius() { return bMain1.getRadius(); }

    public String getTeamAMainBotMessage() {
        return aMain1.getLogMessage();
    }

    public String getTeamBMainBotMessage() { return bMain1.getLogMessage(); }

    private double crossProduct(Coordinates p, Coordinates q) {
        return p.x * q.y - p.y * q.x;
    }

    private double dotProduct(Coordinates p, Coordinates q) { return p.x * q.x + p.y * q.y; }

    private boolean segmentCollision(Coordinates p, Coordinates r, Coordinates q, Coordinates s) {
        double rxs = crossProduct(r, s);
        Coordinates pq = new Coordinates(q.x - p.x, q.y - p.y);
        double trxs = crossProduct(pq, s);
        double urxs = crossProduct(pq, r);
        if (rxs != 0.0D) {
            double t = trxs / rxs;
            double u = urxs / rxs;
            return (0.0D <= t) && (t <= 1.0D) && (0.0D <= u) && (u <= 1.0D);
        }
        if ((Math.abs(rxs) < 0.01D) && (Math.abs(urxs) < 0.01D)) {

            double pqr = dotProduct(pq, r);
            double qps = dotProduct(new Coordinates(p.x - q.x, p.y - q.y), s);
            return ((0.0D <= pqr) && (pqr <= dotProduct(r, r))) || ((0.0D <= qps) && (qps <= dotProduct(s, s)));
        }
        return false;
    }

    public ArrayList<Bullet> getCurrentBullets() {
        for (int i = 0; i < bullets.size(); i++) {
            if (((Bullet) bullets.get(i)).isDestroyed()) {
                bullets.remove(i);
                i--;
            }
        }

        int i = 0;

        while (i < bullets.size()) {
            int j = i + 1;
            while (j < bullets.size()) {
                if (segmentCollision(new Coordinates(((Bullet) bullets.get(i)).getX(), ((Bullet) bullets.get(i))
                                .getY()), new Coordinates(((Bullet) bullets.get(i)).getVelocity() * Math
                                .cos(((Bullet) bullets.get(i)).getHeading()), ((Bullet) bullets.get(i))
                                .getVelocity() * Math
                                .sin(((Bullet) bullets.get(i)).getHeading())), new Coordinates(((Bullet) bullets.get(j))
                                .getX(), ((Bullet) bullets.get(j)).getY()), new Coordinates(((Bullet) bullets.get(j))
                                .getVelocity() * Math.cos(((Bullet) bullets.get(j)).getHeading()), ((Bullet) bullets
                                .get(j)).getVelocity() * Math.sin(((Bullet) bullets.get(j)).getHeading())))) {

                    bullets.remove(j);
                    bullets.remove(i);
                    i = 0;
                    j = 0;
                }
                j++;
            }
            i++;
        }

        explosions = new ArrayList();
        for (int k = 0; k < bullets.size(); k++) {
            ((Bullet) bullets.get(k)).step();
            for (Bot bot : bots) {
                if ((((Bullet) bullets.get(k)).getX() - bot.getX()) * (((Bullet) bullets.get(k)).getX() - bot
                                .getX()) + (((Bullet) bullets.get(k)).getY() - bot.getY()) * (((Bullet) bullets.get(k))
                                .getY() - bot.getY()) < (((Bullet) bullets.get(k)).getRadius() + bot
                                .getRadius()) * (((Bullet) bullets.get(k)).getRadius() + bot.getRadius())) {

                    bot.takeDamage(10.0D);
                    explosions.add(new Bullet(bot.getX() + 1.1D * (bot.getRadius() + 5.0D) * Math
                                    .cos(3.141592653589793D + ((Bullet) bullets.get(k)).getHeading()), bot
                                    .getY() + 1.1D * (bot.getRadius() + 5.0D) * Math
                                    .sin(3.141592653589793D + ((Bullet) bullets.get(k))
                                                    .getHeading()), 0.0D, 0.0D, 0.0D, 5.0D, 0.0D));

                    bullets.remove(k);
                    k--;
                    break;
                }
            }
        }

        return bullets;
    }

    public ArrayList<Bullet> getCurrentExplosions() { return explosions; }

    protected void addBullet(Bot bot, double dir) {
        bullets.add(new Bullet(bot.getX() + 1.01D * (bot.getRadius() + 5.0D) * Math.cos(dir), bot.getY() + 1.01D * (bot
                        .getRadius() + 5.0D) * Math.sin(dir), dir, 10.0D, 10.0D, 5.0D, 1000.0D));
    }

    private boolean collision(Coordinates p, Coordinates q, Bot bot) {
        double d = p.distance(q);
        Coordinates unitPQ = new Coordinates((q.x - p.x) / d, (q.y - p.y) / d);
        double projection = this.dotProduct(new Coordinates(bot.getX() - p.x, bot.getY() - p.y), unitPQ);
        if(projection < 0.0D) {
            return (bot.getX() - p.x) * (bot.getX() - p.x) + (bot.getY() - p.y) * (bot.getY() - p.y) < bot.getRadius() * bot.getRadius();
        } else if(projection > d) {
            return (bot.getX() - q.x) * (bot.getX() - q.x) + (bot.getY() - q.y) * (bot.getY() - q.y) < bot.getRadius() * bot.getRadius();
        } else {
            Coordinates c = new Coordinates(p.x + projection * unitPQ.x, p.y + projection * unitPQ.y);
            return (bot.getX() - c.x) * (bot.getX() - c.x) + (bot.getY() - c.y) * (bot.getY() - c.y) < bot.getRadius() * bot.getRadius();
        }
    }

    protected FrontSensorResult detect(double x, double y, double s, double t, int me) {
        for (Bot bot : bots) {
            if ((x != bot.getX()) || (y != bot.getY())) {
                if (collision(new Coordinates(x, y), new Coordinates(s, t), bot)) {
                    if (bot.getHealth() == 0.0D)
                        return new FrontSensorResult(characteristics.IFrontSensorResult.Types.Wreck);
                    if (me == bot.getTeam()) {
                        if (bot.hasRocket()) {
                            return new FrontSensorResult(characteristics.IFrontSensorResult.Types.TeamMainBot);
                        }
                        return new FrontSensorResult(characteristics.IFrontSensorResult.Types.TeamSecondaryBot);
                    }

                    if (bot.hasRocket()) {
                        return new FrontSensorResult(characteristics.IFrontSensorResult.Types.OpponentMainBot);
                    }
                    return new FrontSensorResult(characteristics.IFrontSensorResult.Types.OpponentSecondaryBot);
                }
            }
        }

        if ((s < 0.0D) || (s > world.getWidth()) || (t < 0.0D) || (t > world.getHeight()))
            return new FrontSensorResult(characteristics.IFrontSensorResult.Types.WALL);
        return new FrontSensorResult(characteristics.IFrontSensorResult.Types.NOTHING);
    }

    protected ArrayList<characteristics.IRadarResult> detectRadar(double range, Bot bot) {
        ArrayList<IRadarResult> result = new ArrayList();
        Coordinates me = new Coordinates(bot.getX(), bot.getY());
        for (Bot b : bots)
            if ((b.getX() != bot.getX()) || (b.getY() != bot.getY())) {
                Coordinates it = new Coordinates(b.getX(), b.getY());
                double d = it.distance(me);
                Coordinates meit = new Coordinates(it.x - me.x, it.y - me.y);
                double dir;
                if (crossProduct(new Coordinates(1.0D, 0.0D), new Coordinates(0.0D, -1.0D)) * crossProduct(new
                                Coordinates(1.0D, 0.0D), meit) > 0.0D) {
                    dir = -Math.acos(dotProduct(meit, new Coordinates(1.0D, 0.0D)) / d);
                } else {
                    dir = Math.acos(dotProduct(meit, new Coordinates(1.0D, 0.0D)) / d);
                }
                if (d < b.getRadius() + range) {
                    characteristics.IRadarResult.Types type;
                    if (b.isDestroyed()) {
                        type = characteristics.IRadarResult.Types.Wreck;
                    } else {
                        if (b.getTeam() == bot.getTeam()) {
                            if (b.hasRocket())
                                type = characteristics.IRadarResult.Types.TeamMainBot;
                            else
                                type = characteristics.IRadarResult.Types.TeamSecondaryBot;
                        } else {
                            if (b.hasRocket())
                                type = characteristics.IRadarResult.Types.OpponentMainBot;
                            else
                                type = characteristics.IRadarResult.Types.OpponentSecondaryBot;
                        }
                    }
                    result.add(new RadarResult(type, dir, d, b.getRadius()));
                }
            }
        for (Bullet b : bullets) {
            Coordinates it = new Coordinates(b.getX(), b.getY());
            double d = it.distance(me);
            Coordinates meit = new Coordinates(it.x - me.x, it.y - me.y);
            double dir;
            if (crossProduct(new Coordinates(1.0D, 0.0D), new Coordinates(0.0D, -1.0D)) * crossProduct(new Coordinates(1.0D, 0.0D), meit) > 0.0D) {
                dir = -Math.acos(dotProduct(meit, new Coordinates(1.0D, 0.0D)) / d);
            } else {
                dir = Math.acos(dotProduct(meit, new Coordinates(1.0D, 0.0D)) / d);
            }
            if (d < b.getRadius() + range) {
                result.add(new RadarResult(characteristics.IRadarResult.Types.BULLET, dir, d, b.getRadius()));
            }
        }
        return result;
    }

    protected void broadcast(String message, int sender) {
        for (Bot b : bots) {
            if (b.getTeam() == sender) {
                b.addMessage(message);
            }
        }
    }

    public void pauseSimulation() {
        gameClock.stop();
    }

    public void resumeSimulation() {
        gameClock.start();
    }

    public void startSimulation() {
        if (started) {
            resumeSimulation();
        } else {
            for (Bot bot : bots) {
                bot.activate();
            }
            gameGUI.repaint();
            logGUI.repaint();
            gameClock.start();
            started = true;
        }
    }

    public void reloadSimulation() {
        gameClock.stop();
        init();
    }

    public void removeTeamB() {
        teamB = false;
    }
}
