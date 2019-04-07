import io.jenetics.*;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionStatistics;

import java.util.stream.Stream;

import static io.jenetics.engine.EvolutionResult.toBestPhenotype;
import static io.jenetics.engine.Limits.bySteadyFitness;

/**
 * Created by Stephen West on 06/04/2019.
 *
 * based on code from https://www.baeldung.com/jenetics
 */
public class KnapsackProblemGA implements Runnable{

    private static final double MUTATOR_PROBABILITY = 0.115;
    private static final double CROSSOVER_PROBABILITY = 0.16;
    private static final int SAMPLE_SIZE = 5;

    public static void main(String[] args) {
        KnapsackProblemGA ga = new KnapsackProblemGA();
        ga.run();
    }


    @Override
    public void run() {
        var nItems = 25;
        double ksCapacity = nItems * 100.0 / 3.0;

        KnapsackFitnessFunction fitnessFunction = new KnapsackFitnessFunction(Stream.generate(KnapsackItem::random)
        .limit(nItems)
        .toArray(KnapsackItem[]::new),ksCapacity);

        Engine<BitGene, Double> engine = Engine.builder(fitnessFunction, BitChromosome.of(nItems, 0.5))
                .populationSize(500)
                .offspringSelector(new RouletteWheelSelector<>())
                .survivorsSelector(new TournamentSelector<>(SAMPLE_SIZE))
                .alterers(new Mutator<>(MUTATOR_PROBABILITY), new SinglePointCrossover<>(CROSSOVER_PROBABILITY))
                .build();

        EvolutionStatistics<Double, ?> statistics = EvolutionStatistics.ofNumber();

        Phenotype<BitGene, Double> best = engine.stream()
                .limit(bySteadyFitness(7))
                .limit(100)
                .peek(statistics)
                .collect(toBestPhenotype());

        System.out.println(statistics);
        System.out.println(best);
    }
}
