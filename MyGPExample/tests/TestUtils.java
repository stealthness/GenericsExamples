import java.util.Arrays;
import java.util.List;

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
        return new FunctionNodeImpl(GPUtils.add, functionString, nodes);
    }
}
