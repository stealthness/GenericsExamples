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

        int count = 0;
        boolean terminationCondition = false;
        while (!terminationCondition){

            List<Individual> newIndividuals = new ArrayList<>();

            /************** REPRODUCTION ********************/
            System.out.println("\n PART 1 - Reproduction ");
            newIndividuals.addAll(population.getReproductionSelection());


            /************** CROSSING ********************/
            System.out.println("\n PART 2 - Crossing");
            newIndividuals.addAll(population.getCrossoverSelection());
            System.out.println(population.printFitness()+"\n");

            //
            System.out.println("BEFORE mutation " +population.size());
            if (newIndividuals.size()< population.getMaxSize()){
                for (int i = 0 ; i < population.getMaxSize() -newIndividuals.size(); i++){
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


            /************** TERMINATION ********************/
            System.out.println(String.format("newIndividuals size %d",newIndividuals.size()));
            System.out.println(String.format("pop size %d",population.size()));
            System.out.println("\n PART 5 - Check termination : generation : " + count++);
            System.out.println("\n\n");

            terminationCondition = count > MAX_RUN || population.isTerminalConditionMet();

            // toClojureString result
            population.evaluate(expSolution);
            System.out.println(population.printFitness());

        }
        return population.getFittest(0);
    }
}
