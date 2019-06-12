import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    private static final String TESTCASE_FILENAME = "D:\\WS\\Java\\GeneticsAlgorithmsExamples\\SimpleQuadraticExample\\testcases\\ExpPopulationPrint.txt";
    private static final double MAX_RUNS = 10;
    private static final String FULL = "full";
    private List<Node> terminalListE;
    List<Node> terminalList0;
    List<Node> terminalList1;
    List<Node> terminalList0to4;
    List<GPFunction> functionListAddMulti;
    List<GPFunction> functionListSingle;
    Population population;


    @BeforeEach

    void setUp(){
        terminalListE = Arrays.asList(TestUtils.eNode);
        terminalList0 = Arrays.asList(TestUtils.zeroNode);
        terminalList1 = Arrays.asList(TestUtils.oneNode);
        terminalList0to4 = Arrays.asList(TestUtils.zeroNode,TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode,TestUtils.fourNode);
        functionListAddMulti = Arrays.asList(new GPBiFunction(GPUtils.add,"+"),new GPBiFunction(GPUtils.multiply,"*"));
        functionListSingle = Arrays.asList(new GPSingleFunction(GPUtils.abs,"abs"),new GPSingleFunction(GPUtils.reciprocal,"1/x"));
    }

    @Test
    void testCreatPopulation(){
        population = Population.builder().build();
        assertEquals(Population.class, population.getClass());
    }

    @Test
    void generateTreesOfDepth0(){
        for (int i = 0; i < MAX_RUNS;i++){
            int maxPopulationSize = new Random().nextInt(10)+10;
            population = Population.builder()
                                    .maxPopulation(maxPopulationSize)
                                    .terminalNodeList(terminalList0to4)
                                    .build();
            population.initialise();
            assertPopulation(Optional.of(1),Optional.of(0), Optional.of(maxPopulationSize),population);
        }
    }

    @Test
    void generateTreesOfDepth1withGPBiFunction(){
        generateTreeAndTest(functionListAddMulti,terminalList0to4,FULL,1,Optional.of(3),Optional.of(1));
    }
    @Test
    void generateTreesOfDepth2withGPBiFunctions(){
        generateTreeAndTest(functionListAddMulti,terminalList0to4,FULL,2,Optional.of(7),Optional.of(2));
    }
    @Test
    void generateTreesOfDepth3withGPBiFunctions(){
        generateTreeAndTest(functionListAddMulti,terminalList0to4,FULL,3,Optional.of(15),Optional.of(3));
    }

    @Test
    void generateTreesOfDepth1WithGPSingleFunction(){
        generateTreeAndTest(functionListSingle,terminalList0to4,FULL,1,Optional.of(2),Optional.of(1));
    }

    @Test
    void generateTreesOfDepth2WithGPSingleFunction(){
        generateTreeAndTest(functionListSingle,terminalList0to4,FULL,2,Optional.of(3),Optional.of(2));
    }

    @Test
    void generateTreesOfDepth3WithGPSingleFunction(){
        generateTreeAndTest(functionListSingle,terminalList0to4,FULL,3,Optional.of(4),Optional.of(3));
    }

    @Test
    void generateTreesOfDepth0WithEphemeral(){
        Individual individual = Individual.builder().root(terminalListE.get(0)).build();
        System.out.println(individual.toClojureString());
        System.out.println(individual.calculate(new Double[]{2.2}));
        System.out.println(individual.getRoot().toClojureString());

        generateTreeAndTest(functionListSingle,terminalListE,FULL,0,Optional.of(1),Optional.of(0));
    }

    @Test
    void testGetMaxDepthOfPopulation(){
        final int maxPopulation = 5;
        population = Population.builder()
                .maxGenerationDepth(0)
                .maxPopulation(maxPopulation)
                .build();
        List<Individual> newIndividuals = new ArrayList<>();
        for (int i = 0; i < maxPopulation; i++){
            newIndividuals.add(Individual.builder().root(new TerminalNode(1.0)).build());
        }
        population.setIndividuals(newIndividuals);
        assertEquals(maxPopulation,population.size());
        // check largest depth
        int expMaxDepth = 0;
        int expMaxSize = 1;
        assertEquals(expMaxDepth,population.getMaxDepth());
        assertEquals(expMaxSize,population.getMaxSize());
        population.setIndividualAt(0,Individual.builder().root(NodeUtils.createNodeFromString("(+ 1.0 x0)")).build() );

        // check largest depth
        assertEquals(expMaxDepth+1,population.getMaxDepth());
        assertEquals(expMaxSize+2,population.getMaxSize());

    }



    private void generateTreeAndTest(List<GPFunction> functionList, List<Node> terminalList, String generationMethod,
                                     int maxGenerationDepth, Optional<Integer> expSize, Optional<Integer> expDepth){
        for (int i = 0; i < MAX_RUNS;i++){
            int maxPopulationSize = new Random().nextInt(10)+10;
            population = Population.builder()
                    .maxGenerationDepth(maxGenerationDepth)
                    .generationMethod(generationMethod)
                    .maxPopulation(maxPopulationSize)
                    .functionNodeList(functionList)
                    .terminalNodeList(terminalList)
                    .build();
            population.initialise();
            System.out.println(population.print());
            assertPopulation(expSize,expDepth, Optional.of(maxPopulationSize),population);
        }
    }

    private void assertPopulation(Optional<Integer> expSize,Optional<Integer> expDepth, Optional<Integer> expMaxPopulation, Population actPopulation){
        assertEquals(Population.class, population.getClass());
        if (!expMaxPopulation.isEmpty()){
            assertEquals(expMaxPopulation.get(),population.getMaxPopulation(),"Population's individuals size");
        }
        if (!expSize.isEmpty()){
            assertEquals(expSize.get(),actPopulation.getMaxSize(),"Population's max individual size");
        }
        if (!expDepth.isEmpty()){
            assertEquals(expDepth.get(),actPopulation.getMaxDepth(),"Population's max individual depth");
        }
    }


    @Test
    void testPopulationPrint(){
        List<Node> terminalList = Arrays.asList(TestUtils.oneNode);
        population = Population.builder()
                .maxGenerationDepth(0)
                .generationMethod("full")
                .maxPopulation(10)
                .terminalNodeList(terminalList)
                .build();
        population.initialise();
        assertEquals(getTestCase("test003"),population.print());
    }



    @Test
    void testPopulationPrintReadFile(){
        assertEquals("(1.0)\n(1.0)\n(1.0)\n(1.0)\n",getTestCase("test001"));
        assertEquals("(2.0)\n(2.0)\n(2.0)\n(2.0)\n",getTestCase("test002"));
    }


    String getTestCase(String testcase){
        List<String> strings = TestUtils.getTestCase(testcase,TESTCASE_FILENAME,Optional.empty());
        String[] info = strings.get(0).split(",");
        int populationSize= Integer.valueOf(info[2]);
        var sb = new StringBuilder();
        strings.stream().skip(1).forEach(string->{
            sb.append(string).append("\n");
        });
        return sb.toString();
    }


}