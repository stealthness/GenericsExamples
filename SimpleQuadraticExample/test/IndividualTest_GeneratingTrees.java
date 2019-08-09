import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class IndividualTest_GeneratingTrees {

    private static final String GROW = "GROW";
    Individual actIndividual;
    List<Node> nodeList;
    List<GPFunction> functionList;

    @Test
    void testGeneratingTreeWithTerminalNodesOnly(){

        nodeList = Arrays.asList(TestUtils.oneNode);
        actIndividual = Individual.generate(nodeList, null, GROW,0);

        assertIndividual(Optional.empty(),Optional.of("(1.0)"), Optional.of(0), Optional.of(1),actIndividual);

    }

    private void assertIndividual(Optional<Individual> expIndividual, Optional<String> expClojureString, Optional<Integer> expDepth, Optional<Integer> expSize,Individual actIndividual ) {

        // TO DO compare Individuals
        if (!expIndividual.isEmpty()){
            try {
                throw new Exception("Test Not written");
            } catch (Exception e) {
                System.out.println("Test Not written");
            }
        }

        if (!expClojureString.isEmpty()){
            assertEquals(expClojureString.get(),actIndividual.toClojureString(),"ClojureStrings not equal");
        }
        if (!expSize.isEmpty()){
            assertEquals(expSize.get(),actIndividual.size(), "size not equal");
        }
        if (!expDepth.isEmpty()){
            assertEquals(expDepth.get(),actIndividual.getDepth(), "depth not equal");
        }

    }
}
