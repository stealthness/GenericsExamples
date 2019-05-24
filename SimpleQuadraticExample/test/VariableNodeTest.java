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
        double[] input = new double[]{Math.random()};
        assertEquals(input[0],variableNode.apply(input));
    }

    @Test
    void testMultiVariableApply(){
        int maxSize = 4;
        double[] variableRange = new double[]{-1.0,2.0};
        IntStream.range(0,MAX_RUNS).forEach(runs ->{
            double[] input = createRandomInput(maxSize,variableRange);
            IntStream.range(0,maxSize).forEach(i -> assertVariableNode(input,new VariableNode(i)));
        });
    }

    private void assertVariableNode(double[] actInputs, Node variableNode){
        assertTrue(((VariableNode)variableNode).getIndex() < actInputs.length,
                String.format("Index %n < %n is false",((VariableNode)variableNode).getIndex(),actInputs.length));
        assertEquals(actInputs[((VariableNode) variableNode).getIndex()],variableNode.apply(actInputs));
    }

    private double[] createRandomInput(int size, double[] range){
        double[] inputs = new double[size];
        IntStream.range(0,size).forEach(i -> inputs[i] = Math.random()*(range[0]+range[1])-range[0]);
        return inputs;
    }
}