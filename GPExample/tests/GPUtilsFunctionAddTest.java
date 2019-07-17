import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class GPUtilsFunctionAddTest {

    GPFunction<Double[], List<Node<Double[],Double>>, Double> function;

    @BeforeEach
    void setUp(){
        //function = GPUtils.add;
    }

    @Test
    void getClojureStringRepresentation(){
        assertEquals("+",function);
    }
}
