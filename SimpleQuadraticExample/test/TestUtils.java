public class TestUtils {

    // Terminal Nodes
    static TerminalNode oneTree = new TerminalNode(1.0);
    static TerminalNode twoTree = new TerminalNode(2.0);
    static VariableNode xTree = new VariableNode(0);
    // Function Nodes
    static FunctionNode xPlus1Tree = new FunctionNode(GPUtils.add, xTree, oneTree);
    static FunctionNode xSqrdTree = new FunctionNode(GPUtils.multiply, xTree, xTree);
    static FunctionNode xSqrdPlus1Tree = new FunctionNode(GPUtils.add, xSqrdTree, oneTree);
    static FunctionNode xSqrdPlus1TwiceTree = new FunctionNode(GPUtils.multiply, twoTree, xSqrdPlus1Tree);
}
