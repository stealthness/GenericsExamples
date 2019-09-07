import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class IndividualTest_GeneratingTrees {

    private static final String GROW = "grow";
    Individual actIndividual;
    List<Node> nodeList;
    List<GPFunction> functionList;

    @Test
    void testGeneratingTreeWithOneNodesOnly(){

        nodeList = Arrays.asList(TestUtils.oneNode);
        actIndividual = Individual.generate(nodeList, null, GROW,0);
        assertIndividual(Optional.empty(),Optional.of(Arrays.asList("(1.0)")), Optional.of(0), Optional.of(1),actIndividual);

    }

    @Test
    void testGeneratingTreeWithVariableNodesOnly(){

        nodeList = Arrays.asList(TestUtils.x0Node);
        actIndividual = Individual.generate(nodeList, null, GROW,0);
        assertIndividual(Optional.empty(),Optional.of(Arrays.asList("(x0)")), Optional.of(0), Optional.of(1),actIndividual);

    }

    @Test
    void testGeneratingTreeWithTerminalNodesOnly(){
        nodeList = List.of(TestUtils.oneNode,TestUtils.twoNode);
        actIndividual = Individual.generate(nodeList, null, GROW,0);
        assertIndividual(Optional.empty(),Optional.of(List.of("(1.0)", "(2.0)")), Optional.of(0), Optional.of(1),actIndividual);
    }


    @Test
    void testIndividualWithSimpleRoot(){
        nodeList = List.of(TestUtils.oneNode,TestUtils.twoNode);
        functionList = List.of(GPUtils.getGPFunction("abs"));
        actIndividual = Individual.generate(nodeList, functionList, GROW,1);
        assertIndividual(Optional.empty(),Optional.of(List.of("(abs 1.0)", "(abs 2.0)", "(1.0)", "(2.0)")), Optional.empty(), Optional.empty(),actIndividual);
    }


    //  private methods

    /**
     *
     * @param expIndividual the expected individual
     * @param expClojureStrings the expected size of the individual's root
     * @param expDepth the expected max depth of the individual's root
     * @param expSize the expected size of the individual's root
     * @param actIndividual the individual being tested
     */
    private void assertIndividual(Optional<Individual> expIndividual, Optional<List<String>> expClojureStrings, Optional<Integer> expDepth, Optional<Integer> expSize,Individual actIndividual ) {

        // TO DO compare Individuals
        if (!expIndividual.isEmpty()){
            try {
                throw new Exception("Test Not written");
            } catch (Exception e) {
                System.out.println("Test Not written");
            }
        }

        if (!expClojureStrings.isEmpty()){

            assertTrue(expClojureStrings.get().stream().anyMatch(strings -> {
                System.out.println(strings);
                System.out.println(actIndividual.toClojureString());
                return strings.contains(actIndividual.toClojureString());
            }), "no matches found");
           //assertEquals(expClojureString.get(),actIndividual.toClojureString(),"ClojureStrings not equal");
        }
        if (!expSize.isEmpty()){
            assertEquals(expSize.get(),actIndividual.size(), "size not equal");
        }
        if (!expDepth.isEmpty()){
            assertEquals(expDepth.get(),actIndividual.getDepth(), "depth not equal");
        }

    }
}
