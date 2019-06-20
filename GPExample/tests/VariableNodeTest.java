import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class VariableNodeTest {

    private static final int MAX_RUNS = 100;
    private static final double LOWER_RANGE_LIMIT = -1.0;
    private static final double UPPER_RANGE_LIMIT = 2.0;
    private static final int VECTOR_SIZE_LIMIT = 10;


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
        Double[] variableRange = new Double[]{LOWER_RANGE_LIMIT, UPPER_RANGE_LIMIT};
        IntStream.range(0,MAX_RUNS).forEach(runs ->{
            Double[] input = createRandomInput(VECTOR_SIZE_LIMIT,variableRange);
            IntStream.range(0,VECTOR_SIZE_LIMIT).forEach(i -> assertVariableNodeCalculate(input ,new VariableNode(i)));
        });
    }

    @Test
    void testToClojureStringVariableNode(){
        IntStream.range(0, VECTOR_SIZE_LIMIT).forEach(index -> assertClojureString(index, new VariableNode(index)));
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

    // Private methods


    /**
     *  asserts that a variable node with a given inputs returns the correct value of the input
     */
    private void assertVariableNodeCalculate(Double[] actInputs, Node variableNode){
        String msg = String.format("Index %d < %d is false",((VariableNode)variableNode).getIndex(),actInputs.length);
        int index = ((VariableNode)variableNode).getIndex();
        assertTrue(index  < actInputs.length, msg);
        assertEquals(actInputs[index], variableNode.calculate(actInputs));
    }

    private void assertClojureString(int index, Node variableNode){
        assertEquals("x"+index,((VariableNode)variableNode).toClojureString());
    }

    /**
     * Returns a vector of random double values within range given
     * @param size the vector size of the inputs.
     * @param range the range of the values
     * @return A random input
     */
    private Double[] createRandomInput(int size, Double[] range){
        Double[] input = new Double[size];
        IntStream.range(0,size).forEach(i -> input[i] = Math.random()*(range[0]+range[1])-range[0]);
        return input;
    }


}