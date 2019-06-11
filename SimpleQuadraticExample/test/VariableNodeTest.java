
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class VariableNodeTest {

    private static final int MAX_RUNS = 100;


    @Test
    void testVariableCreate(){
        Node variableNode = new VariableNode(0);
        assertEquals(VariableNode.class, variableNode.getClass());
    }

    @Test
    void testSingleVariableApply(){
        Node variableNode = new VariableNode(0);
        Double[] input = new Double[]{Math.random()};
        assertEquals(input[0],variableNode.calculate(input));
    }

    @Test
    void testMultiVariableApply(){
        int maxSize = 4;
        Double[] variableRange = new Double[]{-1.0,2.0};
        IntStream.range(0,MAX_RUNS).forEach(runs ->{
            Double[] input = createRandomInput(maxSize,variableRange);
            IntStream.range(0,maxSize).forEach(i -> assertVariableNode(input,new VariableNode(i)));
        });
    }

    private void assertVariableNode(Double[] actInputs, Node variableNode){
        assertTrue(((VariableNode)variableNode).getIndex() < actInputs.length,
                String.format("Index %n < %n is false",((VariableNode)variableNode).getIndex(),actInputs.length));
        assertEquals(actInputs[((VariableNode) variableNode).getIndex()],variableNode.calculate(actInputs));
    }

    private Double[] createRandomInput(int size, Double[] range){
        Double[] inputs = new Double[size];
        IntStream.range(0,size).forEach(i -> inputs[i] = Math.random()*(range[0]+range[1])-range[0]);
        return inputs;
    }

    @Test
    void testPrintVariableNode(){
        Node variableNode = new VariableNode(0);
        assertEquals("x0",variableNode.toClojureString());
        IntStream.range(0,10).forEach(this::assertPrintVariableNode);

    }

    private void assertPrintVariableNode(int index){
        Node variableNode = new VariableNode(index);
        assertEquals("x"+index,variableNode.toClojureString());
    }

    @Test
    void testClone(){
        Node variableNode = new VariableNode(0);
        Node copyNode = variableNode;
        // shows that copied reference
        assertEquals(System.identityHashCode(copyNode),System.identityHashCode(variableNode));
        assertEquals(copyNode,variableNode);

        Node clonedNode = variableNode.clone();
        assertNotEquals(System.identityHashCode(clonedNode),System.identityHashCode(variableNode));
        assertNotEquals(System.identityHashCode(clonedNode),System.identityHashCode(copyNode));
        // lombok equals will see them as equal
        assertEquals(clonedNode,copyNode);
        assertEquals(clonedNode,variableNode);

        // change ref to variableNode
        variableNode = new VariableNode(1);
        assertNotEquals(System.identityHashCode(copyNode),System.identityHashCode(variableNode));
        assertNotEquals(variableNode, copyNode);
        assertNotEquals(variableNode, clonedNode);


    }


}