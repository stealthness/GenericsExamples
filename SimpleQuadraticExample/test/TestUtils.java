import java.util.stream.IntStream;

class TestUtils {


    public static Node ozeroNode = new TerminalNode(0.0);
    public static Node oneNode = new TerminalNode(1.0);
    public static Node twoNode = new TerminalNode(2.0);
    public static Node threeNode = new TerminalNode(3.0);
    public static Node fourNode = new TerminalNode(4.0);


    public static Node xNode = new VariableNode(0);

    public static double[] createRandomInput(int size, double[] range){
        double[] inputs = new double[size];
        IntStream.range(0,size).forEach(i -> inputs[i] = Math.random()*(range[0]+range[1])-range[0]);
        return inputs;
    }

    /**
     * default range of [-1.0,1.0]
     */
    public static double[] createRandomInput(int size){
        return createRandomInput(size, new double[]{-1.0,1.0});
    }
}
