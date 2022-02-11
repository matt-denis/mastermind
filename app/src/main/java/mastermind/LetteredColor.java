package mastermind;

public class LetteredColor extends Color {
    
    private char letter;

    public LetteredColor(char letter) { this.letter = letter; }

    @Override
    public String toString() {
        return Character.toString(letter);
    }
}
