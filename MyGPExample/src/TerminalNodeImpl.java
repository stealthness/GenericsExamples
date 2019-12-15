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
        return String.format("(%.2f)",constant);
    }

    @Override
    public Node clone() {
        return new TerminalNodeImpl(constant);
    }

    @Override
    public Node getSubtree(int index) {
        if (index == 0){
            return this;
        }else{
            throw new IndexOutOfBoundsException("Terminal Node index : " + index + " > 0");
        }
    }

}
