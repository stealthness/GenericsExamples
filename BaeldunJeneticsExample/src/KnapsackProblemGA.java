import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;

/**
 * Created by Stephen West on 06/04/2019.
 */
public class KnapsackProblemGA {


    public static Integer eval(Genotype<BitGene> gt) {
        return gt.getChromosome().as(BitChromosome.class).bitCount();
    }
}
