import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 10;
    private static final int MAX_SIZE = 10;
    private static final String FULL = "full";
    Node expFunction;
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){
        expFunction = NodeUtils.createNodeFromString("(+ (* x0 x0) (+ 1.0 x0))");
        Optional<Individual> solution = findSolution(expFunction);
        if (solution.isEmpty()){
            System.out.println("no Solution found");
        }else{
            System.out.println(String.format("Solution found : %s",solution.get().toClojureString()));
        }

    }

    private Optional<Individual> findSolution(Node expSolution){

        List<Node> terminalList = Arrays.asList(new VariableNode(0),new VariableNode(0),
                new VariableNode(0),new VariableNode(0),new VariableNode(0),
                new TerminalNode(-1.0),new TerminalNode(0.0),new TerminalNode(1.0),
                new TerminalNode(2.0),new TerminalNode(3.0),new TerminalNode(4.0));
        List<GPFunction> functionList = Arrays.asList(new GPBiFunction(GPUtils.add,"+"),
                new GPBiFunction(GPUtils.multiply,"*"),//new GPBiFunction(GPUtils.abs,"abs"),
                new GPBiFunction(GPUtils.subtract,"-"),new GPBiFunction(GPUtils.divide,"/"));

        population = Population.builder()
                .maxPopulation(MAX_SIZE)
                .maxGenerationDepth(1)
                .generationMethod("grow")
                .terminalNodeList(terminalList)
                .functionNodeList(functionList)
                .build();

        population.initialise();
        population.evaluate(expSolution);
        System.out.println(population.printFitness());

        // popSize,maxDepth,MaxSize,BestFitness
        double[][] details = new double[MAX_RUN][4];

        int generation = 0;
        boolean terminationCondition = false;
        while (!terminationCondition){

            List<Individual> newIndividuals = new ArrayList<>();

            /************** REPRODUCTION ********************/
            System.out.println("\n PART 1 - Reproduction ");
            newIndividuals.addAll(population.getReproductionSelection());


            /************** CROSSING ********************/
            System.out.println("\n PART 2 - Crossing");
            newIndividuals.addAll(population.getCrossoverSelection());


            population.setIndividuals(newIndividuals);
            population.evaluate(expSolution);
            System.out.println(population.print());


            //
            System.out.println("BEFORE mutation " +population.size());
            if (newIndividuals.size()< population.getMaxSize()){
                for (int i = 0 ; i < population.getMaxSize() - newIndividuals.size(); i++){
                    newIndividuals.add(Individual.generate(terminalList,functionList,"grow",2));
                }
            }else if (newIndividuals.size()>population.getMaxSize()){
                newIndividuals = newIndividuals.stream().limit(population.getMaxSize()).collect(Collectors.toList());
            }
            System.out.println("BEFORE mutation " +population.size());
            population.setIndividuals(newIndividuals);
            population.evaluate(expSolution);

            /************** MUTATING ********************/
            System.out.println("\n PART 3 - Mutations ");
            newIndividuals.addAll(population.mutate());
            population.evaluate(expSolution);



            population.setIndividuals(newIndividuals);
            population.evaluate(expSolution);

            /************** EDITING ********************/
            System.out.println("\n PART 4 - edit");
//            newIndividuals.addAll(population.edit());



            // toClojureString result
            population.evaluate(expSolution);
            System.out.println(population.printFitness());
            details[generation][0]=population.size();
            details[generation][1]=population.getMaxSize();
            details[generation][2]=population.getMaxDepth();
            details[generation][3]=population.getFittest(0).get().getFitness();

            /************** TERMINATION ********************/
            System.out.println(String.format("newIndividuals size %d",newIndividuals.size()));
            printDetail(details[generation]);
            System.out.println("\n PART 5 - Check termination : generation : " + generation);
            System.out.println("\n\n");

            terminationCondition = generation++ > MAX_RUN-5 || population.isTerminalConditionMet();

        }
        printDetails(details);
        return population.getFittest(0);
    }

    private void printDetails(double[][] details) {
        Arrays.stream(details).forEach(this::printDetail);
    }

    private void printDetail(double[] detail) {
        System.out.println(String.format("Population Size %2d :  MaxSize %2d  :  MaxDepth  %2d  :  Best Fitness %4f",
                (int) detail[0], (int) detail[1], (int) detail[2], detail[3]));
    }
}
