import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtils {

    public static final double TOL = 0.0000001;
    static TerminalNodeImpl oneNode = new TerminalNodeImpl(1.0);
    static TerminalNodeImpl twoNode = new TerminalNodeImpl(2.0);
    static FunctionNodeImpl addNode1Plu2 =  new FunctionNodeImpl(GPUtils.add, "+",Arrays.asList(oneNode, twoNode));

    static Node getConstantNode(double constant){
        return new TerminalNodeImpl(constant);
    }

    public static Node getVariableNode(String variableString,int variableIndex) {
        return new VariableNodeImpl(variableString, variableIndex);
    }

    public static Node getFunctionNode(String functionString, List<Node> nodes) {
        return new FunctionNodeImpl(getFunction(functionString), functionString, nodes);
    }

    static BiFunction<Double[], List<Node>, Double> getFunction(String functionString){
        return switch (functionString){
            case "+" -> GPUtils.add;
            case "-" -> GPUtils.subtract;
            case "*" -> GPUtils.multiply;
            case "/" -> GPUtils.divide;
            default -> GPUtils.identity;
        };
    }


    static void assertNode(Optional<String> expString, Optional<Integer> expDepth,Optional<Integer> expSize,
                           Optional<Double> expResult, Optional<Double[]> inputs, Node actNode){
        if (expString.isPresent()){
            assertEquals(expString.get(), actNode.toClojureString());
        }
        if (expDepth.isPresent()){
            assertEquals(expDepth.get(), actNode.getDepth());
        }
        if (expSize.isPresent()){
            assertEquals(expSize.get(), actNode.size());
        }
        if (expResult.isPresent() && inputs.isPresent()){
            assertEquals(expResult.get(), actNode.calculate(inputs.get()));
        }

    }
}
