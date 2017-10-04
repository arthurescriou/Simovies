package robotsimulator;

import java.util.ArrayList;

import characteristics.IRadarResult;

public class Bot {

    private double radius;
    private double frontRange;
    private double speed;
    private double stepTurnAngle;
    private double x;
    private double y;
    private double angle;
    private double maxHealth;
    private double health;
    private Brain brain;
    private SimulatorEngine engine;
    private int me;
    private boolean rocket;
    private ArrayList<String> mailbox;

    public Bot(double radius, double range, double speed, double stepTurnAngle, double x, double y, double angle,
                    double health, boolean rocket, Brain brain, int me) {
        this.radius = radius;
        frontRange = range;
        this.speed = speed;
        this.stepTurnAngle = stepTurnAngle;
        this.x = x;
        this.y = y;
        this.angle = angle;
        maxHealth = health;
        this.health = health;
        this.brain = brain;
        this.me = me;
        this.rocket = rocket;
        mailbox = new ArrayList();
        brain.bind(this);
    }

    protected void bind(SimulatorEngine engine) {
        this.engine = engine;
    }

    public void activate() {
        brain.activation();
    }

    public void step() { brain.stepAction(); }

    public double getX() {
        return x;
    }

    public double getY() { return y; }

    public double getRadius() {
        return radius;
    }

    public double getHeading() { return angle; }

    public double getHealth() {
        return health;
    }

    public double getMaxHealth() { return maxHealth; }

    public int getTeam() {
        return me;
    }

    public String getLogMessage() { return brain.getLogMessage(); }

    public void takeDamage(double damage) {
        health = Math.max(health - damage, 0.0D);
    }

    public boolean isDestroyed() { return health <= 0.0D; }

    protected void move() {
        double newX = x + speed * Math.cos(angle);
        double newY = y + speed * Math.sin(angle);
        if ((newX >= radius) && (newX <= engine.getWorld().getWidth() - radius) && (newY >= radius) && (newY <= engine
                        .getWorld().getHeight() - radius)) {
            boolean doIt = true;
            for (Bot bot : engine.getBots()) {
                if (!bot.equals(this))
                    if ((newX - bot.getX()) * (newX - bot.getX()) + (newY - bot.getY()) * (newY - bot
                                    .getY()) < (getRadius() + bot.getRadius()) * (getRadius() + bot.getRadius()))
                        doIt = false;
            }
            if (doIt) {
                x = newX;
                y = newY;
            }
        }
    }

    protected void moveBack() {
        double newX = x - speed * Math.cos(angle);
        double newY = y - speed * Math.sin(angle);
        if ((newX >= radius) && (newX <= engine.getWorld().getWidth() - radius) && (newY >= radius) && (newY <= engine
                        .getWorld().getHeight() - radius)) {
            boolean doIt = true;
            for (Bot bot : engine.getBots()) {
                if (!bot.equals(this))
                    if ((newX - bot.getX()) * (newX - bot.getX()) + (newY - bot.getY()) * (newY - bot
                                    .getY()) < (getRadius() + bot.getRadius()) * (getRadius() + bot.getRadius()))
                        doIt = false;
            }
            if (doIt) {
                x = newX;
                y = newY;
            }
        }
    }

    protected void fire(double dir) { engine.addBullet(this, dir); }

    protected void stepTurnLeft() {
        angle -= stepTurnAngle;
    }

    protected void stepTurnRight() { angle += stepTurnAngle; }

    protected void broadcast(String message) { engine.broadcast(message, me); }

    protected ArrayList<String> fetchAllMessages() {
        ArrayList<String> messages = (ArrayList) mailbox.clone();
        mailbox.clear();
        return messages;
    }

    protected void addMessage(String message) { mailbox.add(message); }

    protected boolean hasRocket() { return rocket; }

    protected FrontSensorResult detectFront() {
        double frontRangeX = x + frontRange * Math.cos(angle);
        double frontRangeY = y + frontRange * Math.sin(angle);
        return engine.detect(x, y, frontRangeX, frontRangeY, me);
    }

    protected ArrayList<IRadarResult> detectRadar() { return engine.detectRadar(frontRange, this); }
}
