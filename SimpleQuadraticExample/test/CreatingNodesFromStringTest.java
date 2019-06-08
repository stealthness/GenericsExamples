import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Purpose of this class is to test creating Nodes from strings
 */
public class CreatingNodesFromStringTest {


    @Test
    void testTreesOfSize1(){
        var actStrings = Arrays.asList("(0.0)","(1.0)","(2.0)","(3.0)","(4.0)","(x0)","(x1)");
        var expNodes = Arrays.asList(TestUtils.zeroNode,TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode,
                TestUtils.fourNode,TestUtils.x0Node,TestUtils.x1Node);

        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize2(){
        var actStrings = Arrays.asList("(abs 0.0)","(abs 1.0)","(abs 2.0)","(abs 3.0)","(abs 4.0)","(abs x0)","(abs x1)");
        var expNodes = Arrays.asList(TestUtils.absZeronNode,TestUtils.absOneNode,TestUtils.absTwoNode,TestUtils.absThreeNode,
                TestUtils.absFourNode,TestUtils.absX0Node,TestUtils.absX1Node);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize3Depth1(){
        var actStrings = Arrays.asList("(abs 0.0 1.0)","(abs 1.0 2.0)","(abs x0 1.0)","(abs 2.0 x1)");
        var expNodes = Arrays.asList(TestUtils.absZeroOneNode,TestUtils.absOneTwoNode,TestUtils.absX0OneNode,TestUtils.absTwoX1Node);
        assertNodesFromStrings(actStrings, expNodes);
    }




    private void assertNodesFromStrings(List<String> actStrings, List<Node> expNodes) {
        for (int i = 0 ; i< actStrings.size() ; i++){
            if (expNodes.get(0).size() == 1){ // is TerminalNode or VariableNode
                assertEquals(expNodes.get(i).print(),actStrings.get(i).replace("("," ").replace(")"," ").strip());
            }else{  // Node is FunctionNode
                assertEquals(expNodes.get(i).print(),actStrings.get(i));
            }
        }
        for (int i = 0 ; i< actStrings.size() ; i++){
            Node actNode = createNodeFromString(actStrings.get(i));
            System.out.println((actNode==null)?"null":actNode.print() + "  :  " + expNodes.get(i).print()) ;
            TestUtils.assertNode(expNodes.get(i),actNode);
            assertEquals(expNodes.get(i).print(),actNode.print());
        }
    }

    private Node createNodeFromString(String string){
        List<String> strings = Arrays.asList(string.split(" "));
        if (strings.size()==1){
            return getTerminalNode(strings.get(0));
        } else if (strings.size() == 2){
            Node subNode = getTerminalNode(strings.get(1));
            return new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"),Arrays.asList(subNode));
        } else {
            if (false){
                // replace later with check brackets
            } else{
                List subNodes = new ArrayList();
                for (int i = 1 ; i< strings.size() ; i++){
                    subNodes.add(getTerminalNode(strings.get(i)));
                }
                return new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"),subNodes);
            }

        }
        return null;
    }

    private Node getTerminalNode(String string) {
        final String strip = string.replace("(", " ").replace(")", " ").strip();
        if (strip.startsWith("x")){
            return new VariableNode(Integer.valueOf(string.substring(1,2)));
        }else{
            return new TerminalNode(Double.valueOf(strip));
        }
    }
}
