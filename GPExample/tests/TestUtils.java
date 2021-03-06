import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class TestUtils {

    static Node<Double[],Double> negOneNode = new NodeTerminal(-1.0);
    static Node<Double[],Double> zeroNode = new NodeTerminal(0.0);
    static Node<Double[],Double> oneNode = new NodeTerminal(1.0);
    static Node<Double[],Double> twoNode = new NodeTerminal(2.0);


    public static List<String> getTestCase(String testcase, String filePath, Optional<Integer> testLimit) {
        try {
            String[] testInfo = Files.lines(Path.of(filePath))
                    .filter(line -> line.startsWith(testcase))
                    .findFirst()
                    .get()
                    .split(",");
            int testStart = Integer.valueOf(testInfo[1]);
            // set number of line to read in testcase
            if (testLimit.isEmpty()) {
                testLimit = Optional.of(Integer.valueOf(testInfo[2]) + 1);
            }
            return Files.lines(Path.of(filePath))
                    .skip(testStart - 1)
                    .limit(testLimit.get())
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
