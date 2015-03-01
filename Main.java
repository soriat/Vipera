import java.awt.*;
import java.awt.Color.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.Timer;
import java.text.DecimalFormat;
import java.awt.geom.*;

public class Main extends JApplet implements Runnable, KeyListener {
    private Snake snake;
    private String dirAlpha, dirBeta, dirDelta;
    private Player player;
    private Graphics preg2; 
    private Image offscreen, background, name, gameOver;
    private Dimension dim;
    private Text text;
    private int speed = 30, add_counter, total_orbs, ae_green, ae_pink, ae_violet, ae_gold;
    private double seconds, pre_sec, g_sec, r_count, p_count, v_count, g_count, total;
    private double halfmin;
    private boolean running, paused, adding;
    private boolean bpg, bpgo, bpp, bpv;
    private Random generator;
    private Point app_g, app_go, app_p, app_r, app_v;
    private Point pg, pgo, pp, pv;
    private Timer timer;
    private PointList deaths, bouncers;
    final static float dash1[] = {10.0f};
    final static BasicStroke dashed = new BasicStroke(1.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);
    final static BasicStroke normal = new BasicStroke();
    
    @Override
    public void init() {
        background = getImage(getCodeBase(), "Background.gif");
        gameOver = getImage(getCodeBase(), "Game_Over.gif");
        name = getImage(getCodeBase(), "Name.gif");
        setBackground(new Color(0,0,0));
        setFont(new Font("Helvetica", Font.BOLD, 16));
        snake = new Snake();
        generator = new Random();
        ae_green = ae_pink = ae_violet = ae_gold = total_orbs = 0;
        dirAlpha = "East"; dirBeta = "North"; dirDelta = "West";
        player = new Player();
        seconds = r_count = g_count = total = 0.0;
        v_count = 5.0;
        p_count = 10.0;
        g_sec = 20.00;
        halfmin = 15.0;
        running = adding = paused = bpg = bpgo = bpp = bpv = false;
        app_g = app_go = app_v = app_p = app_r = new Point(-100, -100);
        pg = pgo = pv = pp = new Point(-100, -100);
        dim = getSize();
        text = new Text();
        deaths = new PointList();
        bouncers = new PointList();
        offscreen = createImage(dim.width,dim.height); 
        preg2 = offscreen.getGraphics();
        timer = new Timer(10, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seconds += .01; halfmin += .01; total += .01;
                r_count += .01; p_count += .01; v_count += .01;
                if (g_count >= 2000) { seconds += .01; }
                if (g_sec > 0) { g_sec -= .01; } g_count++;
            }
        });
    }
    @Override
    public void start() {
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
  		new Thread(this).start();
    } 
    @Override
    public void run() {
        while(true) {
           System.out.println("asdas");
            if (running) {
                timer.start();
                if (snake.isAlive()) {
                    if (snake.testMove(dirAlpha)) { snake.move(dirAlpha, adding); } else
                    if (snake.testMove(dirBeta))  { snake.move(dirBeta,  adding); } else
                    { snake.move(dirDelta, adding); }
                }
                repaint();
                try { Thread.sleep(speed); }
                catch (InterruptedException ex) { stop(); }
            }
        }
    }
    @Override
    public void update(Graphics g) { paint(g); }
    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) preg2;
        Graphics2D g3 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255));
        g2.clearRect(0, 0, 700, 504);
        g2.drawImage(background, 0, 0, null);
        PointList record = snake.getRecord();
        if (running) {
            String sColor = "Light Gray";
            int gray = 0, lightgray = 0, dark_gray = 0;
            for(int i = record.size() - 1; i >= 0; i--) {
                if (i == record.size() - 1) g2.setColor(new Color(255, 255, 255));
                else if (sColor.equals("Light Gray")) {
                    g2.setColor(new Color(238, 233, 233));
                    if (lightgray == 3) {sColor = "Gray";}
                    dark_gray = 0; lightgray++; }
                else if (sColor.equals("Gray")) {
                    g2.setColor(new Color(205, 201, 201));
                    if (gray == 2) {sColor = "Dark Gray";}
                    lightgray = 0; gray++; }
                else if (sColor.equals("Dark Gray")) {
                    g2.setColor(new Color(139, 137, 137));
                    if (dark_gray == 3) {sColor = "Light Gray";}
                    gray = 0; dark_gray++; }
                Point point = record.get(i);
                g2.fill3DRect(point.getX(), point.getY(), snake.getSize(), snake.getSize(), true);
            }
        }
        if (player.getLives()) {
            if (running || paused) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Helvetica", Font.BOLD, 13));
                g2.drawString("Score: " + player.getScore(), 10, 20);
            }
            if (!running && !paused) {
                text.introScreen(g2, name);
                repaint();
            }
            else if (paused) {
                text.pauseScreen(g2);
                repaint();
            }
        }
        else if (!player.getLives()) {
            text.deathScreen(g2, player, snake, total_orbs, deaths,
             bouncers, ae_green, ae_gold, ae_pink, ae_violet, total, gameOver);
            repaint();
        }
        if (running) {
            if (app_g.equals(new Point(-100, -100))) { seconds = 0; app_g  = shuffle(); total_orbs++; }
            if (g_count >= 2000) { if (app_go.equals(new Point(-100, -100))) { app_go = shuffle(); total_orbs++; }}
            if (p_count >= 60)   { if (app_p.equals (new Point(-100, -100))) { app_p  = shuffle(); total_orbs++; }}
            if (v_count >= 45)   { if (app_v.equals (new Point(-100, -100))) { app_v  = shuffle(); total_orbs++; }}
            if (r_count >= 10)   { deaths.add(shuffle()); r_count = 0; total_orbs++;}
            if (halfmin >= 45)   { bouncers.add(shuffle()); halfmin = 0; total_orbs++; }
            draworb(g2, app_g,  1, Color.GREEN);
            draworb(g2, app_go, 1, new Color(255, 215, 0));
            draworb(g2, app_p,  1, Color.PINK);
            draworb(g2, app_v,  1, new Color(138, 43, 226));
            if (bpg  == true) app_g.printScore( 1750, player, g2, pg,  bpg,  0, Color.GREEN.brighter());
            if (bpgo == true) app_go.printScore(2000, player, g2, pgo, bpgo, 1, new Color(255, 215, 0).brighter());
            if (bpp  == true) app_p.printScore( 2250, player, g2, pp,  bpp,  1, Color.PINK);
            if (bpv  == true) app_v.printScore( 2500, player, g2, pv,  bpv,  1, new Color(138, 43, 226).brighter());
            if (deaths.size() > 0) {
                for(int i = 0; deaths.get(i) != null; i++)
                    draworb(g2, deaths.get(i), 1, Color.RED.darker());
            }
            if (bouncers.size() > 0) {
                for(int i = 0; bouncers.get(i) != null; i++)
                    draworb(g2, bouncers.get(i), 1, Color.CYAN);
            }
            Point head = new Point(record.get(record.size()-1));
            for(int i = 0; i < record.size()-1; i++) {
                Point point = record.get(i);
                if (head.equals(point)) {
                    player.kill(); snake.kill(); running = false;
                    timer.stop(); repaint();
                }
            }
            if (deaths.size() > 0) {
                for(int i = 0; i <= deaths.size()-1; i++) {
                    Point point = deaths.get(i);
                    if (point.equals(head)) {
                        player.kill(); snake.kill(); running = false;
                        timer.stop(); repaint();
                    }
                }
            }
            if (bouncers.size() > 0) {
                for(int i = 0; i < bouncers.size(); i++) {
                    Point point = bouncers.get(i);
                    if (point.equals(head)) {
                        Point dir = snake.reverse();
                        int xdir = dir.getX(), ydir = dir.getY();
                        if (xdir < 0) { dirAlpha = "West";  } else
                        if (xdir > 0) { dirAlpha = "East";  } else
                        if (ydir < 0) { dirAlpha = "North"; } else
                        if (ydir > 0) { dirAlpha = "South"; } else { } 
                    }
                }
            }
            if (app_g.equals(head)) {
                adding = true; add_counter = 0; pg = app_g; bpg = true;
                app_g = new Point(-100, -100); pre_sec = seconds; ae_green++;
            }
            if (app_go.equals(head)) {
                pgo = app_go; app_go = new Point(-100, -100); bpgo = true;
                g_count = 0; g_sec = 20.00; ae_gold++;
            }
            if (app_p.equals(head)) {
                pp = app_p; app_p = new Point(-100, -100); p_count = 0; ae_pink++; bpp = true;
                for(int i = 0; i < 5; i++)
                    deaths.removeFirst();
            }
            if (app_v.equals(head)) {
                pv = app_v; app_v = new Point(-100, -100); v_count = 0; bpv = true;
                r_count = 10; snake.cut(); ae_violet++;
            }
        }
        if (add_counter > (int)pre_sec) { adding = false; }
        add_counter++;
        g3.drawImage(offscreen, 0, 0, this);
    }
    private void draworb(Graphics2D g2, Point app, int type, Color color) {
        if (!app.equals(new Point(-100, -100))) {
            g2.setStroke(dashed);
            g2.setColor(color);
            if (app.getD() < 25) {
                double d = app.getD(), diff = (app.getX() + 3.5) - (app.getX() + (d/2));
                g2.draw(new Ellipse2D.Double(app.getX() + diff, app.getY() + diff, d, d));
                app.addD(1);
            }
            g2.setStroke(normal);
            if (type == 1) g2.fillOval(app.getX(), app.getY(), 7, 7);
        }
    }
    private Point shuffle() {
        PointList head_test = snake.getRecord();
        Point head_tester = head_test.get(head_test.size()-1);
        int x = 0, y = 0;
        while (
        (x < 1)    ||    (y < 1)  ||
        (x == head_tester.getX()) ||
        (y == head_tester.getY()) ||
        (!pass(new Point(x, y), deaths)) ||
        (!pass(new Point(x, y), head_test)) ||
        (!pass(new Point(x, y), bouncers)) ||
        (x == app_g.getX()  && y == app_g.getY())  ||
        (x == app_go.getX() && y == app_go.getY()) ||
        (x == app_p.getX()  && y == app_p.getY())  ||
        (x == app_v.getX()  && y == app_v.getY())) {
            x = (int)(generator.nextDouble() * 1000);
            y = (int)(generator.nextDouble() * 1000);
            while(x > 847) { x -= 847; }
            while(y > 497) { y -= 497; }
            while(x%7 != 0) { x++; }
            while(y%7 != 0) { y++; }
        } return new Point(x, y);
    }
    private boolean pass(Point point, PointList list) {
        for(int i = 0; i < list.size()-1; i++) {
            Point Point = list.get(i);
            if (Point.getX() == point.getX() &&
            Point.getY() == point.getY()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        char c = e.getKeyChar();
        if (!paused) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT || c == 'a') {
                if (!"East".equals(dirAlpha)) { dirDelta = dirBeta; dirBeta = dirAlpha; dirAlpha = "West"; }
                if (!running && !paused) running = true; }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT || c == 'd') {
                if (!"West".equals(dirAlpha)) { dirDelta = dirBeta; dirBeta = dirAlpha; dirAlpha = "East"; }
                if (!running && !paused) running = true; }
            if (e.getKeyCode() == KeyEvent.VK_UP || c == 'w') {
                if (!"South".equals(dirAlpha)) {dirDelta = dirBeta; dirBeta = dirAlpha; dirAlpha = "North"; }
                if (!running && !paused) running = true; }
            if (e.getKeyCode() == KeyEvent.VK_DOWN || c == 's') {
                if (!"North".equals(dirAlpha)) {dirDelta = dirBeta; dirBeta = dirAlpha; dirAlpha = "South"; }
                if (!running && !paused) running = true; }
            if ((e.getKeyCode() == KeyEvent.VK_SPACE) && !player.getLives()) {
                init();
            }
        }
        if (c == 'p') {
            if (!paused && running) {
                timer.stop(); running = false; paused = true; repaint();
            }else if (paused) { running = true; paused = false; repaint(); }
        }
    }
    @Override
    public void keyReleased(KeyEvent e) { }
    @Override
    public void keyTyped(KeyEvent e) { }
}
