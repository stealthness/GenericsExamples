import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NodeTerminal {

    final Double value;

    public Double calculate() {
        return value;
    }
}
