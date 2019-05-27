import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GPBiFunctionTest {

    @Test
    void calculateAddWithTerminalNode(){
        Node addNode = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"), Arrays.asList(TestUtils.oneNode,TestUtils.oneNode));
        assertEquals(FunctionNode.class,addNode.getClass());
        assertEquals(3, addNode.size());
        assertEquals(1,addNode.getDepth());
        assertEquals("(~+ 1.0 1.0)", addNode.print());

        assertFunctionNode(Optional.of(2.0),Optional.of(3),Optional.of(1),Optional.of("(+ 1.0 1.0)"),Optional.empty(), addNode);
    }

    private void assertFunctionNode(Optional<Double> expValue, Optional<Integer> expSize, Optional<Integer>  expDepth, Optional<String>  expClojueString, Optional<Double[]> inputs, Node actNode){
        assertEquals(FunctionNode.class,actNode.getClass());
        assertEquals(expSize.get(), actNode.size());
        assertEquals(expDepth.get(),actNode.getDepth());
        assertEquals(expClojueString,actNode.print());
    }

}