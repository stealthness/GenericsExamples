import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    private static final String TESTCASE_FILENAME = "testcases\\expPopulationPrint.txt";
    private static final double MAX_RUNS = 10;
    private static final String FULL = "full";
    private List<Node> terminalListE;
    private List<Node> terminalList0to4;
    private List<GPFunction> functionListAddMulti;
    private List<GPFunction> functionListSingle;
    private Population population;


    @BeforeEach

    void setUp(){
        terminalListE = Collections.singletonList(TestUtils.eNode);
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
        population.replaceIndividualAt(1,Individual.builder().root(NodeUtils.createNodeFromString("(+ 1.0 x0)")).build() );
        assertEquals("(1.0)\n(+ 1.0 x0)\n(1.0)\n(1.0)\n(1.0)\n",getTestCase("test004"));
        // check largest depth
        population.replaceIndividualAt(3,Individual.builder().root(NodeUtils.createNodeFromString("(abs (+ 2.0 1.0 0.0))")).build() );
        assertEquals(expMaxDepth+2,population.getIndividualsMaxDepth());
        System.out.println(population.print());
        assertEquals(expMaxSize+4,population.getIndividualsMaxSize());
        assertEquals("(1.0)\n(+ 1.0 x0)\n(1.0)\n(abs (+ 2.0 1.0 0.0))\n(1.0)\n",getTestCase("test005"));

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
                .generationMethod(FULL)
                .maxPopulation(10)
                .terminalNodeList(terminalList)
                .build();
        population.initialise();
        assertEquals(getTestCase("test003"),population.print());
    }



    @Test
    void testPopulationPrintReadFile(){
        assertEquals("(1.0)\n(1.0)\n(1.0)\n(1.0)\n(1.0)\n",getTestCase("test001"));
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