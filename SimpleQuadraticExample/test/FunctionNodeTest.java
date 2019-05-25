import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FunctionNodeTest {


    private static final double TOL = 0.000001;


    @Test
    void testCreateFunctionNode(){
        Node functionNode = new FunctionNode(new GPSingleFunction(GPUtils.identity), TestUtils.oneNode);
        assertEquals(FunctionNode.class,functionNode.getClass());
    }

    @Test
    void testAddSubNode(){

    }


    @Test
    void setSubNode() {
    }
}