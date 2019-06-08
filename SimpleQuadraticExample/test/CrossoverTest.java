import org.junit.jupiter.api.Test;

import java.util.Arrays;

public class CrossoverTest {



    @Test
    void testCrossoverOnSimpleTrees(){
        Node parentNode0 = TestUtils.onePlusX;
        Node parentNode1= TestUtils.xPlusTwo;
        Node parentNode2 = TestUtils.oneNode;
        Node parentNode3 = TestUtils.xPlusX;
        Node parentNode4 = TestUtils.xNode;

        Node child0 = GPUtils.mutateIndex1.apply(Arrays.asList(parentNode0,parentNode2),1.0).clone();
        Node child1 = GPUtils.mutateIndex1.apply(Arrays.asList(parentNode0,parentNode4),1.0).clone();

        TestUtils.assertNode(TestUtils.onePlusX,child0);
        TestUtils.assertNode(parentNode3,child1);
    }
}
