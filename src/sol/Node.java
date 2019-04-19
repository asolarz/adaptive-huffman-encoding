package sol;

public class Node {

    Node parent;
    Node left = null;
    Node right = null;

    boolean isNYT = false;
    boolean isLeaf = false;

    private int weight;
    private int index;
    private Character value;

    public Node(Node parent, Node left, Node right, int weight, int index) {
        this.parent = parent;
        this.weight = weight;
        this.index = index;
    }


    public Node(Node parent) {
        this.parent = parent;
        this.weight = 0;
        this.index = 0;
        this.isNYT = true;
    }


    public Node(Node parent, Character value) {
        this.parent = parent;
        this.weight = 1;
        this.index = 1;
        this.value = value;
        this.isLeaf = true;
        this.isNYT = false;
    }


    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getWeight() {
        return this.weight;
    }

    public void increment() {
        this.weight++;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    private Character getValue() {
        return this.value;
    }


    public void print(String prefix) {
        print(prefix, true);
    }

    private void print(String prefix, boolean isTail) {
        String val = "";
        if (this.getValue() != null)
            val = "(" + this.getValue() + ")";
        System.out.println(prefix + (isTail ? "└──── " : "├──── ") + this.getWeight() + val);

        if (null != left) {
            left.print(prefix + (isTail ? "       " : "│      "), (null == right));
        }

        if (null != right) {
            right.print(prefix + (isTail ? "       " : "│      "), true);
        }

    }


}
