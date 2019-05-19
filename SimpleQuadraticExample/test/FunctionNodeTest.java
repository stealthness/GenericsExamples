import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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


    @Test
    void simpleAddLambdaTest(){
        assertEquals(v0+v1, GPUtils.add.apply(v0,v1),TOL);
    }

    @Test
    void simpleSubtractLambdaTest(){
        assertEquals(v0-v1, GPUtils.subtract.apply(v0,v1),TOL);
    }

    @Test
    void simpleMultiplyLambdaTest(){
        assertEquals(v0*v1, GPUtils.multiply.apply(v0,v1),TOL);
    }

    @Test
    void simpleProtectedDivisionLambdaTest(){
        assertEquals(1.0, GPUtils.protectedDivision.apply(3.3,0.0),TOL);
        v1 = 0.0;
        assertEquals(1.2, GPUtils.protectedDivision.apply(3.6,3.0),TOL);
    }


    @Test
    void testNodeWithLambdaAndTwoTerminalNode(){
        var f0 = new FunctionNode(GPUtils.add,t0,t1);
        assertEquals(v0+v1,f0.get(null),TOL);
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
        assertEquals(v0,x0.get(variables),TOL);
        assertEquals(v1,x1.get(variables),TOL);
    }

    @Test
    void testPrintAddFunctionNode(){
        var f0 = new FunctionNode(GPUtils.add);
        assertEquals("(+ null null)",f0.print());
    }

    @Test
    void testExpressionCreation1(){
        // create tree "2.0 + 0.0"

        Node root = new FunctionNode(GPUtils.add, new TerminalNode(2.0), new TerminalNode(0.0));
        IntStream.range(-5,5).forEach(i -> {
            assertEquals(2.0, root.get(new double[]{(double)i}));
        });
        assertEquals("(+ 2.0 0.0)",root.print());

        var fit = d.reduce(0,(a,b) -> a + Math.abs((b*b + b + 1) - root.get(new double[]{b})));
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
            assertEquals(i+1.0, root.get(new double[]{(double)i}),TOL);
        });
        assertEquals("(- (+ x0 1.0) 0.0)",root.print());


        var fit = d.reduce(0,(a,b) -> a + Math.abs((b*b + b + 1) - root.get(new double[]{b})));
        // the known expected fitness as given Field Handbook of GP
        assertEquals(7.7, fit, TOL);
    }

    // Test Depth

    @Test
    void testDepthTerminalAreZero(){
        assertEquals(0,TestUtils.oneTree.getDepth());
        assertEquals(0,TestUtils.twoTree.getDepth());
        assertEquals(0,TestUtils.xTree.getDepth());
    }

    @Test
    void testFunctionsOfDepth1(){
        assertEquals(1,TestUtils.xSqrdTree.getDepth());
        assertEquals(1,TestUtils.xPlus2Tree.getDepth());
        assertEquals(1,TestUtils.oneDivideXTree.getDepth());
    }

    @Test
    void testFunctionsOfDepth2(){
        assertEquals(2,TestUtils.xSqrdPlus1Tree.getDepth());
        System.out.println(TestUtils.xSqrdPlusXPlus1TreeD2.print());
        assertEquals(2,TestUtils.xSqrdPlusXPlus1TreeD2.getDepth());
        assertEquals(2,TestUtils.twoXSqrdTree.getDepth());

    }

    @Test
    void testFunctionsOfDepth3(){
        assertEquals(3,TestUtils.xSqrdPlus1TwiceTree.getDepth());
        assertEquals(3,TestUtils.xSqrdPlusXPlus1TreeD3.getDepth());
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

}