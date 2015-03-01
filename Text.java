import java.awt.*;

public class Text {
    public static void fade(Graphics2D g2, double t, Color color) {
        double copy = t;
        while(copy > 10) copy -= 10;
        for(double i = 8.5; i < 10; i += .25) { if(i <= copy) { color = color.darker(); } }
        g2.setFont(new Font("Helvetica", Font.BOLD, 20));  g2.setColor(color);
    }
    public void pauseScreen(Graphics2D g2) {
        g2.setFont(new Font("Helvetica", Font.BOLD, 18));
        g2.drawString("(Paused...  Press 'p' to continue)", 315, 225);
    }
    public void deathScreen(Graphics2D g2, Player player, Snake snake, int total_orbs, PointList deaths,
    PointList bouncers, int ae_green, int ae_gold, int ae_pink, int ae_violet, double total, Image gameOver) {
        g2.drawImage(gameOver, 10, 10, null);
        
        int sec = (int)(total);
        int min = 0;
        while (sec - 60 >= 0) {
           min += 1;
           sec -= 60;
        }
        
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Helvetica", Font.BOLD, 16));
        
        g2.drawString("Orbs Eaten: ", 100, 215);
        g2.drawString("Obstacles: ", 275, 215);
        
        g2.drawString("Game Time:", 450, 215);
        g2.drawString("Snake Length:", 450, 250);
        g2.drawString("Orbs Displayed:", 450, 285);
        
        Color[] colors = {Color.RED, Color.GREEN, new Color(255, 215, 0),
         Color.PINK, new Color(138, 43, 226), Color.CYAN};
                
        g2.setColor(colors[2]);
        g2.drawString(min + " Minutes,  " + sec + " Seconds", 550, 215);
        g2.drawString("" + snake.getLength(), 570, 250);
        g2.drawString("" + total_orbs, 585, 285);
        
         for (int i = 1, x = 150, y = 250; i < 5; i++, y += 35) {
            g2.setColor(colors[i]);
            g2.fillOval(x - 20, y - 9, 7, 7);
            switch (i) {
                case 1: g2.drawString("x   " + ae_green,  x, y); break;
                case 2: g2.drawString("x   " + ae_gold,   x, y); break;
                case 3: g2.drawString("x   " + ae_pink,   x, y); break;
                case 4: g2.drawString("x   " + ae_violet, x, y); break;
            }
        }
        
        g2.setColor(colors[0]);
        g2.fillOval(305, 241, 7, 7);
        g2.drawString("x   " + deaths.size(), 325, 250);
        g2.setColor(colors[5]);
        g2.fillOval(305, 276, 7, 7);
        g2.drawString("x   " + bouncers.size(), 325, 285);
        
        
    }

    public void introScreen(Graphics2D g2, Image name) {
        g2.drawImage(name, 0, 0, null);

        g2.setFont(new Font("Helvetica", Font.BOLD, 15));
        g2.drawString ("Orbs:", 125, 125);
        g2.drawString ("Game Mechanics:", 425, 125);
        g2.drawString ("Start Game with Arrow Keys or WASD", 285, 450);

        Color[] colors = {Color.RED, Color.GREEN, new Color(255, 215, 0),
         Color.PINK, new Color(138, 43, 226), Color.CYAN};
        
         for (int i = 0, x = 175, y = 175; i < 6; i++, y += 35) {
            g2.setColor(colors[i]);
            g2.fillOval(x - 25, y - 9, 7, 7);
            switch (i) {
                case 0: g2.drawString("Death",               x, y); break;
                case 1: g2.drawString("Ever-Present Points", x, y); break;
                case 2: g2.drawString("Time Accelerator",    x, y); break;
                case 3: g2.drawString("Cleanup",             x, y); break;
                case 4: g2.drawString("Tail Cutter",         x, y); break;
                case 5: g2.drawString("Momentum Reverser",   x, y); break;
            }
            switch (i) {
                case 0: g2.drawString("Tail growth is relative to elapsed time",   x + 275, y); break;
                case 1: g2.drawString("The faster you collect orbs, the better",   x + 275, y); break;
                case 2: g2.drawString("Gold orbs double the speed of `time`",      x + 275, y); break;
                case 3: g2.drawString("Eat special orbs to clear up the field",    x + 275, y); break;
                case 4: g2.drawString("Take shortcuts by going through the walls", x + 275, y); break;
                case 5: g2.drawString("Press `p` to pause at any time",            x + 275, y); break;
            }
        }
    }
}
