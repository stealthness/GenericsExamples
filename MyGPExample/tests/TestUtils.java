import java.util.Arrays;

public class TestUtils {

    static TerminalNodeImpl oneNode = new TerminalNodeImpl(1.0);
    static TerminalNodeImpl twoNode = new TerminalNodeImpl(2.0);
    static FunctionNodeImpl addNode1Plu2 =  new FunctionNodeImpl(GPUtils.add, "+",Arrays.asList(oneNode, twoNode));

    static Node getConstantNode(double constant){
        return new TerminalNodeImpl(constant);
    }
}
