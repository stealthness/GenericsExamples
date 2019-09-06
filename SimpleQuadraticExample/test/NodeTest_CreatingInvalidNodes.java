import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class NodeTest_CreatingInvalidNodes {

    @Test
    void testThatEmptyStringCreatesException(){
        var actString = " ";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var  expNode = NodeUtils.createNodeFromString(actString);
        });
    }

    @Test
    void testThatStringWithImbalanceBracketsIsInvalid(){
        var actString = "(+ 1.0 2.0)) ";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            var  expNode = NodeUtils.createNodeFromString(actString);
        });
    }
}
