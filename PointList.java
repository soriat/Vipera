import java.util.*;

public class PointList {
    public ArrayList<Point> pointList;
    public PointList() {
        pointList = new ArrayList<Point>();
    }
    public PointList(int size) {
        pointList = new ArrayList<Point>(size);
    }
    public void add(Point point) {
        pointList.add(point);
    }
    public Point reverse() {
        Collections.reverse(pointList);
        Point prior = new Point(pointList.get(pointList.size() - 2));
        Point first = new Point(pointList.get(pointList.size() - 1));
        first.addX(-prior.getX());
        first.addY(-prior.getY());
        return first;
    }
    public void removePoint(int x, int y) {
        try {
            for(int i = 0; pointList.get(i) != null; i++) {
                Point point = pointList.get(i);
                if (point.getX() == x && point.getY() == y) {
                    pointList.remove(i);
                    break;
                }
            }
        }
        catch (NullPointerException e)   { }
        catch (InputMismatchException e) { }
        catch (NoSuchElementException e) { }
    }
    public void removeAll() {
        pointList = new ArrayList<Point>();
    }
    public void remove(int index) {
        pointList.remove(index);
    }
    public Point find(int x, int y) {
        try {
            for(int i = 0; pointList.get(i) != null; i++) {
                Point point = pointList.get(i);
                if (point.getX() == x && point.getY() == y)
                    return pointList.get(i);
            }
        }
        catch (NullPointerException e)   { }
        catch (InputMismatchException e) { }
        catch (NoSuchElementException e) { }
        return null;
    }
    public Point get(int target) {
        if (target < pointList.size())
            return pointList.get(target);
        return null;
    }
    public void sort() {
        Collections.sort(pointList);
    }
    public boolean equals(ArrayList<Point> array) {
        try {
            for(int i = 0; pointList.get(i) != null; i++) {
                Point point1 = pointList.get(i);
                Point point2 = array.get(i);
                if (point1.getX() != point2.getX() ||
                    point1.getY() != point2.getY() )
                    return false;
            }
        }
        catch (NullPointerException e) { }
        catch (InputMismatchException e) {System.err.println("illegal input");}
        catch (NoSuchElementException e) {System.err.println("illegal input");}
        return true;
    }
    @Override
    public String toString() {
        String string = new String();
        try {
            for(int i = 0; pointList.get(i) != null; i++) {
                Point point = pointList.get(i);
                string += point.toString() + "\n";
            }
        }
        catch (IndexOutOfBoundsException e1) { }
        catch (InputMismatchException e) {System.err.println("illegal input");}
        catch (NoSuchElementException e) {System.err.println("illegal input");}
        return string;
    }
    public int size() {
        return pointList.size();
    }
    public Point mostRecent() {
        return pointList.get(pointList.size()-1);
    }
    public void removeFirst() {
        if (pointList.size() > 1) pointList.remove(0);
    }
}
