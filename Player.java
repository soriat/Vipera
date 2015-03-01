public class Player {
    private boolean alive;
    private int score;

    public Player() {
        alive = true;
        score = 0;
    }
    public int getScore() { return score; }
    public void addScore(int points) { score += points; }
    public boolean getLives() { return alive; }
    public void kill() { alive = false; }
}