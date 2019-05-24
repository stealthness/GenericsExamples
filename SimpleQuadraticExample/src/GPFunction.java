import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GPFunction {

    @Builder.Default
    final int maxSubNodes = 2;

    final String clojureString;



    public Double apply(Double[] inputs){
        return null;
    }

}
