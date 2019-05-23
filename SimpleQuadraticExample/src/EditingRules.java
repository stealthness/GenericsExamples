import java.util.Optional;
import java.util.function.Function;

public class EditingRules {

    static Function<Node,Optional<Node>> ruleDivideByZero = (node -> {
        if(node.getClass()== FunctionNode.class && node.print().startsWith("(/") && node.print().endsWith("0.0)")){
           return Optional.of(new TerminalNode(1.0));
        }
        return Optional.empty();
    });
}
