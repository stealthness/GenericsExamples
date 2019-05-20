import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GP {

    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){
        population = Population.builder()
                .maxSize(4)
                .build();
        population.generate("full");

        population.getIndividuals().stream().forEach(individual -> System.out.println(individual.print()));

    }
}
