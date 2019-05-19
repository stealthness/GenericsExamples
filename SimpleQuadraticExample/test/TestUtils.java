public class TestUtils {



    // Terminal Nodes
    static TerminalNode oneTree = new TerminalNode(1.0);
    static TerminalNode twoTree = new TerminalNode(2.0);
    static VariableNode xTree = new VariableNode(0);
    // Function Nodes
    // depth 1
    static FunctionNode xPlus2Tree = new FunctionNode(GPUtils.add, xTree, twoTree);
    static FunctionNode xPlus1Tree = new FunctionNode(GPUtils.add, xTree, oneTree);
    static FunctionNode oneDivideXTree = new FunctionNode(GPUtils.protectedDivision, oneTree, xTree);
    static FunctionNode xSqrdTree = new FunctionNode(GPUtils.multiply, xTree, xTree);
    // depth 2
    static FunctionNode xSqrdPlus1Tree = new FunctionNode(GPUtils.add, xSqrdTree, oneTree);
    static FunctionNode xSqrdPlusXPlus1TreeD2 = new FunctionNode(GPUtils.add, xSqrdTree, xPlus1Tree);
    static FunctionNode xSqrdPlusOneDivideXTree = new FunctionNode(GPUtils.add, xSqrdTree, oneDivideXTree);
    public static FunctionNode twoXSqrdTree = new FunctionNode(GPUtils.multiply, twoTree ,xSqrdTree);
    // depth 3
    static FunctionNode xSqrdPlus1TwiceTree = new FunctionNode(GPUtils.multiply, twoTree, xSqrdPlus1Tree);
    static FunctionNode xSqrdPlusXPlus1TreeD3 = new FunctionNode(GPUtils.add, xSqrdPlus1Tree, xTree);
}
