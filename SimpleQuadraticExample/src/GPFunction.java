import java.util.List;

interface GPFunction {


    Double apply(Double[] inputs, List<Node> nodes);

    int getMaxSubNodes();

}
