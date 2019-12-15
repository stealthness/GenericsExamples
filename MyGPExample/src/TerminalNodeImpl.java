import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class TerminalNodeImpl implements Node {


    Double constant;

    public TerminalNodeImpl( double constant){
        super();
        this.constant = constant;
    }


    @Override
    public boolean isTerminalNode() {
        return  true;
    }

    @Override
    public Double calculate(Double[] inputs) {
        return constant;
    }

    @Override
    public String toClojureString() {
        return null;
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public Node getSubtree(int index) {
        return null;
    }
}
