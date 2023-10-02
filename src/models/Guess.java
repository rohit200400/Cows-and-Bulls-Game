package models;

public class Guess {
    private String guess;
    private int bulls;
    private int cows;

    public Guess(Guess currentGuess) {
        this.guess = currentGuess.getGuess();
        this.cows = currentGuess.getCows();
        this.bulls = currentGuess.getBulls();
    }

    public Guess() {
        this.guess = null;
        this.bulls = 0;
        this.cows = 0;
    }

    public int getCows() {
        return cows;
    }

    public void setCows(int cows) {
        this.cows = cows;
    }

    public int getBulls() {
        return bulls;
    }

    public void setBulls(int bulls) {
        this.bulls = bulls;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    @Override
    public String toString() {
        return guess + " : " +
                " bulls=" + bulls +
                ", cows=" + cows + "\n";
    }
}
