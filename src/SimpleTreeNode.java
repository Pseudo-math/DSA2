import java.util.*;

public class SimpleTreeNode<T>
{
    public T NodeValue; // значение в узле
    public SimpleTreeNode<T> Parent; // родитель или null для корня
    public List<SimpleTreeNode<T>> Children; // список дочерних узлов или null
    public int Level;

    public SimpleTreeNode(T val, SimpleTreeNode<T> parent)
    {
        NodeValue = val;
        Parent = parent;
        Children = null;
        Level = 0;
    }
}

class SimpleTree<T>
{
    public SimpleTreeNode<T> Root; // корень, может быть null

    public SimpleTree(SimpleTreeNode<T> root)
    {
        Root = root;
    }

    public void AddChild(SimpleTreeNode<T> ParentNode, SimpleTreeNode<T> NewChild)
    {
        if (ParentNode == null) {
            Root = NewChild;
            return;
        }
        if (ParentNode.Children == null)
            ParentNode.Children = new ArrayList<SimpleTreeNode<T>>();
        ParentNode.Children.add(NewChild);
        NewChild.Parent = ParentNode;
        NewChild.Level = NewChild.Parent.Level + 1;
    }

    public void DeleteNode(SimpleTreeNode<T> NodeToDelete)
    {
        if (Root.equals(NodeToDelete)) {
            throw new IllegalArgumentException("NodeToDelete must not be root");
        }
        var ParentOfNode = NodeToDelete.Parent;
        ParentOfNode.Children.remove(NodeToDelete);
        NodeToDelete.Parent = null;
    }

    public List<SimpleTreeNode<T>> GetAllNodes()
    {
        var allNodes = new ArrayList<SimpleTreeNode<T>>();
        if (Root != null)
            allNodes.add(Root);
        if (Root == null || Root.Children == null || Root.Children.isEmpty()) {
            return allNodes;
        }
        for (var currentNode : Root.Children) {
            allNodes.addAll((new SimpleTree<T>(currentNode)).GetAllNodes());
        }
        return allNodes;
    }

    public List<SimpleTreeNode<T>> FindNodesByValue(T val)
    {
        var findedNodes = new ArrayList<SimpleTreeNode<T>>();
        if (Root != null && Root.NodeValue != null && Root.NodeValue.equals(val)) {
            findedNodes.add(Root);
        }
        if (Root == null || Root.Children == null || Root.Children.isEmpty()) {
            return findedNodes;
        }
        for (var currentNode : Root.Children) {
            findedNodes.addAll((new SimpleTree<T>(currentNode)).FindNodesByValue(val));
        }
        return findedNodes;
    }

    public void MoveNode(SimpleTreeNode<T> OriginalNode, SimpleTreeNode<T> NewParent)
    {
        DeleteNode(OriginalNode);
        AddChild(NewParent, OriginalNode);
        (new SimpleTree<T>(OriginalNode)).UpdateNodesLevels();
    }

    public int Count()
    {
        int count = 0;
        if (Root != null)
            count++;
        if (Root == null || Root.Children == null || Root.Children.isEmpty()) {
            return count;
        }
        for (var currentNode : Root.Children) {
            count += ((new SimpleTree<T>(currentNode)).Count());
        }
        return count;
    }

    public int LeafCount()
    {
        int leafCount = 0;
        if (Root != null && (Root.Children == null || Root.Children.isEmpty())) {
            leafCount++;
            return leafCount;
        }
        if (Root == null) {
            return leafCount;
        }
        for (var currentNode : Root.Children) {
            leafCount += ((new SimpleTree<T>(currentNode)).LeafCount());
        }
        return leafCount;
    }
    public void UpdateNodesLevels()
    {
        if (Root != null && Root.Parent != null)
            Root.Level = Root.Parent.Level + 1;
        if (Root == null || Root.Children == null || Root.Children.isEmpty()) {
            return;
        }
        for (var currentNode : Root.Children) {
            (new SimpleTree<T>(currentNode)).UpdateNodesLevels();
        }
    }

    public ArrayList<T> EvenTrees() {
        ArrayList<T> result = new ArrayList<>();
        if (Root == null) return result;
        countSubtreeSize(Root, result);
        return result;
    }

    private int countSubtreeSize(SimpleTreeNode<T> node, ArrayList<T> result) {
        if (node.Children == null) return 1; // Лист, возвращаем 1

        int size = 1; // Включаем текущий узел
        for (SimpleTreeNode<T> child : node.Children) {
            int childSize = countSubtreeSize(child, result);
            size += childSize;

            if (childSize % 2 == 0) {
                result.add(node.NodeValue);
                result.add(child.NodeValue);
            }
        }
        return size;
    }


    private SimpleTreeNode<T> getLeftChild(SimpleTreeNode<T> node) {
        if (node != null && node.Children != null && !node.Children.isEmpty()) {
            return node.Children.get(0);
        }
        return null;
    }

    private SimpleTreeNode<T> getRightChild(SimpleTreeNode<T> node) {
        if (node != null && node.Children != null && node.Children.size() > 1) {
            return node.Children.get(1);
        }
        return null;
    }

    public void BalanceBinaryTree() {
        if (Root == null) {
            return;
        }

        List<T> inorderValues = new ArrayList<>();
        collectInorderValues(Root, inorderValues);

        this.Root = buildBalancedTreeFromValues(inorderValues, 0, inorderValues.size() - 1, null);
        // UpdateNodesLevels(); // Optional
    }

    private void collectInorderValues(SimpleTreeNode<T> node, List<T> values) {
        if (node == null) {
            return;
        }
        collectInorderValues(getLeftChild(node), values);
        values.add(node.NodeValue);
        collectInorderValues(getRightChild(node), values);
    }

    private SimpleTreeNode<T> buildBalancedTreeFromValues(List<T> values, int start, int end, SimpleTreeNode<T> parent) {
        if (start > end) {
            return null;
        }

        int mid = start + (end - start) / 2;
        SimpleTreeNode<T> newNode = new SimpleTreeNode<>(values.get(mid), parent);

        SimpleTreeNode<T> leftChild = buildBalancedTreeFromValues(values, start, mid - 1, newNode);
        SimpleTreeNode<T> rightChild = buildBalancedTreeFromValues(values, mid + 1, end, newNode);

        if (leftChild != null || rightChild != null) {
            newNode.Children = new ArrayList<>();
            if (leftChild != null) {
                newNode.Children.add(leftChild);
            }
            if (rightChild != null) {
                newNode.Children.add(rightChild);
            }
        } else {
            newNode.Children = null;
        }

        return newNode;
    }

    public int CountEvenSubtrees(SimpleTreeNode<T> subNode) {
        if (subNode == null) {
            return 0;
        }
        int[] evenCount = {0};
        calculateSubtreeSizeAndCountEven(subNode, evenCount);
        return evenCount[0];
    }

    private int calculateSubtreeSizeAndCountEven(SimpleTreeNode<T> node, int[] evenCount) {
        if (node == null) {
            return 0;
        }

        int currentSubtreeSize = 1;

        if (node.Children != null) {
            for (SimpleTreeNode<T> child : node.Children) {
                currentSubtreeSize += calculateSubtreeSizeAndCountEven(child, evenCount);
            }
        }

        if (currentSubtreeSize > 0 && currentSubtreeSize % 2 == 0) {
            evenCount[0]++;
        }

        return currentSubtreeSize;
    }
}
