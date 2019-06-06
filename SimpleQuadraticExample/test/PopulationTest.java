import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    private static final String TESTCASE_FILENAME = "D:\\WS\\Java\\GeneticsAlgorithmsExamples\\SimpleQuadraticExample\\testcases\\ExpPopulationPrint.txt";
    private static final double MAX_RUNS = 10;
    List<Node> terminalList0;
    List<Node> terminalList1;
    List<Node> terminalList0to4;
    List<GPFunction> functionListAddMulti;
    Population population;


    @BeforeEach

    void setUp(){
        terminalList0 = Arrays.asList(TestUtils.zeroNode);
        terminalList1 = Arrays.asList(TestUtils.oneNode);
        terminalList0to4 = Arrays.asList(TestUtils.zeroNode,TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode,TestUtils.fourNode);
        functionListAddMulti = Arrays.asList(new GPBiFunction(GPUtils.addBiFunction,"+"),new GPBiFunction(GPUtils.multiplyBiFunction,"*"));
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
            assertEquals(maxPopulationSize,population.getMaxPopulation());
            assertEquals(Population.class, population.getClass());
            assertPopulation(Optional.of(1),Optional.of(0), population);
        }
    }

    @Test
    void generateTreesOfDepth1(){
        for (int i = 0; i < MAX_RUNS;i++){

            int maxPopulationSize = new Random().nextInt(10)+10;
            population = Population.builder()
                    .maxGenerationDepth(1)
                    .generationMethod("full")
                    .maxPopulation(maxPopulationSize)
                    .functionNodeList(functionListAddMulti)
                    .terminalNodeList(terminalList0to4)
                    .build();
            population.initialise();
            assertEquals(maxPopulationSize,population.getMaxPopulation());
            assertEquals(Population.class, population.getClass());
            assertPopulation(Optional.of(3),Optional.of(1), population);
        }
    }

    @Test
    void generateTreesOfDepth1WithGPSingleFunction(){

        List<Node> terminalList = Arrays.asList(TestUtils.zeroNode,TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode,TestUtils.fourNode);
        List<GPFunction> functionList = Arrays.asList(new GPSingleFunction(GPUtils.abs,"abs"),new GPSingleFunction(GPUtils.reciprocal,"1/x"));
        for (int i = 0; i < MAX_RUNS;i++){

            int maxPopulationSize = new Random().nextInt(10)+10;
            population = Population.builder()
                    .maxGenerationDepth(1)
                    .generationMethod("full")
                    .maxPopulation(maxPopulationSize)
                    .functionNodeList(functionList)
                    .terminalNodeList(terminalList)
                    .build();
            population.initialise();
            assertEquals(maxPopulationSize,population.getMaxPopulation());
            assertEquals(Population.class, population.getClass());
            assertPopulation(Optional.of(2),Optional.of(1), population);
        }
    }

    private void assertPopulation(Optional<Integer> expSize,Optional<Integer> expDepth, Population actPopulation){

        if (!expSize.isEmpty()){
            assertEquals(expSize.get(),actPopulation.getMaxSize());
        }
        if (!expDepth.isEmpty()){
            assertEquals(expDepth.get(),actPopulation.getMaxDepth());
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
        try {
            String[] testInfo = Files.lines(Path.of(TESTCASE_FILENAME)).filter(line -> line.startsWith(testcase)).findFirst().get().split(",");
            int testStart = Integer.valueOf(testInfo[1]);
            int testSize = Integer.valueOf(testInfo[2]);
            var sb = new StringBuilder();
            Files.lines(Path.of(TESTCASE_FILENAME)).skip(testStart).limit(testSize).forEach(line -> sb.append(line+"\n"));
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "test case not found";
    }


}