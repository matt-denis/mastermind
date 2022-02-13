package mastermind;

public class LetteredColorFactory implements ColorFactory {

    private static final String letters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private int index;

    @Override
    public LetteredColor create() {
        return new LetteredColor(letters.charAt(index++));
    }
    
}
