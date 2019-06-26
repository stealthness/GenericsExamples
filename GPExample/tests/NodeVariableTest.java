import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class NodeVariableTest {

    private static final int MAX_RUNS = 100;


    @Test
    void testVariableCreate(){
        Node<Double[], Double> NodeVariable = new NodeVariable(0);
        assertEquals(NodeVariable.class, NodeVariable.getClass());
    }

    @Test
    void testSingleVariableApply(){
        Node<Double[], Double> NodeVariable = new NodeVariable(0);
        Double[] input = new Double[]{Math.random()};
        assertEquals(input[0],NodeVariable.calculate(input));
    }

    @Test
    void testMultiVariableApply(){
        int maxSize = 4;
        Double[] variableRange = new Double[]{-1.0,2.0};
        IntStream.range(0,MAX_RUNS).forEach(runs ->{
            Double[] input = createRandomInput(maxSize,variableRange);
            IntStream.range(0,maxSize).forEach(i -> assertNodeVariable(input,new NodeVariable(i)));
        });
    }

    private void assertNodeVariable(Double[] actInputs, Node<Double[], Double> NodeVariable){
        assertTrue(((NodeVariable)NodeVariable).getIndex() < actInputs.length,
                String.format("Index %d < %d is false",((NodeVariable)NodeVariable).getIndex(),actInputs.length));
        assertEquals(actInputs[((NodeVariable) NodeVariable).getIndex()],NodeVariable.calculate(actInputs));
    }

    private Double[] createRandomInput(int size, Double[] range){
        Double[] inputs = new Double[size];
        IntStream.range(0,size).forEach(i -> inputs[i] = Math.random()*(range[0]+range[1])-range[0]);
        return inputs;
    }

    @Test
    void testPrintNodeVariable(){
        Node<Double[], Double> NodeVariable = new NodeVariable(0);
        assertEquals("x0",NodeVariable.toClojureString());
        IntStream.range(0,10).forEach(this::assertPrintNodeVariable);

    }

    private void assertPrintNodeVariable(int index){
        Node NodeVariable = new NodeVariable(index);
        assertEquals("x"+index,NodeVariable.toClojureString());
    }

    @Test
    void testClone(){
        Node NodeVariable = new NodeVariable(0);
        Node copyNode = NodeVariable;
        // shows that copied reference
        assertEquals(System.identityHashCode(copyNode),System.identityHashCode(NodeVariable));
        assertEquals(copyNode,NodeVariable);

        Node clonedNode = NodeVariable.clone();
        assertNotEquals(System.identityHashCode(clonedNode),System.identityHashCode(NodeVariable));
        assertNotEquals(System.identityHashCode(clonedNode),System.identityHashCode(copyNode));
        // lombok equals will see them as equal
        assertEquals(clonedNode,copyNode);
        assertEquals(clonedNode,NodeVariable);

        // change ref to NodeVariable
        NodeVariable = new NodeVariable(1);
        assertNotEquals(System.identityHashCode(copyNode),System.identityHashCode(NodeVariable));
        assertNotEquals(NodeVariable, copyNode);
        assertNotEquals(NodeVariable, clonedNode);


    }


}