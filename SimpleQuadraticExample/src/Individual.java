import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Optional;


/**
 * The Individual class contains the
 */
@Data
@Builder
public class Individual implements Node,Comparable{

    /**
     * root is the tree structure program. It is of type Node, which may contain subNodes
     */
    Node root;
    /**
     * Is the fitness value of the Individual
     */
    Double fitness;


    /**
     * Returns the size of  the root tree
     * @return root size
     */
    public int size(){
        return root.size();
    }

    @Override
    public Double calculate(Double[] inputs) {
        return root.calculate(inputs);
    }

    @Override
    public String toClojureString() {
        if (root.getClass() != FunctionNode.class){
            return "("+root.toClojureString()+")";
        }
        return root.toClojureString();
    }

    @Override
    public Node clone() {
        return root.clone();
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    int maxDepth() {
        return root.getDepth();
    }

    @Override
    public Optional<Node> getSubtree(int index){
        return root.getSubtree(index);
    }


    /**
     * Replaces the subtree at index with newSubtree. If the index is 0 then new subtree will replace the individuals root
     * @param index position of the root tree
     * @param newSubtree the new subtree to place subtree at index
     */
    void replaceSubtreeAt(int index, Node newSubtree) {
        if (index == 0){
            setRoot(newSubtree.clone());
        } else{
            ((FunctionNode)root).replaceSubtreeAt(index,newSubtree);
        }
    }

    // static methods

    /**
     * Returns a new Individual with randomly selected tree
     * @param terminalList a list of functions that can be selected
     * @param functionList a list of terminal that can be selected
     * @param method either "grow" or "full"
     * @param depth the maximum depth that tree will be generated too
     * @return a new individual with randomly generated tree
     */
    static Individual generate(List<Node> terminalList, List<GPFunction> functionList, String method, int depth){

        Node root;
        if (depth == 0){
            root = NodeUtils.selectTerminalNode(terminalList);
        }else{
            root = NodeUtils.generateNode(terminalList,functionList,method,depth);
        }
        return Individual.builder().root(root).build();
    }

}
