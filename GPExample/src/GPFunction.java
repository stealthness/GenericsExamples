import java.util.List;

interface GPFunction<T,N> {

    Double apply(T[] inputs, List<N> nodes);

    String toClojureString();

    int getMaxSubNodes();

}
