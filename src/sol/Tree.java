package sol;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tree {

    private Node root;
    private Node NYT;

    private Map<Character, Node> characterNodeHashMap = new HashMap<>();
    private List<Node> nodes = new ArrayList<>();


    public Tree() {
        this.root = new Node(null);
        this.NYT = root;
        nodes.add(root);
    }


    public void addCharacter(Character value) {
        if (characterNodeHashMap.containsKey(value)) {
            Node toUpdate = characterNodeHashMap.get(value);
            updateTree(toUpdate);
        } else {
            Node parent = createNode(value);
            updateTree(parent);
        }
    }


    private Node createNode(Character value) {
        Node newNYT = new Node(NYT);
        Node leaf = new Node(NYT, value);
        characterNodeHashMap.put(value, leaf);
        nodes.add(0, leaf);
        nodes.add(0, newNYT);

        Node oldNYT = NYT;
        NYT.isNYT = false;
        NYT.left = newNYT;
        NYT.right = leaf;
        NYT = newNYT;

        updateNodeIndices();
        return oldNYT;
    }

    private void updateTree(Node node) {
        while (node != root) {
            if (maxInWeightClass(node)) {
                Node toSwap = findHighestIndexWeight(node);
                swap(toSwap, node);

            }
            node.setWeight(node.getWeight() + 1);
            node = node.parent;
        }
        node.increment();
        node.setIndex(nodes.indexOf(node));
    }


    private boolean maxInWeightClass(Node node) {
        int index = nodes.indexOf(node);
        int weight = node.getWeight();
        for (int i = index + 1; i < nodes.size(); i++) {
            Node next = nodes.get(i);
            if (next != node.parent && next.getWeight() == weight) {
                return true;
            } else if (next != node.parent && next.getWeight() > weight) {
                return false;
            }
        }
        return false;
    }

    private Node findHighestIndexWeight(Node node) {
        Node next;
        int index = node.getIndex() + 1;
        int weight = node.getWeight();
        while ((next = nodes.get(index)).getWeight() == weight) {
            index++;
        }
        next = nodes.get(--index);
        return next;

    }

    private void swap(Node newNodePosition, Node oldNodeGettingSwapped) {
        int newIndex = newNodePosition.getIndex();
        int oldIndex = oldNodeGettingSwapped.getIndex();

        Node oldParent = oldNodeGettingSwapped.parent;
        Node newParent = newNodePosition.parent;

        boolean oldNodeWasOnRight, newNodePositionOnRight;
        oldNodeWasOnRight = newNodePositionOnRight = false;

        if (newNodePosition.parent.right == newNodePosition) {
            newNodePositionOnRight = true;
        }
        if (oldNodeGettingSwapped.parent.right == oldNodeGettingSwapped) {
            oldNodeWasOnRight = true;
        }
        if (newNodePositionOnRight) {
            newParent.right = oldNodeGettingSwapped;
        } else {
            newParent.left = oldNodeGettingSwapped;
        }
        if (oldNodeWasOnRight) {
            oldParent.right = newNodePosition;
        } else {
            oldParent.left = newNodePosition;
        }

        oldNodeGettingSwapped.parent = newParent;
        newNodePosition.parent = oldParent;

        nodes.set(newIndex, oldNodeGettingSwapped);
        nodes.set(oldIndex, newNodePosition);
        updateNodeIndices();
    }


    private void updateNodeIndices() {
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            node.setIndex(nodes.indexOf(node));
        }
    }


    public void printCodes() {
        System.out.printf("%-20s %-20s %-20s %-20s %n", "Znak", "Kod", "Wystąpienia", "Index");
        for (Map.Entry<Character, Node> entry : characterNodeHashMap.entrySet()) {
            System.out.printf("%-20s %-20s %-20s %-20s %n",
                    entry.getKey(),
                    code(entry.getValue()),
                    String.valueOf(entry.getValue().getWeight()),
                    entry.getValue().getIndex());
        }

    }

    private String code(Node in) {
        Node node = in;
        Node parent;
        StringBuilder stringBuilder = new StringBuilder();
        while (node.parent != null) {
            parent = node.parent;
            if (parent.left == node) {
                stringBuilder.append("0");
            } else {
                stringBuilder.append("1");
            }
            node = parent;
        }
        return stringBuilder.reverse().toString();
    }

    public void print() {
        root.print("");
    }


    public float getEntropy() {
        int weight = nodes.stream().filter(node -> node.isLeaf).mapToInt(node -> node.getWeight()).sum();
        float entropy = 0;
        for (Node el : nodes) {
            if (el.isLeaf) {
                double p = (double) el.getWeight() / weight;
                entropy += p * log2(1 / p);
            }
        }
        return entropy;
    }

    private double log2(double number) {
        return Math.log(number) / Math.log(2);
    }


    public String code(char c) {
        String code;
        code = code(characterNodeHashMap.get(c));
        return code;
    }


    private int getTotalWeight() {
        return nodes.stream().filter(node -> node.isLeaf).mapToInt(node -> node.getWeight()).sum();
    }


}
