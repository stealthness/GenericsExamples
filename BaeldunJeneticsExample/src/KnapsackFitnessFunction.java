import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * Created by Stephen West on 07/04/2019.
 *
 *  based on code from https://www.baeldung.com/jenetics
 */
@AllArgsConstructor
public class KnapsackFitnessFunction implements Function<Genotype<BitGene>, Double> {


    private KnapsackItem[] items;
    private double capacity;


    @Override
    public Double apply(Genotype<BitGene> gt) {
        KnapsackItem sum = ((BitChromosome) gt.getChromosome()).ones()
                .mapToObj(i -> items[i])
                .collect(KnapsackItem.toSum());
        return sum.getWeight() <= this.capacity ? sum.value:0;
    }
}
