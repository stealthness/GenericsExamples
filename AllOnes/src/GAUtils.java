import java.util.function.Function;

/**
 * Created by Stephen West on 20/03/2019.
 */
public class GAUtils {

    static Function<Individual, Double> sillyFirstGeneFitness = individual -> (double)individual.getGene(0);


    static Function<Individual, Double> sillyLastGeneFitness = individual -> (double)individual.getGene(0);
}
