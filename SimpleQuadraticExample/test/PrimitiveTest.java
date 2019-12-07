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
        assertEquals("TerminalNode(value=1.0)",pset.toString());
    }

    @Test
    void testAddX0ToString(){
        pset.add((TestUtils.x0Node));
        assertEquals("VariableNode(index=0)",pset.toString());
    }

    // Static Assert Methods

    static private void assertSetCount(int expCount, PrimitiveSet actSet, Optional<String> msg){
        assertEquals(expCount,actSet.size(),msg.orElse("No message"));
    }
}