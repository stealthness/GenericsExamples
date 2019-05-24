import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 50;
    private static final int MAX_SIZE = 100;
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){


        population = Population.builder()
                .build();
        int count = 0;

        boolean terminationCondition = false;

        while (!terminationCondition){

            List<Individual> newIndividuals = new ArrayList<>();

            /************** REPRODUCTION ********************/
            System.out.println("\n PART 1 - Reproduction ");
            /************** CROSSING ********************/
            System.out.println("\n PART 2 - Crossing");
            System.out.println("\n PART 3 - Mutations ");
            System.out.println("\n PART 4 - Reduce");
            System.out.println("\n PART 5 - Check termination : generation : "+count);

            System.out.println("\n\n");
        }

        // print result

    }
}
