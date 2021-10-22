package supportGUI;

import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

import robotsimulator.*;
import weUsedToLoveAsiats.DetectBrainAffichage;
import weUsedToLoveAsiats.MasterMind;
import weUsedToLoveAsiats.tools.CartCoordinate;
import weUsedToLoveAsiats.tools.trucsDeBinhNul.Position;

public class DisplayGame extends javax.swing.JPanel {

    private static final int xStep = 5;
    private static final int yStep = 5;
    private static final int xZoomStep = 5;
    private static final int yZoomStep = 5;
    private static final double alphaZoomStep = 0.98D;
    private int xModifier;
    private int yModifier;
    private double zoomFactor;
    private double teamAMainBotXScale;
    private double teamAMainBotYScale;
    private double teamBMainBotXScale;
    private double teamBMainBotYScale;
    private double teamASecondaryBotXScale;
    private double teamASecondaryBotYScale;
    private double teamBSecondaryBotXScale;
    private double teamBSecondaryBotYScale;
    private AffineTransform teamAMainBotAffineTransform;
    private AffineTransform teamBMainBotAffineTransform;
    private AffineTransform teamASecondaryBotAffineTransform;
    private AffineTransform teamBSecondaryBotAffineTransform;
    private AffineTransform af;
    private Graphics2D g2d;
    private SimulatorEngine engine;
    private BufferedImage teamAMainBotRaw;
    private BufferedImage teamBMainBotRaw;
    private BufferedImage teamASecondaryBotRaw;
    private BufferedImage teamBSecondaryBotRaw;
    private BufferedImage teamAMainBot;
    private BufferedImage teamBMainBot;
    private BufferedImage teamASecondaryBot;
    private BufferedImage teamBSecondaryBot;
    private ArrayList<Line> lines;
    private ArrayList<Circle> circles;
    private Line healthBar;

    protected DisplayGame() {
        addComponentListener(new java.awt.event.ComponentListener() {
            public void componentHidden(ComponentEvent e) {}

            public void componentMoved(ComponentEvent e) {}

            public void componentShown(ComponentEvent e) {}

            public void componentResized(ComponentEvent e) {
                resetSize(getWidth(), getHeight());
            }

        });
        lines = new ArrayList();
        circles = new ArrayList();
    }

    protected void bind(SimulatorEngine engine) {
        this.engine = engine;
        lines.add(new Line(new Point(-1, -1), new Point(engine.getWorldWidth() + 1, -1)));
        lines.add(new Line(new Point(-1, -1), new Point(-1, engine.getWorldHeight() + 1)));
        lines.add(new Line(new Point(engine.getWorldWidth() + 1, engine.getWorldHeight() + 1), new Point(engine
                        .getWorldWidth() + 1, -1)));
        lines.add(new Line(new Point(engine.getWorldWidth() + 1, engine.getWorldHeight() + 1), new Point(-1, engine
                        .getWorldHeight() + 1)));
    }

    protected void start(int width, int height) {
        FileLoader loader = new FileLoader();
        try {
            File file = new File(loader.getTeamAMainBotAvatarFileName());
            teamAMainBotRaw = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Exception: failure reading image file for team A main bot.");
            return;
        }
        try {
            File file = new File(loader.getTeamASecondaryBotAvatarFileName());
            teamASecondaryBotRaw = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Exception: failure reading image file for team A secondary bot.");
            return;
        }
        try {
            File file = new File(loader.getTeamBMainBotAvatarFileName());
            teamBMainBotRaw = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Exception: failure reading image file for team B main bot.");
            return;
        }
        try {
            File file = new File(loader.getTeamBSecondaryBotAvatarFileName());
            teamBSecondaryBotRaw = ImageIO.read(file);
        } catch (IOException e) {
            System.err.println("Exception: failure reading image file for team B secondary bot.");
            return;
        }

        resetSize(width, height);
    }

    protected void resetSize(int width, int height) {
        setPreferredSize(new java.awt.Dimension(width, height));

        zoomFactor = Math.min(0.95D * width / engine.getWorldWidth(), 0.95D * height / engine.getWorldHeight());
        xModifier = ((int) (0.5D * (width - zoomFactor * engine.getWorldWidth())));
        yModifier = ((int) (0.5D * (height - zoomFactor * engine.getWorldHeight())));
        resetImageSize();

        repaint();
    }

    private void resetImageSize() {
        if (teamAMainBotRaw.getWidth() < teamAMainBotRaw.getHeight()) {
            teamAMainBotXScale = (100.0D * zoomFactor / teamAMainBotRaw.getHeight());
            teamAMainBotYScale = (100.0D * zoomFactor / teamAMainBotRaw.getHeight());
        } else {
            teamAMainBotXScale = (100.0D * zoomFactor / teamAMainBotRaw.getWidth());
            teamAMainBotYScale = (100.0D * zoomFactor / teamAMainBotRaw.getWidth());
        }

        if (teamBMainBotRaw.getWidth() < teamBMainBotRaw.getHeight()) {
            teamBMainBotXScale = (100.0D * zoomFactor / teamBMainBotRaw.getHeight());
            teamBMainBotYScale = (100.0D * zoomFactor / teamBMainBotRaw.getHeight());
        } else {
            teamBMainBotXScale = (100.0D * zoomFactor / teamBMainBotRaw.getWidth());
            teamBMainBotYScale = (100.0D * zoomFactor / teamBMainBotRaw.getWidth());
        }

        if (teamASecondaryBotRaw.getWidth() < teamASecondaryBotRaw.getHeight()) {
            teamASecondaryBotXScale = (100.0D * zoomFactor / teamASecondaryBotRaw.getHeight());
            teamASecondaryBotYScale = (100.0D * zoomFactor / teamASecondaryBotRaw.getHeight());
        } else {
            teamASecondaryBotXScale = (100.0D * zoomFactor / teamASecondaryBotRaw.getWidth());
            teamASecondaryBotYScale = (100.0D * zoomFactor / teamASecondaryBotRaw.getWidth());
        }

        if (teamBSecondaryBotRaw.getWidth() < teamBSecondaryBotRaw.getHeight()) {
            teamBSecondaryBotXScale = (100.0D * zoomFactor / teamBSecondaryBotRaw.getHeight());
            teamBSecondaryBotYScale = (100.0D * zoomFactor / teamBSecondaryBotRaw.getHeight());
        } else {
            teamBSecondaryBotXScale = (100.0D * zoomFactor / teamBSecondaryBotRaw.getWidth());
            teamBSecondaryBotYScale = (100.0D * zoomFactor / teamBSecondaryBotRaw.getWidth());
        }

        java.awt.GraphicsEnvironment env = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.GraphicsDevice device = env.getDefaultScreenDevice();
        GraphicsConfiguration config = device.getDefaultConfiguration();
        teamAMainBot = config
                        .createCompatibleImage((int) (2.0D * zoomFactor * 50.0D), (int) (2.0D * zoomFactor * 50.0D), 3);
        teamBMainBot = config
                        .createCompatibleImage((int) (2.0D * zoomFactor * 50.0D), (int) (2.0D * zoomFactor * 50.0D), 3);
        teamASecondaryBot = config
                        .createCompatibleImage((int) (2.0D * zoomFactor * 50.0D), (int) (2.0D * zoomFactor * 50.0D), 3);
        teamBSecondaryBot = config
                        .createCompatibleImage((int) (2.0D * zoomFactor * 50.0D), (int) (2.0D * zoomFactor * 50.0D), 3);

        Graphics2D gA = (Graphics2D) teamAMainBot.getGraphics();
        teamAMainBotAffineTransform = new AffineTransform();
        teamAMainBotAffineTransform.translate((int) (zoomFactor * 50.0D), (int) (zoomFactor * 50.0D));
        teamAMainBotAffineTransform.rotate(1.5707963267948966D);
        teamAMainBotAffineTransform.scale(teamAMainBotXScale, teamAMainBotYScale);
        teamAMainBotAffineTransform.translate(-teamAMainBotRaw.getWidth() / 2, -teamAMainBotRaw.getHeight() / 2);
        gA.drawImage(teamAMainBotRaw, teamAMainBotAffineTransform, this);

        Graphics2D gB = (Graphics2D) teamBMainBot.getGraphics();
        teamBMainBotAffineTransform = new AffineTransform();
        teamBMainBotAffineTransform.translate((int) (zoomFactor * 50.0D), (int) (zoomFactor * 50.0D));
        teamBMainBotAffineTransform.rotate(1.5707963267948966D);
        teamBMainBotAffineTransform.scale(teamBMainBotXScale, teamBMainBotYScale);
        teamBMainBotAffineTransform.translate(-teamBMainBotRaw.getWidth() / 2, -teamBMainBotRaw.getHeight() / 2);
        gB.drawImage(teamBMainBotRaw, teamBMainBotAffineTransform, this);

        gA = (Graphics2D) teamASecondaryBot.getGraphics();
        teamASecondaryBotAffineTransform = new AffineTransform();
        teamASecondaryBotAffineTransform.translate((int) (zoomFactor * 50.0D), (int) (zoomFactor * 50.0D));
        teamASecondaryBotAffineTransform.rotate(1.5707963267948966D);
        teamASecondaryBotAffineTransform.scale(teamASecondaryBotXScale, teamASecondaryBotYScale);
        teamASecondaryBotAffineTransform
                        .translate(-teamASecondaryBotRaw.getWidth() / 2, -teamASecondaryBotRaw.getHeight() / 2);
        gA.drawImage(teamASecondaryBotRaw, teamASecondaryBotAffineTransform, this);

        gB = (Graphics2D) teamBSecondaryBot.getGraphics();
        teamBSecondaryBotAffineTransform = new AffineTransform();
        teamBSecondaryBotAffineTransform.translate((int) (zoomFactor * 50.0D), (int) (zoomFactor * 50.0D));
        teamBSecondaryBotAffineTransform.rotate(1.5707963267948966D);
        teamBSecondaryBotAffineTransform.scale(teamBSecondaryBotXScale, teamBSecondaryBotYScale);
        teamBSecondaryBotAffineTransform
                        .translate(-teamBSecondaryBotRaw.getWidth() / 2, -teamBSecondaryBotRaw.getHeight() / 2);
        gB.drawImage(teamBSecondaryBotRaw, teamBSecondaryBotAffineTransform, this);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2d = ((Graphics2D) g.create());


        if (DetectBrainAffichage.getInstance().getSize() > 0) {
            for (int j = 0; j < 2; j++) {
                //TODO separate into multiple fori and make accessors available from Brains

                double tempX = DetectBrainAffichage.getInstance().getScout(j).getPos().getX()-50;
                double tempY = DetectBrainAffichage.getInstance().getScout(j).getPos().getY()-50;

                Circle c = new Circle(new CartCoordinate((int)Math.round(tempX),(int)Math.round(tempY)), 550, Color.BLACK);
                c.draw(g2d, xModifier, yModifier, zoomFactor, 1);
            }
        }

        if (DetectBrainAffichage.getInstance().getSize() > 0) {
            for (int j = 0; j < 3; j++) {
                //TODO separate into multiple fori and make accessors available from Brains

                double tempX = DetectBrainAffichage.getInstance().getTank(j).getPos().getX()-50;
                double tempY = DetectBrainAffichage.getInstance().getTank(j).getPos().getY()-50;

                Circle c = new Circle(new CartCoordinate((int)Math.round(tempX),(int)Math.round(tempY)), 250, Color.BLACK);
                c.draw(g2d, xModifier, yModifier, zoomFactor, 1);
            }
        }

        for (int i = 0; i < lines.size(); i++) {
            ((Line) lines.get(i)).draw(g2d, xModifier, yModifier, zoomFactor);
        }

        for (int i = 0; i < 3; i++) {
            af = new AffineTransform();
            af.translate((int) ((((Bot) engine.getBots().get(i))
                            .getX() - 50.0D) * zoomFactor + xModifier), (int) ((((Bot) engine.getBots().get(i))
                            .getY() - 50.0D) * zoomFactor + yModifier));
            af.rotate(((Bot) engine.getBots().get(i)).getHeading(), teamAMainBot.getWidth() / 2, teamAMainBot
                            .getHeight() / 2);
            g2d.drawImage(teamAMainBot, af, this);
            healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i)).getX() - 50.0D), (int) (((Bot) engine
                            .getBots().get(i)).getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                            .getX() + 50.0D), (int) (((Bot) engine.getBots().get(i)).getY() - 60.0D)), Color.RED);

            healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            if (!((Bot) engine.getBots().get(i)).isDestroyed()) {
                healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D + 100.0D * ((Bot) engine.getBots().get(i)).getHealth() / ((Bot) engine
                                .getBots().get(i)).getMaxHealth()), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), Color.GREEN);

                healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            }
        }

        for (int i = 3; i < 6; i++) {
            af = new AffineTransform();
            af.translate((int) ((((Bot) engine.getBots().get(i))
                            .getX() - 50.0D) * zoomFactor + xModifier), (int) ((((Bot) engine.getBots().get(i))
                            .getY() - 50.0D) * zoomFactor + yModifier));
            af.rotate(((Bot) engine.getBots().get(i)).getHeading(), teamBMainBot.getWidth() / 2, teamBMainBot
                            .getHeight() / 2);
            g2d.drawImage(teamBMainBot, af, this);
            healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i)).getX() - 50.0D), (int) (((Bot) engine
                            .getBots().get(i)).getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                            .getX() + 50.0D), (int) (((Bot) engine.getBots().get(i)).getY() - 60.0D)), Color.RED);

            healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            if (!((Bot) engine.getBots().get(i)).isDestroyed()) {
                healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D + 100.0D * ((Bot) engine.getBots().get(i)).getHealth() / ((Bot) engine
                                .getBots().get(i)).getMaxHealth()), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), Color.GREEN);

                healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            }
        }

        for (int i = 6; i < 8; i++) {
            af = new AffineTransform();
            af.translate((int) ((((Bot) engine.getBots().get(i))
                            .getX() - 50.0D) * zoomFactor + xModifier), (int) ((((Bot) engine.getBots().get(i))
                            .getY() - 50.0D) * zoomFactor + yModifier));
            af.rotate(((Bot) engine.getBots().get(i)).getHeading(), teamASecondaryBot.getWidth() / 2, teamASecondaryBot
                            .getHeight() / 2);
            g2d.drawImage(teamASecondaryBot, af, this);
            healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i)).getX() - 50.0D), (int) (((Bot) engine
                            .getBots().get(i)).getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                            .getX() + 50.0D), (int) (((Bot) engine.getBots().get(i)).getY() - 60.0D)), Color.RED);

            healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            if (!((Bot) engine.getBots().get(i)).isDestroyed()) {
                healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D + 100.0D * ((Bot) engine.getBots().get(i)).getHealth() / ((Bot) engine
                                .getBots().get(i)).getMaxHealth()), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), Color.GREEN);

                healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            }
        }

        for (int i = 8; i < 10; i++) {
            af = new AffineTransform();
            af.translate((int) ((((Bot) engine.getBots().get(i))
                            .getX() - 50.0D) * zoomFactor + xModifier), (int) ((((Bot) engine.getBots().get(i))
                            .getY() - 50.0D) * zoomFactor + yModifier));
            af.rotate(((Bot) engine.getBots().get(i)).getHeading(), teamBSecondaryBot.getWidth() / 2, teamBSecondaryBot
                            .getHeight() / 2);
            g2d.drawImage(teamBSecondaryBot, af, this);
            healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i)).getX() - 50.0D), (int) (((Bot) engine
                            .getBots().get(i)).getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                            .getX() + 50.0D), (int) (((Bot) engine.getBots().get(i)).getY() - 60.0D)), Color.RED);

            healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            if (!((Bot) engine.getBots().get(i)).isDestroyed()) {
                healthBar = new Line(new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), new Point((int) (((Bot) engine.getBots().get(i))
                                .getX() - 50.0D + 100.0D * ((Bot) engine.getBots().get(i)).getHealth() / ((Bot) engine
                                .getBots().get(i)).getMaxHealth()), (int) (((Bot) engine.getBots().get(i))
                                .getY() - 60.0D)), Color.GREEN);

                healthBar.draw(g2d, xModifier, yModifier, zoomFactor, 10);
            }
        }

        CartCoordinate p = null;
        for (Bullet b : engine.getCurrentBullets()) {
            p=new CartCoordinate(b.getX(), b.getY());
            Circle c = new Circle(p, b.getRadius(), Color.BLACK);
            c.draw(g2d, xModifier, yModifier, zoomFactor);
        }
        for (Bullet b : engine.getCurrentExplosions()) {
            p=new CartCoordinate(b.getX(), b.getY());
            Circle c = new Circle(p, b.getRadius() * 3.0D, Color.RED);
            c.draw(g2d, xModifier, yModifier, zoomFactor, 20);
        }

        for (Position position : MasterMind.getInstance().getTargets()) {
            double xx = (position.getX() - 50) * zoomFactor + xModifier;
            double yy = (position.getY() - 50) * zoomFactor + yModifier;
            Color col = null;
            switch (position.getTypes()) {

                case OpponentMainBot:
                    col = Color.RED;
                    break;
                case OpponentSecondaryBot:
                    col = Color.PINK;
                    break;
                case TeamMainBot:
                    col = Color.BLUE;
                    break;
                case TeamSecondaryBot:
                    col = Color.CYAN;
                    break;
                case Wreck:
                    col = Color.black;
                    break;
                case BULLET:
                    col = Color.GREEN;
                    break;
                default:
                    col = Color.gray;
                    break;
            }
            g2d.setColor(col);
            g2d.drawRect((int) Math.round(xx -3), (int) Math.round(yy-3), 6, 6);

        }
        g2d.setColor(Color.black);
        double xx = (2550 - 50) * zoomFactor + xModifier;
        double yy = (850 - 50) * zoomFactor + yModifier;
        g2d.drawRect((int) Math.round(xx -5), (int) Math.round(yy-5), 10, 10);

    }

    protected void shiftLeftAll() {
        xModifier -= 5;
        repaint();
    }

    protected void shiftUpAll() {
        yModifier -= 5;
        repaint();
    }

    protected void shiftDownAll() {
        yModifier += 5;
        repaint();
    }

    protected void shiftRightAll() {
        xModifier += 5;
        repaint();
    }

    protected void zoomOut() {
        zoomFactor *= 0.98D;
        xModifier += 5;
        yModifier += 5;
        resetImageSize();
        repaint();
    }

    protected void zoomIn() {
        zoomFactor /= 0.98D;
        xModifier -= 5;
        yModifier -= 5;
        resetImageSize();
        repaint();
    }
}
