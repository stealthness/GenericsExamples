public class TestUtils {


    // Terminal Nodes
    static final TerminalNode minusOneTree = new TerminalNode(-1.0);
    static final TerminalNode zeroTree = new TerminalNode(0.0);
    static final TerminalNode oneTree = new TerminalNode(1.0);
    static final TerminalNode twoTree = new TerminalNode(2.0);
    static final VariableNode xTree = new VariableNode(0);
    // Function Nodes
    // depth 1
    static final FunctionNode xPlus2Tree = new FunctionNode(GPUtils.add, xTree, twoTree);
    static final FunctionNode xPlus1Tree = new FunctionNode(GPUtils.add, xTree, oneTree);
    static final FunctionNode oneDivideXTree = new FunctionNode(GPUtils.protectedDivision, oneTree, xTree);
    static final FunctionNode twoDivideXTree = new FunctionNode(GPUtils.protectedDivision, twoTree, xTree);
    static final FunctionNode xSqrdTree = new FunctionNode(GPUtils.multiply, xTree, xTree);
    static final FunctionNode twiceXTree = new FunctionNode(GPUtils.add, xTree, xTree);
    static final FunctionNode zeroXTree = new FunctionNode(GPUtils.subtract, xTree, xTree);
    // depth 2
    static final FunctionNode xSqrdPlus1Tree = new FunctionNode(GPUtils.add, xSqrdTree, oneTree);
    static final FunctionNode xSqrdPlusXPlus1TreeD2 = new FunctionNode(GPUtils.add, xSqrdTree, xPlus1Tree);
    static final FunctionNode xSqrdPlusOneDivideXTree = new FunctionNode(GPUtils.add, xSqrdTree, oneDivideXTree);
    static final FunctionNode twoXSqrdTree = new FunctionNode(GPUtils.multiply, twoTree ,xSqrdTree);
    static final FunctionNode xPlus1MultiplyXTree = new FunctionNode(GPUtils.multiply, xPlus1Tree, xTree);
    // depth 3
    static final FunctionNode xSqrdPlus1TwiceTree = new FunctionNode(GPUtils.multiply, twoTree, xSqrdPlus1Tree);
    static final FunctionNode xSqrdPlusXPlus1TreeD3 = new FunctionNode(GPUtils.add, xSqrdPlus1Tree, xTree);


    static final Node oneNode = new TerminalNode(1.0);
    static final Node twoNode = new TerminalNode(2.0);

    static final Node xNode = new VariableNode(0);
}
