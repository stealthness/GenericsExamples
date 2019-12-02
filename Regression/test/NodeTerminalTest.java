import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTerminalTest {

    private static final double TOL = 0.00000001 ;
    private static final int MAX_RUNS = 10;

    @Test
    void testCreateNodeTerminal(){
        assertEquals(1.0,new NodeTerminal(1.0).calculate(),TOL );
    }

    @Test
    void testCreateNodeTerminalWithRandomInitialValues(){

    }

}