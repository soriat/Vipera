public class Snake {
    private int size;
    private PointList record;
    private boolean alive;
    
    public Snake() {
        size = 7;
        record = new PointList();
        record.add(new Point(21, 252));
        record.add(new Point(21+size, 252));
        record.add(new Point(21+size+size, 252));
        alive = true;
    }
    public Snake(PointList list) {
        size = 7;
        record = list;
        record.add(new Point(21, 252));
        record.add(new Point(21+size, 252));
        record.add(new Point(21+size+size, 252));
        alive = true;
    }
    public PointList getRecord() { return record; }
    public Point reverse() { return record.reverse(); }
    public boolean testMove(String direction) {
        Point point = record.mostRecent();
        Point head = new Point(point.getX(), point.getY());
               if (direction.equals("North")) {
            head.addY(-size);
        } else if (direction.equals("South")) {
            head.addY(size);
        } else if (direction.equals("East"))  {
            head.addX(size);
        } else if (direction.equals("West"))  {
            head.addX(-size);
        }
               if (head.getX() < 0)   {
            head.setX(847);
        } else if (head.getX() > 847) {
            head.setX(0);
        } else if (head.getY() < 0)   {
            head.setY(497);
        } else if (head.getY() > 497) {
            head.setY(0);
        }
        Point point2 = record.get(record.size()-2);
        if (point2.getX() == head.getX() &&
           point2.getY() == head.getY())
        {
            return false;
        }
        return true;
    }
    public void move(String direction, boolean adding) {
        Point point = record.mostRecent();
        Point head = new Point(point.getX(), point.getY());
        if (direction.equals("North")) { head.addY(-size); } else
        if (direction.equals("South")) { head.addY(size);  } else
        if (direction.equals("East"))  { head.addX(size);  } else
        if (direction.equals("West"))  { head.addX(-size); }
        if (head.getX() < 0) { head.setX(847); }  else
        if (head.getX() > 847) { head.setX(0); } else
        if (head.getY() < 0) { head.setY(497); }   else
        if (head.getY() > 497) { head.setY(0); }
        record.add(new Point(head.getX(), head.getY()));
        if (!adding) {record.removeFirst();}
    }
    public void cut() {
        for(int i = 0; i < 20; i++) {
            if (record.get(0) != record.mostRecent() &&
               record.get(0) != null && record.size() > 2) {
                record.remove(0);
            }
        }
    }
    public int getSize()     { return size; }
    public boolean isAlive() { return alive; }
    public int getLength()   { return record.size(); }
    public void kill()       { alive = false; }
    public void giveLife()   { alive = true; }
}
