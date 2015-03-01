import java.awt.*;

public class Point implements Comparable<Point> {
    private int x; //x Location
    private int y; //y Location
    private double d;  //Diameter of ellipse
    private double e;  //Time Elapsed since creation
    
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
        this.d = 7;
        this.e = 0;
    }
    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
        this.d = point.d;
        this.e = point.e;
    }
    public int getX()              { return this.x; }
    public int getY()              { return this.y; }
    public double getD()           { return this.d; }
    public double getE()           { return this.e; }
    public void setX(int value)    { this.x = value; }
    public void setY(int value)    { this.y = value; }
    public void addX(int value)    { this.x += value; }
    public void addY(int value)    { this.y += value; }
    public void addD(double value) { this.d += value; }
    public void addE()             { this.e += .01; }
    public void refreshE()         { this.e = 0; }
    public boolean equals(Point point) {
        try {
            if (point.x == this.x && point.y == this.y)
                return true;
        } catch (NullPointerException exception) { }
        return false;
    }
    @Override
    public int compareTo(Point p) {
        if (this.x != p.x)
           return this.x - p.x;
        return this.y - p.y;
    }
    @Override
    public String toString() {
        return "Point: x = " + this.x + ", " + "y = " + this.y;        
    }
    public void printScore(int value, Player player, Graphics2D g2, Point loc,
                           boolean b, int type, Color color) {
        if (value != 0) {
            if ((e < .75 && x != -100 && y != -100 && type == 0) ||
               (e < .75 && x == -100 && y == -100 && type == 1)) {
                    for(double i = .35; i < e; i += .075)
                        color = color.darker();
                    g2.setColor(color);
                    g2.setFont(new Font("Helvetica", Font.BOLD, 10));
                    g2.drawString("+ " + value, loc.getX() - 15, loc.getY() - (int)(50 * e));
            }
        }
        if (e == .75) b = false;
        if (e == 0) player.addScore(value);
        addE();
    }
}
