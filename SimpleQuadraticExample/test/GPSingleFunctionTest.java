import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPSingleFunctionTest {

    @Test
    void apply() {
    }

    @Test
    void toString1() {
        Node identity = new FunctionNode(new GPSingleFunction(GPUtils.identity), new TerminalNode(1.0));
        assertEquals(1.0, ((FunctionNode)identity).get(TestUtils.createRandomInput(1)));
    }
}