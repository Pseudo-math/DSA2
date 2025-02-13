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
        if (FromNode == null)
            return null;
        if (FindMax && FromNode.RightChild == null || !FindMax && FromNode.LeftChild == null)
            return FromNode;
        if (FindMax)
            return FinMinMax(FromNode.RightChild, FindMax);
        else
            return FinMinMax(FromNode.LeftChild, FindMax);
    }

    public boolean DeleteNodeByKey(int key) {
        BSTFind<T> foundNode = FindNodeByKey(key);
        if (!foundNode.NodeHasKey || foundNode.Node == null)
            return false;

        BSTNode<T> deletingNode = foundNode.Node;

        if (deletingNode.LeftChild != null && deletingNode.RightChild != null) {
            BSTNode<T> heir = FinMinMax(deletingNode.RightChild, false);

            if (heir.RightChild != null) {
                heir.RightChild.Parent = heir.Parent;
                if (heir.Parent.LeftChild == heir) {
                    heir.Parent.LeftChild = heir.RightChild;
                } else {
                    heir.Parent.RightChild = heir.RightChild;
                }
            } else {
                if (heir.Parent.LeftChild == heir) {
                    heir.Parent.LeftChild = null;
                } else {
                    heir.Parent.RightChild = null;
                }
            }

            if (deletingNode.Parent == null) {
                Root = heir;
            } else if (deletingNode.Parent.LeftChild == deletingNode) {
                deletingNode.Parent.LeftChild = heir;
            } else {
                deletingNode.Parent.RightChild = heir;
            }

            heir.Parent = deletingNode.Parent;

            heir.LeftChild = deletingNode.LeftChild;
            heir.RightChild = deletingNode.RightChild;

            Count--;
            return true;
        }
        BSTNode<T> child = (deletingNode.LeftChild != null) ? deletingNode.LeftChild : deletingNode.RightChild;

        if (deletingNode.Parent == null) {
            Root = child;
        } else {
            if (deletingNode.Parent.LeftChild == deletingNode) {
                deletingNode.Parent.LeftChild = child;
            } else {
                deletingNode.Parent.RightChild = child;
            }
        }

        if (child != null) {
            child.Parent = deletingNode.Parent;
        }

        Count--;
        return true;
    }

    public int Count()
    {
        return Count; // количество узлов в дереве
    }

}