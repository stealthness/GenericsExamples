import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimitiveTest {

    Primitive pset;

    @BeforeEach
    void setUp() {
        pset = new Primitive();
    }

    @Test
    void testAddOneTerminal(){
        assertEquals(0,pset.size());
        pset.add(TestUtils.oneNode);
        assertEquals(1,pset.size());
    }
}