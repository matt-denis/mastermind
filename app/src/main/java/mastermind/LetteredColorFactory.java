package mastermind;

public class LetteredColorFactory implements ColorFactory {

    private final String letters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private int index;

    @Override
    public LetteredColor create() {
        return new LetteredColor(letters.charAt(index++));
    }
    
}
