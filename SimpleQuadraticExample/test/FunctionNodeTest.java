import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeTest {


    private static final double TOL = 0.000001;
    private double v0;
    private double v1;

    private Node t0;
    private Node t1;

    private VariableNode x0;
    private VariableNode x1;
    private DoubleStream d;


    @BeforeEach
    void SetUp(){
        v0 = 1.2;
        v1 = -2.8;

        t0 = new TerminalNode(v0);
        t1 = new TerminalNode(v1);

        x0 = new VariableNode(0);
        x1 = new VariableNode(1);

        d = DoubleStream.of(-1.0,-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1
                ,0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0);

    }

    // Test Create

    @Test
    void testNodeWithLambdaAndTwoTerminalNode(){
        var f0 = new FunctionNode(GPUtils.add,t0,t1);
        assertEquals(v0+v1,f0.apply(null),TOL);
    }

    @Test
    void testPrintNodeWithLambdaAndTwoTerminalNode(){
        var f0 = new FunctionNode(GPUtils.add,t0,t1);
        var expStr = "(" + f0.getFunction().getClojureString() + " " + v0 + " " + v1 + ")";
        assertEquals(expStr,f0.print());
        assertEquals(expStr, f0.print());
    }

    @Test
    void testNodeThatIsVariableNode(){
        var variables = new double[]{v0,v1};
        assertEquals(v0,x0.apply(variables),TOL);
        assertEquals(v1,x1.apply(variables),TOL);
    }


    @Test
    void testExpressionCreation1(){
        // create tree "2.0 + 0.0"

        Node root = new FunctionNode(GPUtils.add, new TerminalNode(2.0), new TerminalNode(0.0));
        IntStream.range(-5,5).forEach(i -> {
            assertEquals(2.0, root.apply(new double[]{(double)i}));
        });
        assertEquals("(+ 2.0 0.0)",root.print());

        var fit = d.reduce(0,(a,b) -> a + Math.abs((b*b + b + 1) - root.apply(new double[]{b})));
        // The know expected fitness from the Field Handbook of GP
        assertEquals(17.98,fit, TOL);
    }

    @Test
    void testExpressionCreation2(){
        // function xTree+1
        // create tree (- (+ x0 1.0) 0.0)

        Node subtree = new FunctionNode(GPUtils.add, new VariableNode(0), new TerminalNode(1.0));

        Node root = new FunctionNode(GPUtils.subtract,subtree, new TerminalNode(0.0));
        IntStream.range(-5,5).forEach(i -> {
            assertEquals(i+1.0, root.apply(new double[]{(double)i}),TOL);
        });
        assertEquals("(- (+ x0 1.0) 0.0)",root.print());


        var fit = d.reduce(0,(a,b) -> a + Math.abs((b*b + b + 1) - root.apply(new double[]{b})));
        // the known expected fitness as given Field Handbook of GP
        assertEquals(7.7, fit, TOL);
    }

    // Test Depth

    @Test
    void testDepthTerminalAreZero(){
        List<Node> testList = Arrays.asList(TestUtils.oneTree,TestUtils.twoNode,TestUtils.zeroTree,TestUtils.minusOneTree);
        testList.stream().forEach(node -> assertDepth(0,node));
    }

    @Test
    void testFunctionsOfDepth1(){
        List<Node> testList = Arrays.asList(TestUtils.xPlus1Tree,TestUtils.xPlus2Tree,
                TestUtils.oneDivideXTree,TestUtils.twoDivideXTree,TestUtils.twiceXTree,TestUtils.zeroXTree);
        testList.stream().forEach(node -> assertDepth(1,node));
    }

    @Test
    void testFunctionsOfDepth2() {
        List<Node> testList = Arrays.asList(TestUtils.xSqrdPlus1Tree, TestUtils.xSqrdPlusXPlus1TreeD2, TestUtils.twoXSqrdTree,
                TestUtils.xSqrdPlusOneDivideXTree, TestUtils.xPlus1MultiplyXTree);
        testList.stream().forEach(node -> assertDepth(2, node));
    }

    @Test
    void testFunctionsOfDepth3(){
        List<Node> testList = Arrays.asList(TestUtils.xSqrdPlus1TwiceTree,TestUtils.xSqrdPlusXPlus1TreeD3);
        testList.stream().forEach(node -> assertDepth(3,node));
    }

    void assertDepth(int expeDepth, Node actNode){
        assertEquals(expeDepth,actNode.getDepth(),actNode.print());
    }


    // Test Compare

    @Test
    void testCompareFunctionNodeOfDepth1(){
        assertEquals(0, TestUtils.xPlus1Tree.compareTo(TestUtils.xSqrdTree));
        assertEquals(0,TestUtils.xPlus2Tree.compareTo(TestUtils.xPlus1Tree));
    }

    @Test
    void testCompareFunctionOfDepth2(){
        assertEquals(1, TestUtils.xSqrdPlus1Tree.compareTo(TestUtils.xSqrdTree));
        assertEquals(-1,TestUtils.xPlus1Tree.compareTo(TestUtils.xSqrdPlus1Tree));
        assertEquals(1, TestUtils.xSqrdPlusXPlus1TreeD2.compareTo(TestUtils.xSqrdPlus1Tree));
        assertEquals(-1, TestUtils.xSqrdPlus1Tree.compareTo(TestUtils.xSqrdPlusXPlus1TreeD2));

        assertEquals(2, TestUtils.xSqrdPlusXPlus1TreeD2.getDepth());
        assertEquals(2, TestUtils.xSqrdPlusOneDivideXTree.getDepth());
        assertEquals(7, TestUtils.xSqrdPlusXPlus1TreeD2.size());
        assertEquals(7, TestUtils.xSqrdPlusOneDivideXTree.size());
        assertEquals(0, TestUtils.xSqrdPlusXPlus1TreeD2.compareTo(TestUtils.xSqrdPlusOneDivideXTree));

    }

    @Test
    void testEquality(){
        assertEquals(TestUtils.xPlus1Tree,new FunctionNode(GPUtils.add,TestUtils.xNode,TestUtils.oneNode));
        assertEquals(TestUtils.xPlus1Tree,TestUtils.xPlus1Tree);
        assertNotEquals(TestUtils.xPlus1Tree,TestUtils.xSqrdTree);
        assertNotEquals(TestUtils.xPlus1Tree,TestUtils.xSqrdPlusOneDivideXTree);
    }


    // test getSubtree random node

    @Test
    void testSelectRootNode0xSqrdTree(){
        Node root = TestUtils.xSqrdTree;
        Node selectedRoot = ((FunctionNode)root).getSubtree(0);
        assertEquals(root, selectedRoot);
    }

    @Test
    void testSelectNode0WithXPlus1Tree(){
        Node root = TestUtils.xPlus1Tree;
        Node selectedRoot = ((FunctionNode)root).getSubtree(0);
        assertEquals(root, selectedRoot);

        assertNotEquals(root,((FunctionNode)root).getSubtree(1));
        assertEquals(TestUtils.xNode,((FunctionNode)root).getSubtree(1));


        assertNotEquals(root,((FunctionNode)root).getSubtree(2));
        assertEquals(TestUtils.oneNode,((FunctionNode)root).getSubtree(2));
    }

    @Test
    void testSelectNode1(){
        // should always return the node1
        Node root = TestUtils.xSqrdTree.clone();
        Node selectedRoot = ((FunctionNode)root).getSubtree(1);
        assertEquals(TestUtils.xNode, selectedRoot);

        root = TestUtils.xSqrdPlus1Tree.clone();
        selectedRoot = ((FunctionNode)root).getSubtree(1);
        assertEquals(TestUtils.xSqrdTree, selectedRoot);

        root = TestUtils.oneDivideXTree.clone();
        selectedRoot = ((FunctionNode)root).getSubtree(1);
        assertEquals(TestUtils.oneNode, selectedRoot);



    }



    @Test
    void changeSubtreeAtIndex1() {
        Node node = TestUtils.xPlus1Tree.clone();
        ((FunctionNode)node).changeSubtreeAt(1,TestUtils.xSqrdTree.clone());
        assertEquals(node,TestUtils.xSqrdPlus1Tree);


        node = TestUtils.xSqrdTree.clone();
        ((FunctionNode)node).changeSubtreeAt(1,TestUtils.xPlus1Tree.clone());
        assertEquals(node,TestUtils.xPlus1MultiplyXTree);

    }

    @Test
    void changeSubtreeAtIndex2() {
        Node node = TestUtils.xPlus1Tree.clone();
        ((FunctionNode)node).changeSubtreeAt(2,TestUtils.twoTree.clone());
        assertEquals(node,TestUtils.xPlus2Tree);
    }

    @Test
    void changeSubtreeAtIndex3() {
        Node node = TestUtils.xSqrdPlusOneDivideXTree.clone();
        ((FunctionNode)node).changeSubtreeAt(2,TestUtils.oneTree.clone());
        assertEquals(node,TestUtils.oneXPlusDivideXTree);
    }

    @Test
    void changeSubtreeAtIndex6() {
        Node node = TestUtils.xSqrdPlusOneDivideXTree.clone();
        System.out.println(node.print());
        ((FunctionNode)node).changeSubtreeAt(1,TestUtils.xPlus1Tree.clone());
        ((FunctionNode)node).changeSubtreeAt(6,TestUtils.twiceXTree.clone());
        System.out.println(node.print());
        System.out.println(TestUtils.oneXPlusDivideTwiceXTree.print());
        assertEquals(node,TestUtils.oneXPlusDivideTwiceXTree);
    }




    @Test
    void checkChangeOfRef(){
        Node subNode0 = TestUtils.xNode.clone();
        Node subNode1 = TestUtils.oneNode.clone();
        Node node1 = new FunctionNode(GPUtils.add,subNode0, subNode1);
        Node node2 = new FunctionNode(GPUtils.add,subNode0, subNode1);
        assertEquals(node1, node2);
        var expString = "(+ x0 1.0)";

        subNode0 = TestUtils.xPlus1MultiplyXTree;

        assertEquals(expString,node1.print());


        node2 = TestUtils.xSqrdTree.clone();
        ((FunctionNode)node1).setNode(0, node2);
        expString = "(+ (* x0 x0) 1.0)";
        assertEquals(expString,node1.print());
        node2 = TestUtils.zeroTree;
        assertEquals(expString,node1.print());




    }

    @Test
    void testChangeOfRef2(){
        Node subNode0 = TestUtils.xPlus1MultiplyXTree;
        Node subNode1 = TestUtils.xSqrdPlusXPlus1TreeD3;
        Node subNode2 = TestUtils.xSqrdPlus1Tree;

        final String subNodeString0 = subNode0.print();
        final String subNodeString1 = subNode1.print();
        final String subNodeString2 = subNode2.print();

        assertEquals(subNodeString0,subNode0.print());
        assertEquals(subNodeString1,subNode1.print());
        assertEquals(subNodeString2,subNode2.print());

        FunctionNode testNode = new FunctionNode(GPUtils.add, new FunctionNode(GPUtils.protectedDivision,subNode0,subNode1),subNode2);
        final String expString = testNode.print();
        assertEquals(expString, testNode.print());

        System.out.println(testNode.print());

        testNode.changeSubtreeAt(1,subNode0);

        System.out.println(testNode.print());

        testNode.changeSubtreeAt(2,subNode0);

        System.out.println(testNode.print());

        assertEquals(subNodeString0,subNode0.print());
        assertEquals(subNodeString1,subNode1.print());
        assertEquals(subNodeString2,subNode2.print());

        assertEquals(subNodeString0,TestUtils.xPlus1MultiplyXTree.print());
        assertEquals(subNodeString1,TestUtils.xSqrdPlusXPlus1TreeD3.print());
        assertEquals(subNodeString2,TestUtils.xSqrdPlus1Tree.print());
    }
}