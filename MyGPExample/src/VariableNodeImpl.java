public class VariableNodeImpl implements Node {


    String variableString;
    int variableIndex;

    public VariableNodeImpl(String variableString, int variableIndex){
        super();
        this.variableString = variableString;
        this.variableIndex = variableIndex;
    }


    @Override
    public boolean isTerminalNode() {
        return  true;
    }

    @Override
    public Double calculate(Double[] inputs) {
        return inputs[variableIndex];
    }

    @Override
    public String toClojureString() {
        return String.format("(%s)",variableString);
    }

    @Override
    public Node clone() {
        return new VariableNodeImpl(variableString, variableIndex);
    }

    @Override
    public Node getSubtree(int index) {
        if (index == 0){
            return this;
        }else{
            throw new IndexOutOfBoundsException("Variable Node index : " + index + " > 0");
        }
    }

}
