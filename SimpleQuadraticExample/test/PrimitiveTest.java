import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveSetTest {

    PrimitiveSet pset;

    @BeforeEach
    void setUp() {
        pset = new PrimitiveSet();
    }

    @Test
    void testAddOneTerminal(){
        assertSetCount(0,pset, Optional.of(pset.toString()));
        pset.add(TestUtils.oneNode);
        assertSetCount(1,pset, Optional.of(pset.toString()));
    }

    @Test
    void testAddX0Node(){
        pset.add(TestUtils.xNode);
        assertSetCount(1,pset, Optional.of(pset.toString()));
    }

    @Test
    void testAddOneToString(){
        pset.add((TestUtils.oneNode));
        assertEquals("TerminalNode(value=1.0)\n",pset.toString());
    }

    @Test
    void testAddX0ToString(){
        pset.add((TestUtils.x0Node));
        assertEquals("VariableNode(index=0)\n",pset.toString());
    }

    @Test
    void testAddingMultipleTerminalNodes(){
        String expString = "TerminalNode(value=1.0)\nTerminalNode(value=1.0)\nVariableNode(index=0)\n";
        pset.add(TestUtils.oneNode);
        pset.add(TestUtils.oneNode);
        pset.add(TestUtils.x0Node);
        assertSetCount(3,pset, Optional.of("1, 1, x0"));
        assertEquals(expString,pset.toString());
    }


    @Test
    void testAddFunctionNode(){
        pset.add(GPUtils.getGPFunction("add"));
        assertSetCount(1,pset, Optional.of("Add node"));
        assertEquals("GPMultiFunction(add)\n",pset.toString());
    }

    @Test
    void testABSFunctionNode(){
        pset.add(GPUtils.getGPFunction("abs"));
        assertSetCount(1,pset, Optional.of("abs node"));
        assertEquals("GPSingleFunction(abs)\n",pset.toString());
    }



    @Test
    void testAddingMultipleFunctionNodes(){
        String expString = "TerminalNode(value=1.0)\nTerminalNode(value=1.0)\nVariableNode(index=0)\nGPMultiFunction(add)\nGPMultiFunction(add)\nGPSingleFunction(abs)\n";
        pset.add(TestUtils.oneNode);
        pset.add(TestUtils.oneNode);
        pset.add(GPUtils.getGPFunction("add"));
        pset.add(GPUtils.getGPFunction("add"));
        pset.add(TestUtils.x0Node);
        pset.add(GPUtils.getGPFunction("abs"));
        assertSetCount(6,pset, Optional.of("add, add, 1, 1 abs, 0"));
        assertEquals(expString,pset.toString());
    }

    @Test
    void testAddingMultipleNodes(){
        String expString = "GPMultiFunction(add)\nGPMultiFunction(add)\nGPSingleFunction(abs)\n";
        pset.add(GPUtils.getGPFunction("add"));
        pset.add(GPUtils.getGPFunction("add"));
        pset.add(GPUtils.getGPFunction("abs"));
        assertSetCount(3,pset, Optional.of("add, add, abs"));
        assertEquals(expString,pset.toString());
    }


    // Static Assert Methods

    static private void assertSetCount(int expCount, PrimitiveSet actSet, Optional<String> msg){
        assertEquals(expCount,actSet.size(),msg.orElse("No message"));
    }
}