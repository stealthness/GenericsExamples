import io.jenetics.BitChromosome;
import io.jenetics.BitGene;
import io.jenetics.Genotype;
import io.jenetics.engine.Engine;
import io.jenetics.engine.EvolutionResult;
import io.jenetics.util.Factory;
import lombok.AllArgsConstructor;
import java.util.function.Function;
import java.util.stream.Collector;

/**
 * Created by Stephen West on 06/04/2019.
 *
 * based on code from https://www.baeldung.com/jenetics
 */
public class SimpleAllOnesProblem implements Runnable{

    public static void main(String[] args) {
        var ks = new SimpleAllOnesProblem();
        ks.run();
    }



    @Override
    public void run() {
        System.out.println("running");

        Factory<Genotype<BitGene>> gtf = Genotype.of((BitChromosome.of(50,0.5)));
        System.out.println("before the evolution: \n" +gtf);

        Engine<BitGene,Integer> engine = Engine.builder(SimpleAllOnesProblem::eval,gtf).build();

        printEngineDetails(engine);


        Genotype<BitGene> result = engine.stream().limit(500).collect(EvolutionResult.toBestGenotype());
        printEngineDetails(engine);
        System.out.println("after the evolution: \n"+result.toString());
    }

    private void printEngineDetails(Engine engine){
        System.out.println(engine.getSurvivorsCount()+ " : engine.getSurvivorsCount()");
        System.out.println(engine.getOffspringCount()+ " : engine.getOffspringCount()");
        System.out.println(engine.getIndividualCreationRetries()+ " : engine.getIndividualCreationRetries()");
    }


    private static Integer eval(Genotype<BitGene> gt) {
        return gt.getChromosome()
                .as(BitChromosome.class)
                .bitCount();
    }
}


/**
 * Item class
 */
@AllArgsConstructor
final class Item{

    public final double weight;
    public final double value;


    public static Collector<Item,?,Item> toSum() {
        return Collector.of(
                // map on too pair double in array of to
                () -> new double[2],
                // fold them into reduce then to sum of weight and value
                (a, b) -> {a[0] += b.weight; a[1] += b.value;},
                (a, b) -> {a[0] += b[0]; a[1] += b[1]; return a;},
                //map on to a new item that is sum of the weight and value
                r -> new Item(r[0], r[1]) );
    }
}

@AllArgsConstructor
final class FitnessFunction implements Function<Genotype<BitGene>,Double>{

    private final Item[] items;
    private final double capacity;


    @Override
    public Double apply(Genotype<BitGene> gt) {
        final Item sum = ((BitChromosome)gt.getChromosome())
                // filter all the 1 genes
                .ones()
                // map to the items
                .mapToObj(i -> items[i])
                // collect them apply sum
                .collect(Item.toSum());
        // provide the weight is under knapsack size it is returned, otherwise 0
        return sum.weight <= this.capacity?sum.value:0;
    }
}