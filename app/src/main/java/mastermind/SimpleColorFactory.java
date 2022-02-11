package mastermind;

public class SimpleColorFactory implements ColorFactory {

    @Override
    public Color create() {
        return new Color();
    }
    
}
