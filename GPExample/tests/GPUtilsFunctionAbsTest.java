import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPUtilsFunctionAbsTest {


    private static final double TOL = 0.00001;
    private static final int MAX_RUNS = 100;

    @Test
    void testApplyAndGetAbsOnOneNode() {
        List<Node<Double[], Double>> nodeList = Collections.singletonList(TestUtils.oneNode);
        Double[] inputs = new Double[0];
        testApply(nodeList, inputs, 1.0);
    }

    @Test
    void testApplyAndGetAbsOnNegOneNode() {
        List<Node<Double[], Double>> nodeList = Collections.singletonList(TestUtils.negOneNode);
        Double[] inputs = new Double[0];
        testApply(nodeList, inputs, 1.0);
    }

    @Test
    void testApplyAndGetAbsOnNegRandomNode() {
        IntStream.range(0,MAX_RUNS).forEach(i -> {
            Double actValue = Math.random();
            List<Node<Double[], Double>> nodeList = Collections.singletonList(new NodeTerminal(-actValue));
            Double[] inputs = new Double[0];
            testApply(nodeList, inputs, actValue);
        });


    }

    private void testApply(List<Node<Double[], Double>> nodeList, Double[] inputs, double expValue) {
        assertEquals(expValue,GPUtils.abs.apply(inputs,nodeList),TOL);
    }
}
