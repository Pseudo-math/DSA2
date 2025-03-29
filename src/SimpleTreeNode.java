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
        countSubtreeSize(Root, result);
        return result;
    }

    private int countSubtreeSize(SimpleTreeNode<T> node, ArrayList<T> result) {
        int size = 1;
        for (SimpleTreeNode<T> child : node.Children) {
            int childSize = countSubtreeSize(child, result);
            if (childSize % 2 == 0) {
                result.add(node.NodeValue);
                result.add(child.NodeValue);
            } else {
                size += childSize;
            }
        }
        return size;
    }
}
