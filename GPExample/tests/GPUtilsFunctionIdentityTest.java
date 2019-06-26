import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPUtilsFunctionIdentityTest {


    private static final double TOL = 0.00001;

    @Test
    void testApplyAndGetIdentityOnOneNode() {
        List<Node<Double[], Double>> nodeList = Collections.singletonList(TestUtils.oneNode);
        Double[] inputs = new Double[0];
        testApply(nodeList, inputs, 1.0);
    }

    private void testApply(List<Node<Double[], Double>> nodeList, Double[] inputs, double expValue) {
        assertEquals(expValue,GPUtils.identity.apply(inputs,nodeList),TOL);
    }
}
