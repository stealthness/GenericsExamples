import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FunctionNodeTest {


    private static final double TOL = 0.000001;


    @Test
    void testCreateFunctionNode(){
        Node functionNode = new FunctionNode();
        assertEquals(FunctionNode.class,functionNode.getClass());
        assertTrue(((FunctionNode)functionNode).getSubNode(0).isEmpty());
    }

    @Test
    void testAddSubNode(){

    }


}