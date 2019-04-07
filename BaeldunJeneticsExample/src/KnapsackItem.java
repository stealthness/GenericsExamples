import io.jenetics.util.RandomRegistry;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Random;
import java.util.stream.Collector;

/**
 * Created by Stephen West on 07/04/2019.
 *
 * based on code from https://www.baeldung.com/jenetics
 */
@Data
@AllArgsConstructor
public class KnapsackItem {

    public double weight;
    public double value;


    protected static KnapsackItem random(){
        Random r = RandomRegistry.getRandom();
        return new KnapsackItem(r.nextDouble()*100,r.nextDouble()*100);
    }

    protected static Collector<KnapsackItem,?,KnapsackItem> toSum(){
        return Collector.of(() -> new double[2], (a,b) ->{
            a[0] += b.weight;
            a[1] += b.value;
        } , (a, b) -> {
            a[0] += b[0];
            a[1] += b[1];
            return a;
        } , r -> new KnapsackItem(r[0], r[1]));
    }
}
