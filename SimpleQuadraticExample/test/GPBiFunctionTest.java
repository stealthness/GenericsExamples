import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GPBiFunctionTest {

    String testID;
    Optional<Double> expValue;
    Optional<Double> expSubNode0;
    Optional<Double> expSubNode1;
    Optional<Integer> expSize;
    Optional<Integer> expDepth;
    Optional<GPBiFunction> expFunction;
    Optional<String> expClojureString;
    Optional<String> expToString;
    Node actNode;

    private static final String TESTCASE_FILENAME = "D:\\WS\\Java\\GeneticsAlgorithmsExamples\\SimpleQuadraticExample\\testcases\\GPBiFunctionTestCases.csv";
    List<String> testCases;


    @BeforeEach
    void begin(){
        try {
            testCases = Files.readAllLines(Paths.get(TESTCASE_FILENAME));
        } catch (IOException e) {
            new IOException("File not found");
        }
    }

    @Test
    void testAddCalculateAddWith2TerminalNodes(){
        Node addNode = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"), Arrays.asList(TestUtils.oneNode,TestUtils.oneNode));
        assertFunctionNode(Optional.of(2.0),Optional.of(3),Optional.of(1),Optional.of("(+ 1.0 1.0)"),Optional.empty(), addNode);
    }

    @Test
    void testAddCalculateWith1TerminalNodeAnd1VariableNode(){
        Node addNode = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"), Arrays.asList(TestUtils.oneNode,TestUtils.xNode));
        assertFunctionNode(Optional.of(2.0),Optional.of(3),Optional.of(1),Optional.of("(+ 1.0 x0)"),Optional.of(new Double[]{1.0}), addNode);
    }

    @Test
    void testMultipleCalculateWith2TerminalNodes(){
        Node multipleNode = new FunctionNode(new GPBiFunction(GPUtils.multiplyBiFunction,"+"), Arrays.asList(TestUtils.oneNode,TestUtils.twoNode));
        assertFunctionNode(Optional.of(2.0),Optional.of(3),Optional.of(1),Optional.of("(+ 1.0 2.0)"),Optional.empty(), multipleNode);
    }

    @Test
    void testMultipleCalculateWith1TerminalNodeAnd1VariableNode(){
        Node multipleNode = new FunctionNode(new GPBiFunction(GPUtils.multiplyBiFunction,"*"), Arrays.asList(TestUtils.oneNode,TestUtils.xNode));
        assertFunctionNode(Optional.of(2.0),Optional.of(3),Optional.of(1),Optional.of("(* 1.0 x0)"),Optional.of(new Double[]{2.0}), multipleNode);
    }

    @Test
    void testCalculateWith2VariableNodes(){
        Node addNode = new FunctionNode(new GPBiFunction(GPUtils.multiplyBiFunction,"*"), Arrays.asList(TestUtils.xNode,TestUtils.xNode));
        assertFunctionNode(Optional.of(4.0),Optional.of(3),Optional.of(1),Optional.of("(* x0 x0)"),Optional.of(new Double[]{2.0}), addNode);
    }



    // helper method

    private void assertFunctionNode(Optional<Double> expValue, Optional<Integer> expSize, Optional<Integer>  expDepth, Optional<String>  expClojureString, Optional<Double[]> inputs, Node actNode){
        assertEquals(FunctionNode.class,actNode.getClass());
        assertEquals(expSize.get(), actNode.size());
        assertEquals(expDepth.get(),actNode.getDepth());
        assertEquals(expClojureString.get(),actNode.print());
        assertEquals(expValue.get(),actNode.calculate(inputs.orElse(null)));
    }



}