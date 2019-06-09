import org.junit.jupiter.api.Test;

import java.util.Arrays;

class CrossoverTest {



    @Test
    void testCrossoverOnSimpleTrees(){
        Node parentNode0 = TestUtils.onePlusX;
        Node parentNode1 = TestUtils.xNode;

        Node parentNode2= TestUtils.xPlusTwo;
        Node parentNode3 = TestUtils.oneNode;

        Node child0 = GPUtils.mutateIndex1.apply(Arrays.asList(parentNode0,parentNode1),1.0).clone();
        Node child1 = GPUtils.mutateIndex1.apply(Arrays.asList(parentNode2,parentNode3),1.0).clone();

        TestUtils.assertNode(TestUtils.xPlusX,child0);
        TestUtils.assertNode(TestUtils.onePlusTwo,child1);
    }
}
