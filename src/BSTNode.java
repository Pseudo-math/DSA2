import java.io.*;
import java.util.*;


class BSTNode<T>
{
    public int NodeKey; // ключ узла
    public T NodeValue; // значение в узле
    public BSTNode<T> Parent; // родитель или null для корня
    public BSTNode<T> LeftChild; // левый потомок
    public BSTNode<T> RightChild; // правый потомок	

    public BSTNode(int key, T val, BSTNode<T> parent)
    {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }
}

// промежуточный результат поиска
class BSTFind<T>
{
    // null если в дереве вообще нету узлов
    public BSTNode<T> Node;

    // true если узел найден
    public boolean NodeHasKey;

    // true, если родительскому узлу надо добавить новый левым
    public boolean ToLeft;

    public BSTFind() { Node = null; }
}

class BST<T>
{
    BSTNode<T> Root; // корень дерева, или null
    int Count;

    public BST(BSTNode<T> node)
    {
        Root = node;
        Count = (node == null) ? 0 : 1;
    }

    public BSTFind<T> FindNodeByKey(int key)
    {
        if (Root == null)
            return new BSTFind<>();
        if (Root.NodeKey == key) {
            var finded = new BSTFind<T>();
            finded.Node = Root;
            finded.NodeHasKey = true;
            return finded;
        }
        if (key < Root.NodeKey && Root.LeftChild == null) {
            var finded = new BSTFind<T>();
            finded.Node = Root;
            finded.NodeHasKey = false;
            finded.ToLeft = true;
            return finded;
        }
        if (key > Root.NodeKey && Root.RightChild == null) {
            var finded = new BSTFind<T>();
            finded.Node = Root;
            finded.NodeHasKey = false;
            finded.ToLeft = false;
            return finded;
        }
        var rootOfSubtree = key < Root.NodeKey ? Root.LeftChild : Root.RightChild;
        return (new BST<>(rootOfSubtree)).FindNodeByKey(key);
    }

    public boolean AddKeyValue(int key, T val)
    {
        var whereNeedAdd = FindNodeByKey(key);
        if (whereNeedAdd.NodeHasKey)
            return false;
        var addingNode = new BSTNode<>(key, val, whereNeedAdd.Node);
        if (whereNeedAdd.ToLeft)
            whereNeedAdd.Node.LeftChild = addingNode;
        else
            whereNeedAdd.Node.RightChild = addingNode;
        Count++;
        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax)
    {
        if (Root == null)
            return null;
        if (FindMax && FromNode.RightChild == null || !FindMax && FromNode.LeftChild == null)
            return FromNode;
        if (FindMax)
            return FinMinMax(FromNode.RightChild, FindMax);
        else
            return FinMinMax(FromNode.LeftChild, FindMax);
    }
    private void ChangeNodeAndHeir(BSTNode<T> Node, BSTNode<T> Heir, boolean NodeIsLeft)
    {
        Heir.Parent = Node.Parent;
        if (NodeIsLeft)
            Node.Parent.LeftChild = Heir;
        else
            Node.Parent.RightChild = Heir;
    }
    public boolean DeleteNodeByKey(int key)
    {
        var whereNeedDelete = FindNodeByKey(key);
        if (!whereNeedDelete.NodeHasKey)
            return false;
        var deletingNode = whereNeedDelete.Node;
        boolean deletingNodeIsLeft = deletingNode.Parent.LeftChild != null && deletingNode.Parent.LeftChild.NodeKey == key;
        BSTNode<T> heir;
        if (deletingNode.LeftChild == null && deletingNode.RightChild == null) {
            heir = null;
            if (deletingNodeIsLeft)
                deletingNode.Parent.LeftChild = heir;
            else
                deletingNode.Parent.RightChild = heir;
            Count--;
            return true;
        }
        if (deletingNode.LeftChild == null || deletingNode.RightChild == null) {
            heir = Objects.requireNonNullElse(deletingNode.LeftChild, deletingNode.RightChild);
            ChangeNodeAndHeir(deletingNode, heir, deletingNodeIsLeft);
            Count--;
            return true;
        }
        heir = FinMinMax(deletingNode.RightChild, false);
        ChangeNodeAndHeir(deletingNode, heir, deletingNodeIsLeft);
        Count--;
        return true;
    }

    public int Count()
    {
        return Count; // количество узлов в дереве
    }

}